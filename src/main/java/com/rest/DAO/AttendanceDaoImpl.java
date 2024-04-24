package com.rest.DAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rest.models.BarGraphData;
import com.rest.models.Customfilter;
import com.rest.models.Employee;
import com.rest.models.EmployeeAttendanceData;
import com.rest.models.EmployeeData;
import com.rest.models.FullDataRegistration;
import com.rest.models.FullUIDataObject;
import com.rest.models.PieGraphData;
import com.rest.models.ProgramCodes;
import com.rest.models.Punch;
import com.rest.models.PunchModel;
import com.rest.models.Punch_xref;
import com.rest.models.RfId;
import com.rest.models.SignUp;
import com.rest.models.pdfGenerateReport;

@Repository
public class AttendanceDaoImpl implements AttendanceDao {

	@Autowired
	SessionFactory sf;

	// verified on 20/04/24
	@Override
	public boolean savePunchIn(String reffId, String pbId) {
		Date date = new Date();
		Session session = sf.openSession();
		try {

			session.beginTransaction();

			SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
			String time = localDateFormat.format(date);

			LocalTime firstLower = LocalTime.parse("07:30:00");
			LocalTime firstUpper = LocalTime.parse("09:00:00");
			LocalTime secondLower = LocalTime.parse("12:00:00");
			LocalTime secondUpper = LocalTime.parse("13:30:00");
			LocalTime thirdLower = LocalTime.parse("15:30:00");
			LocalTime current = LocalTime.parse(time);
			String punchSlot;

			if (firstLower.compareTo(current) <= 0 && firstUpper.compareTo(current) >= 0) {
				punchSlot = "Forenoon Punch In";
			} else if (secondLower.compareTo(current) <= 0 && secondUpper.compareTo(current) >= 0) {
				punchSlot = "Afternoon Punch In";
			} else if (thirdLower.compareTo(current) <= 0) {
				punchSlot = "Punch Out";
			} else {
				punchSlot = "Late Punch";
			}

			session.save(new Punch_xref(new RfId(reffId), date, date, date, true, punchSlot, pbId));

			session.getTransaction().commit();

			session.close();
			return true;
		} catch (Exception ex) {
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return false;
	}

	// verified on 20/04/24
	@Override
	public List<Punch> getDataByDate(String date) {

		Session session = sf.openSession();

		try {

			session.beginTransaction();

			Query query = session.createQuery("from Punch_XREF_ENTITY where date = :c1");

			query.setString("c1", date);

			List<Punch> punches = query.list();

			session.getTransaction().commit();

			session.close();
			return punches;
		} catch (Exception ex) {
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return null;
	}

	// verified on 20/04/24
	@Override
	public FullUIDataObject getDataByFilter(Customfilter filter) {

		Session session = sf.openSession();
		StringBuilder sql = new StringBuilder();
		List<EmployeeData> employeesDataFirst = new ArrayList<>();
		List<EmployeeData> employeesDataSecond = new ArrayList<>();
		List<EmployeeData> employeesDataThird = new ArrayList<>();
		List<EmployeeData> employeesDataIntersectionFirstSecond = new ArrayList<>();
		List<EmployeeData> employeesDataIntersectionSecondThird = new ArrayList<>();
		List<EmployeeData> employeesDataIntersectionThirdFirst = new ArrayList<>();
		List<EmployeeData> employeesData1stHalfAttendance = new ArrayList<>();
		List<EmployeeData> employeesDataFullAttendance = new ArrayList<>();
		List<EmployeeData> employeesData2ndHalfAttendance = new ArrayList<>();
		List<EmployeeData> employeesDataAbsentAttendance = new ArrayList<>();
		List<EmployeeData> employeesDataSinglePunchAttendance = new ArrayList<>();
		List<EmployeeData> employeesPunchGridData = new ArrayList<>();
		List<EmployeeData> employeesAttendanceGridData = new ArrayList<>();
		List<EmployeeData> allemployeesData = new ArrayList<>();
		List<BarGraphData> graphDataList = new ArrayList<>();
		List<pdfGenerateReport> pdfReport =new ArrayList<>();

		FullUIDataObject uiData = new FullUIDataObject();

		try {

			session.beginTransaction();

			sql.append(
					"select * from employee_table as t1 join (select emp.pb_id  as pb_id_temp, min(punch.time) as time,punch.date,punch.punch_slot from employee_table emp join punch_xref_table punch  where emp.pb_id = punch.pb_id and punch.time>'07:30:00' and punch.time <'09:00:00' group by emp.pb_id,punch.date) as t2 where t1.pb_id = t2.pb_id_temp ");

			if (!filter.getPbId().equals("")) {
				sql.append(" and t1.pb_id = '" + filter.getPbId() + "'");
			}
			if (!filter.getDate().equals("")) {
				sql.append(" and (t2.date >= '" + filter.getDate() + "' and t2.date <= '" + filter.getEndDate() + "')");
			}
			if (!filter.getProgramCode().equals("")) {
				sql.append(" and t1.program_code = '" + filter.getProgramCode() + "'");
			}
			sql.append("  order by date,time desc");
			employeesPunchGridData = new ArrayList<>();
			SQLQuery query = session.createSQLQuery(sql.toString());
			List<Object[]> rows = query.list();
			for (Object[] row : rows) {

				EmployeeData emp = new EmployeeData(row[4].toString(), row[0].toString(), new RfId(row[9].toString()),
						row[2].toString(), row[3].toString(), row[5].toString(), row[6].toString(), row[11].toString(),
						row[12].toString(), row[13].toString());
				employeesDataFirst.add(emp);
				employeesPunchGridData.add(emp);
			}

			sql = new StringBuilder();

			sql.append(
					"select * from employee_table as t1 join (select emp.pb_id  as pb_id_temp, min(punch.time) as time,punch.date,punch.punch_slot from employee_table emp join punch_xref_table punch  where emp.pb_id = punch.pb_id and punch.time>'12:00:00' and punch.time <'13:30:00' group by emp.pb_id,punch.date) as t2 where t1.pb_id = t2.pb_id_temp ");

			if (!filter.getPbId().equals("")) {
				sql.append(" and t1.pb_id = '" + filter.getPbId() + "'");
			}
			if (!filter.getDate().equals("")) {
				sql.append(" and (t2.date >= '" + filter.getDate() + "' and t2.date <= '" + filter.getEndDate() + "')");
			}
			if (!filter.getProgramCode().equals("")) {
				sql.append(" and t1.program_code = '" + filter.getProgramCode() + "'");
			}
			sql.append("  order by date,time desc");
			query = session.createSQLQuery(sql.toString());
			rows = query.list();
			for (Object[] row : rows) {

				EmployeeData emp = new EmployeeData(row[4].toString(), row[0].toString(), new RfId(row[9].toString()),
						row[2].toString(), row[3].toString(), row[5].toString(), row[6].toString(), row[11].toString(),
						row[12].toString(), row[13].toString());

				employeesDataSecond.add(emp);
				employeesPunchGridData.add(emp);
			}

			sql = new StringBuilder();

			sql.append(
					"select * from employee_table as t1 join (select emp.pb_id  as pb_id_temp, max(punch.time) as time,punch.date,punch.punch_slot from employee_table emp join punch_xref_table punch  where emp.pb_id = punch.pb_id and punch.time>'15:30:00' and punch.time <'23:59:59' group by emp.pb_id,punch.date) as t2 where t1.pb_id = t2.pb_id_temp ");

			if (!filter.getPbId().equals("")) {
				sql.append(" and t1.pb_id = '" + filter.getPbId() + "'");
			}
			if (!filter.getDate().equals("")) {
				sql.append(" and (t2.date >= '" + filter.getDate() + "' and t2.date <= '" + filter.getEndDate() + "')");
			}
			if (!filter.getProgramCode().equals("")) {
				sql.append(" and t1.program_code = '" + filter.getProgramCode() + "'");
			}
			sql.append("  order by date,time desc");
			query = session.createSQLQuery(sql.toString());
			rows = query.list();
			// session.getTransaction().commit();
			for (Object[] row : rows) {

				EmployeeData emp = new EmployeeData(row[4].toString(), row[0].toString(), new RfId(row[9].toString()),
						row[2].toString(), row[3].toString(), row[5].toString(), row[6].toString(), row[11].toString(),
						row[12].toString(), row[13].toString());

				employeesDataThird.add(emp);
				employeesPunchGridData.add(emp);
			}
			
			// single punch code should be before if (filter.isAllData())
			
			employeesDataIntersectionFirstSecond = new ArrayList<>(employeesDataFirst);
			employeesDataIntersectionFirstSecond.retainAll(employeesDataSecond);
			
			employeesDataIntersectionSecondThird = new ArrayList<>(employeesDataSecond);
			employeesDataIntersectionSecondThird.retainAll(employeesDataThird);
			
			employeesDataIntersectionThirdFirst = new ArrayList<>(employeesDataThird);
			employeesDataIntersectionThirdFirst.retainAll(employeesDataFirst);
			
			
			employeesDataSinglePunchAttendance =new ArrayList<>(employeesPunchGridData);
			employeesDataSinglePunchAttendance.removeAll(employeesDataIntersectionFirstSecond);
			employeesDataSinglePunchAttendance.removeAll(employeesDataIntersectionSecondThird);
			employeesDataSinglePunchAttendance.removeAll(employeesDataIntersectionThirdFirst);
			
// should be before entire data fetch for pdf report
			for(int i =0;i<employeesPunchGridData.size();i++) {
				
				pdfGenerateReport obj = new pdfGenerateReport(employeesPunchGridData.get(i).getPbId(), employeesPunchGridData.get(i).getName(), employeesPunchGridData.get(i).getDesignation(), employeesPunchGridData.get(i).getDivision());
				for(int j=0;j<employeesPunchGridData.size();j++) {
					if(employeesPunchGridData.get(i).equals(employeesPunchGridData.get(j)) && employeesPunchGridData.get(j).getPunchSlot().equals("Forenoon Punch In")) {
						obj.setDate(employeesPunchGridData.get(j).getDate());
						obj.setInPunch(employeesPunchGridData.get(j).getTime());
					}
					
					if(employeesPunchGridData.get(i).equals(employeesPunchGridData.get(j)) && employeesPunchGridData.get(j).getPunchSlot().equals("Punch Out")) {
						obj.setDate(employeesPunchGridData.get(j).getDate());
						obj.setOutPunch(employeesPunchGridData.get(j).getTime());
					}
				}
				boolean isPresent = false;
				for(int k = 0;k<pdfReport.size();k++) {
					if(pdfReport.get(k).equals(obj)) {
						isPresent = true;
					}
				}
				
				if(!isPresent && (employeesPunchGridData.get(i).getPunchSlot().equals("Forenoon Punch In")|| employeesPunchGridData.get(i).getPunchSlot().equals("Punch Out") ))
					{
					pdfReport.add(obj);
					}
			}
			
			

			if (filter.isAllData()) {
				sql = new StringBuilder();
				sql.append(
						"select * from employee_table as t1 join (select emp.pb_id  as pb_id_temp, time,punch.date,punch.punch_slot from employee_table emp join punch_xref_table punch  where emp.pb_id = punch.pb_id ) as t2 where t1.pb_id = t2.pb_id_temp ");

				if (!filter.getPbId().equals("")) {
					sql.append(" and t1.pb_id = '" + filter.getPbId() + "'");
				}
				if (!filter.getDate().equals("")) {
					sql.append(" and (t2.date >= '" + filter.getDate() + "' and t2.date <= '" + filter.getEndDate()
							+ "')");
				}
				if (!filter.getProgramCode().equals("")) {
					sql.append(" and t1.program_code = '" + filter.getProgramCode() + "'");
				}
				sql.append("  order by date,time desc");
				employeesPunchGridData = new ArrayList<EmployeeData>();
				query = session.createSQLQuery(sql.toString());
				rows = query.list();
				for (Object[] row : rows) {

					EmployeeData emp = new EmployeeData(row[4].toString(), row[0].toString(), new RfId(row[9].toString()),
							row[2].toString(), row[3].toString(), row[5].toString(), row[6].toString(), row[11].toString(),
							row[12].toString(), row[13].toString());
					employeesPunchGridData.add(emp);
				}

			}

			sql = new StringBuilder();

			sql.append("select * from employee_table where 1=1");

			if (!filter.getProgramCode().equals("")) {
				sql.append(" and (program_code= '" + filter.getProgramCode() + "')");
			}
			if (!filter.getPbId().equals("")) {
				sql.append(" and pb_id = '" + filter.getPbId() + "'");
			}
			sql.append("  order by registered_date,registered_time desc");
			query = session.createSQLQuery(sql.toString());
			rows = query.list();
			session.getTransaction().commit();
			session.close();
			final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			for (Object[] row : rows) {

				EmployeeData emp = new EmployeeData(row[4].toString(), row[0].toString(), new RfId(row[9].toString()),
						row[2].toString(), row[3].toString(), row[5].toString(), row[6].toString(), row[7].toString(),
						row[1].toString(), "", "Absent");
				
				
				allemployeesData.add(emp);
				LocalDate startDate = LocalDate.parse(filter.getDate(), dtf);
				LocalDate endDate = LocalDate.parse(filter.getEndDate(), dtf);

				for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
					EmployeeData cloneEmp = new EmployeeData(emp.getName(), emp.getPbId(), emp.getReffId(),
							emp.getDesignation(), emp.getDivision(), emp.getPhoneNo(), emp.getProgramCode(),
							emp.getTime(), emp.getDate(), emp.getPunchSlot(), emp.getAttendance());
					cloneEmp.setDate(date.toString());
					employeesDataAbsentAttendance.add(cloneEmp);
				}

			}

			session.close();
		} catch (Exception ex) {
			session.getTransaction().rollback();
		} finally {
			session.close();
		}

		// 1st half
		employeesData1stHalfAttendance = new ArrayList<>(employeesDataFirst);
		employeesData1stHalfAttendance.retainAll(employeesDataSecond);

		// 2nd half
		employeesData2ndHalfAttendance = new ArrayList<>(employeesDataSecond);
		employeesData2ndHalfAttendance.retainAll(employeesDataThird);

		// full attendance
		employeesDataFullAttendance = new ArrayList<>(employeesDataFirst);
		employeesDataFullAttendance.retainAll(employeesDataThird);

		employeesData1stHalfAttendance.removeAll(employeesDataFullAttendance);
		employeesData2ndHalfAttendance.removeAll(employeesDataFullAttendance);

		// Absents
		employeesDataAbsentAttendance.removeAll(employeesDataFullAttendance);
		employeesDataAbsentAttendance.removeAll(employeesData1stHalfAttendance);
		employeesDataAbsentAttendance.removeAll(employeesData2ndHalfAttendance);
		employeesDataAbsentAttendance.removeAll(employeesDataSinglePunchAttendance);
		
		

		for (int i = 0; i < employeesData1stHalfAttendance.size(); i++) {
			employeesData1stHalfAttendance.get(i).setAttendance("Half Day");
			employeesAttendanceGridData.add(employeesData1stHalfAttendance.get(i));
		}
		for (int i = 0; i < employeesData2ndHalfAttendance.size(); i++) {
			employeesData2ndHalfAttendance.get(i).setAttendance("Half Day");
			employeesAttendanceGridData.add(employeesData2ndHalfAttendance.get(i));
		}
		for (int i = 0; i < employeesDataFullAttendance.size(); i++) {
			employeesDataFullAttendance.get(i).setAttendance("Full Day");
			employeesAttendanceGridData.add(employeesDataFullAttendance.get(i));
		}
		for (int i = 0; i < employeesDataAbsentAttendance.size(); i++) {
			employeesDataAbsentAttendance.get(i).setAttendance("Absent");
			employeesAttendanceGridData.add(employeesDataAbsentAttendance.get(i));
		}

		for(int i=0;i<employeesDataSinglePunchAttendance.size();i++) {
			employeesDataSinglePunchAttendance.get(i).setAttendance("Single Punch");
			employeesAttendanceGridData.add(employeesDataSinglePunchAttendance.get(i));
		}
		// finding absents

		// each emp
		/*
		 * for (int i = 0; i < allemployeesData.size(); i++) {
		 * allemployeesData.get(i).setAttendance("Absent"); // each day for (int j = 0;
		 * j < 3; j++) { // check 1st punch for (int k = 0; k <
		 * employeesDataFirst.size(); k++) { if
		 * (allemployeesData.get(i).equals(employeesDataFirst.get(k))) {
		 * allemployeesData.get(i).setAttendance("Absent"); } }
		 * 
		 * // check 2nd punch
		 * 
		 * // check 3rd punch } }
		 */

//pdf data
		/*
		 * for(int i =0;i<employeesDataAbsentAttendance.size();i++) { pdfGenerateReport
		 * obj = new pdfGenerateReport(employeesDataAbsentAttendance.get(i).getPbId(),
		 * employeesDataAbsentAttendance.get(i).getName(),
		 * employeesDataAbsentAttendance.get(i).getDesignation(),
		 * employeesDataAbsentAttendance.get(i).getDivision());
		 * obj.setDate(employeesDataAbsentAttendance.get(i).getDate()); for(int j = 0;j<
		 * pdfReport.size();j++) { if(pdfReport.get(i).equals(obj)) {
		 * pdfReport.remove(pdfReport.get(i)); } }
		 * 
		 * }
		 */
		//pdf data invalid punches
		for(int i=0;i<employeesPunchGridData.size();i++) {
			
			if(employeesPunchGridData.get(i).getPunchSlot().equals("Late Punch")) {
				boolean isEqual =false;
				pdfGenerateReport obj = new pdfGenerateReport(employeesPunchGridData.get(i).getPbId(), employeesPunchGridData.get(i).getName(), employeesPunchGridData.get(i).getDesignation(), employeesPunchGridData.get(i).getDivision());
				obj.setDate(employeesPunchGridData.get(i).getDate());
				LocalTime midTime = LocalTime.parse("12:00:00");
				LocalTime morningTime = LocalTime.parse("09:00:00");
				LocalTime eveningTime = LocalTime.parse("15:30:00");
				LocalTime current = LocalTime.parse(employeesPunchGridData.get(i).getTime());
				
				for(int j=0;j<pdfReport.size();j++) {
					if(pdfReport.get(j).equals(obj)) {
						isEqual=true;
						if (morningTime.compareTo(current) < 0  && midTime.compareTo(current) > 0 && pdfReport.get(j).getInPunch().equals("")) {
							pdfReport.get(j).setInPunch(employeesPunchGridData.get(i).getTime());
						}
						if (midTime.compareTo(current) < 0 && eveningTime.compareTo(current) > 0 && pdfReport.get(j).getOutPunch().equals("")) {
							pdfReport.get(j).setOutPunch(employeesPunchGridData.get(i).getTime());
						}
						
						
					}
					
				}
				if(!isEqual) {
					if (morningTime.compareTo(current) < 0  && midTime.compareTo(current) > 0) {
						obj.setInPunch(employeesPunchGridData.get(i).getTime());
					}
					if (midTime.compareTo(current) < 0 && eveningTime.compareTo(current) > 0) {
						obj.setOutPunch(employeesPunchGridData.get(i).getTime());
					}
					pdfReport.add(obj);
				}
				
				
		
			}

		}
		

		graphDataList.add(new BarGraphData("7:30am to 9:00am", employeesDataFirst.size()));
		graphDataList.add(new BarGraphData("12:00 to 1:30pm", employeesDataSecond.size()));
		graphDataList.add(new BarGraphData("after 3:30pm", employeesDataThird.size()));
		uiData.setEmployeesData1stHalfAttendance(employeesData1stHalfAttendance);
		uiData.setEmployeesData2ndHalfAttendance(employeesData2ndHalfAttendance);
		uiData.setEmployeesDataFirst(employeesDataFirst);
		uiData.setEmployeesDataFullAttendance(employeesDataFullAttendance);
		uiData.setEmployeesDataSecond(employeesDataSecond);
		uiData.setEmployeesDataThird(employeesDataThird);
		uiData.setEmployeesPunchGridData(employeesPunchGridData);
		uiData.setAllemployeesData(allemployeesData);
		uiData.setBarGraphData(graphDataList);
		uiData.setEmployeesDataAbsentAttendance(employeesDataAbsentAttendance);
		uiData.setEmployeesDataSinglePunchAttendance(employeesDataSinglePunchAttendance);
		uiData.setPieGraphData(
				new PieGraphData(employeesDataAbsentAttendance.size(), employeesDataFullAttendance.size(),employeesDataSinglePunchAttendance.size(),(employeesData1stHalfAttendance.size()+employeesData2ndHalfAttendance.size())));
		uiData.setEmployeesAttendanceGridData(employeesAttendanceGridData);
		uiData.setPdfReport(pdfReport);
		return uiData;
	}

	// verified on 20/04/24
	@Override
	public PunchModel getPunchData(String filter) {
		Session session = sf.openSession();

		try {

			session.beginTransaction();

			Query query = session.createQuery("from EMPLOYEE_ENTITY where is_valid = true and reff_id_reff_id = :c1");

			query.setString("c1", filter);

			Employee emp = (Employee) query.getSingleResult();

			session.getTransaction().commit();
			session.close();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
			LocalDateTime now = LocalDateTime.now();
			if (!emp.equals(null)) {
				PunchModel obj = new PunchModel(true, emp.getName() + " has punched at " + dtf.format(now),
						emp.getPbId());
				return obj;
			} else {
				return new PunchModel(false, "Please try again", "");
			}
			// session.close();
		} catch (Exception ex) {
			session.getTransaction().rollback();
			// exception catch
		} finally {
			session.close();
		}
		return null;
	}

	// verified on 20/04/24
	@Override
	public boolean makeEmployeeInvalidIfExist(Employee emp) {
		Session session = sf.openSession();
		try {

			session.beginTransaction();
			StringBuilder sql = new StringBuilder();

			sql.append("UPDATE EMPLOYEE_TABLE SET is_valid = false  WHERE reff_id_reff_id = '"
					+ emp.getReffId().getReffId() + "'");

			SQLQuery query = session.createSQLQuery(sql.toString());
			int ans = query.executeUpdate();
			session.getTransaction().commit();
			session.close();
			return true;
		} catch (Exception ex) {
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return false;
	}

	// verified on 20/04/24
	@Override
	public boolean makeEmployeePunchInvalidIfExist(Employee emp) {
		Session session = sf.openSession();
		try {

			session.beginTransaction();
			StringBuilder sql = new StringBuilder();

			sql.append("UPDATE PUNCH_XREF_TABLE SET valid = false  WHERE reff_id_reff_id = '"
					+ emp.getReffId().getReffId() + "'");

			SQLQuery query = session.createSQLQuery(sql.toString());
			int ans = query.executeUpdate();
			session.getTransaction().commit();
			session.close();
			return true;
		} catch (Exception ex) {
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return false;
	}

	// verified on 20/04/24
	@Override
	public boolean signUp(SignUp userRegistrationDto) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();

		}
		messageDigest.update(userRegistrationDto.getPassword().getBytes());
		String stringHash = new String(messageDigest.digest());
		userRegistrationDto.setPassword(stringHash);
		Session session = sf.openSession();
		try {

			session.beginTransaction();

			session.save(userRegistrationDto);

			session.getTransaction().commit();

			session.close();
			return true;
		} catch (Exception ex) {
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return false;
	}

	// verified on 20/04/24
	@Override
	public SignUp signIn(SignUp userRegistrationDto) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		messageDigest.update(userRegistrationDto.getPassword().getBytes());
		String stringHash = new String(messageDigest.digest());
		userRegistrationDto.setPassword(stringHash);
		Session session = sf.openSession();

		try {

			session.beginTransaction();

			Query query = session.createQuery("from SIGNUP_ENTITY where pbId = :c1 and password = :c2");

			query.setString("c1", userRegistrationDto.getPbId());
			query.setString("c2", userRegistrationDto.getPassword());

			SignUp obj = (SignUp) query.getSingleResult();

			session.getTransaction().commit();

			session.close();
			return obj;
		} catch (Exception ex) {
			session.getTransaction().rollback();

		} finally {
			session.close();

		}
		return null;
	}

	// verified on 20/04/24
	@Override
	public FullDataRegistration saveEmployee(Employee emp) {
		Date date = new Date();
		LocalDateTime myDateObj = LocalDateTime.now();
		System.out.println("Before formatting: " + myDateObj);
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String dateToday = myDateObj.format(myFormatObj);
		emp.setDate(date);
		emp.setTime(date);
		FullDataRegistration fullData = new FullDataRegistration();
		Session session = sf.openSession();
		emp.setProgramCode(emp.getProgramCode().toLowerCase());
		try {

			session.beginTransaction();
			session.saveOrUpdate(emp);

			// session.getTransaction().commit();

			StringBuilder sql = new StringBuilder();

			sql.append("select * from employee_table where registered_date >= '" + dateToday
					+ "' and registered_date <= '" + dateToday + "' and is_valid=1 and program_code = '"+emp.getProgramCode()+"' order by registered_time desc");

			List<EmployeeData> allemployeesData = new ArrayList<>();
			SQLQuery query = session.createSQLQuery(sql.toString());
			List<Object[]> rows = query.list();
			session.getTransaction().commit();
			session.close();
			for (Object[] row : rows) {

				EmployeeData employee = new EmployeeData(row[4].toString(), row[0].toString(),
						new RfId(row[9].toString()), row[2].toString(), row[3].toString(), row[5].toString(),
						row[6].toString(), row[7].toString(), row[1].toString(), "Registration",
						"Attendance no marked");
				allemployeesData.add(employee);
			}
			fullData.setEmployeeRegisteredToday(allemployeesData);
			fullData.setEmployeeRegisteredStatus(true);
			return fullData;
		} catch (Exception ex) {
			session.getTransaction().rollback();

		} finally {
			session.close();

		}
		fullData.setEmployeeRegisteredStatus(false);
		return fullData;
	}

	@Override
	public PieGraphData getGraphDataByFilter(Customfilter filter) {
		Session session = sf.openSession();
		String count = "";
		Object o = null;
		try {
			session.beginTransaction();
			StringBuilder sql = new StringBuilder();

			sql.append(
					"select table1.pb_id,table1.name,table1.designation,table1.division,table1.phone_no,table1.program_code,table1.date from\r\n"
							+ "((select * from employee_table as t1 join (select emp.pb_id  as pb_id_temp, min(punch.time) as time,punch.date,punch.punch_slot from employee_table emp join punch_xref_table punch  where emp.pb_id = punch.pb_id and punch.time>'07:30:00' and punch.time <'09:00:00' group by emp.pb_id,punch.date) as t2 where t1.pb_id = t2.pb_id_temp) as table1\r\n"
							+ " inner join \r\n"
							+ " (select * from employee_table as t1 join (select emp.pb_id  as pb_id_temp, max(punch.time) as time,punch.date,punch.punch_slot from employee_table emp join punch_xref_table punch  where emp.pb_id = punch.pb_id and punch.time>'15:30:00' and punch.time <'23:59:59' group by emp.pb_id,punch.date) as t2 where t1.pb_id = t2.pb_id_temp) as table2 \r\n"
							+ " on table1.pb_id = table2.pb_id and table2.date=table2.date) where (table1.date>= '"
							+ filter.getDate() + "' and table1.date<= '" + filter.getEndDate()
							+ "') and(table2.date>= '" + filter.getDate() + "' and table2.date<= '"
							+ filter.getEndDate() + "') and (table3.date>= '" + filter.getDate()
							+ "' and table3.date<= '" + filter.getEndDate() + "')");
			if (!filter.getProgramCode().equals("")) {
				sql.append(" and (table1.program_code= '" + filter.getProgramCode() + "' and table2.program_code= '"
						+ filter.getProgramCode() + "' and table3.program_code= '" + filter.getProgramCode() + "')");
			}
			if (!filter.getPbId().equals("")) {
				sql.append(" and (table1.pb_id= '" + filter.getPbId() + "' and table2.pb_id= '" + filter.getPbId()
						+ "' and table3.pb_id= '" + filter.getPbId() + "')");
			}

			SQLQuery query = session.createSQLQuery(sql.toString());
			List<Object[]> rows = query.list();
			session.getTransaction().commit();

			o = rows.get(0);
			System.out.print(o);
			session.close();

		} catch (Exception ex) {
			session.getTransaction().rollback();
		} finally {
			session.close();
		}

		session = sf.openSession();
		List<EmployeeData> employeesData = new ArrayList<EmployeeData>();
		try {
			session.beginTransaction();
			StringBuilder sql = new StringBuilder();

			sql.append("select * from employee_table where is_valid = '1'");
			if (!filter.getProgramCode().equals("")) {
				sql.append(" and (program_code= '" + filter.getProgramCode() + "')");
			}
			if (!filter.getPbId().equals("")) {
				sql.append(" and pb_id = '" + filter.getPbId() + "'");
			}

			SQLQuery query = session.createSQLQuery(sql.toString());
			List<Object[]> rows = query.list();
			session.getTransaction().commit();
			session.close();
			for (Object[] row : rows) {

				EmployeeData emp = new EmployeeData();
				employeesData.add(emp);
			}

		} catch (Exception ex) {
			session.getTransaction().rollback();
		} finally {
			session.close();
		}

		if (o != null)
			return new PieGraphData(employeesData.size() - Integer.parseInt(o.toString()),
					Integer.parseInt(o.toString()));
		else {
			return new PieGraphData(employeesData.size() - 0, 0);
		}
	}

	@Override
	public List<BarGraphData> getBarGraphDataByFilter(Customfilter filter) {
		// TODO Auto-generated method stub
		Session session = sf.openSession();
		StringBuilder sql = new StringBuilder();
		List<EmployeeData> employeesData = null;
		List<BarGraphData> graphDataList = new ArrayList<>();

		try {

			session.beginTransaction();

			sql.append(
					"select * from employee_table as t1 join (select emp.pb_id  as pb_id_temp, min(punch.time) as time,punch.date,punch.punch_slot from employee_table emp join punch_xref_table punch  where emp.pb_id = punch.pb_id and punch.time>'07:30:00' and punch.time <'09:00:00' group by emp.pb_id,punch.date) as t2 where t1.pb_id = t2.pb_id_temp ");

			if (!filter.getDate().equals("")) {
				sql.append(" and (t2.date >= '" + filter.getDate() + "' and t2.date <= '" + filter.getEndDate() + "')");
			}
			if (!filter.getProgramCode().equals("")) {
				sql.append(" and t1.program_code = '" + filter.getProgramCode() + "'");
			}
			if (!filter.getPbId().equals("")) {
				sql.append(" and t1.pb_id = '" + filter.getPbId() + "'");
			}

			employeesData = new ArrayList<EmployeeData>();
			SQLQuery query = session.createSQLQuery(sql.toString());
			List<Object[]> rows = query.list();
			for (Object[] row : rows) {

				EmployeeData emp = new EmployeeData(row[3].toString(), row[0].toString(), null, row[1].toString(),
						row[2].toString(), row[4].toString(), row[5].toString(), row[9].toString(), row[10].toString(),
						row[11].toString());
				employeesData.add(emp);
			}

			graphDataList.add(new BarGraphData("7:30am to 9:00am", employeesData.size()));

			sql = new StringBuilder();

			sql.append(
					"select * from employee_table as t1 join (select emp.pb_id  as pb_id_temp, min(punch.time) as time,punch.date,punch.punch_slot from employee_table emp join punch_xref_table punch  where emp.pb_id = punch.pb_id and punch.time>'12:00:00' and punch.time <'13:30:00' group by emp.pb_id,punch.date) as t2 where t1.pb_id = t2.pb_id_temp ");

			if (!filter.getDate().equals("")) {
				sql.append(" and (t2.date >= '" + filter.getDate() + "' and t2.date <= '" + filter.getEndDate() + "')");
			}
			if (!filter.getProgramCode().equals("")) {
				sql.append(" and t1.program_code = '" + filter.getProgramCode() + "'");
			}
			if (!filter.getPbId().equals("")) {
				sql.append(" and t1.pb_id = '" + filter.getPbId() + "'");
			}

			employeesData = new ArrayList<EmployeeData>();
			query = session.createSQLQuery(sql.toString());
			rows = query.list();
			for (Object[] row : rows) {

				EmployeeData emp = new EmployeeData(row[3].toString(), row[0].toString(), null, row[1].toString(),
						row[2].toString(), row[4].toString(), row[5].toString(), row[9].toString(), row[10].toString(),
						row[11].toString());

				employeesData.add(emp);
			}

			graphDataList.add(new BarGraphData("12:00 to 1:30pm", employeesData.size()));

			sql = new StringBuilder();

			sql.append(
					"select * from employee_table as t1 join (select emp.pb_id  as pb_id_temp, max(punch.time) as time,punch.date,punch.punch_slot from employee_table emp join punch_xref_table punch  where emp.pb_id = punch.pb_id and punch.time>'15:30:00' and punch.time <'23:59:59' group by emp.pb_id,punch.date) as t2 where t1.pb_id = t2.pb_id_temp ");

			if (!filter.getDate().equals("")) {
				sql.append(" and (t2.date >= '" + filter.getDate() + "' and t2.date <= '" + filter.getEndDate() + "')");
			}
			if (!filter.getProgramCode().equals("")) {
				sql.append(" and t1.program_code = '" + filter.getProgramCode() + "'");
			}
			if (!filter.getPbId().equals("")) {
				sql.append(" and t1.pb_id = '" + filter.getPbId() + "'");
			}

			employeesData = new ArrayList<EmployeeData>();

			query = session.createSQLQuery(sql.toString());
			rows = query.list();
			session.getTransaction().commit();
			session.close();
			for (Object[] row : rows) {

				EmployeeData emp = new EmployeeData(row[3].toString(), row[0].toString(), null, row[1].toString(),
						row[2].toString(), row[4].toString(), row[5].toString(), row[9].toString(), row[10].toString(),
						row[11].toString());

				employeesData.add(emp);
			}

			graphDataList.add(new BarGraphData("after 3:30pm", employeesData.size()));
		} catch (Exception ex) {
			session.getTransaction().rollback();
		} finally {
			session.close();
		}

		return graphDataList;
	}

	@Override
	public String getCandidateByRef(String reff_id) {
		return "";

	}

//verified on 20/04/24
	@Override
	public List<ProgramCodes> getProgramCodes() {
		// TODO Auto-generated method stub
		Session session = sf.openSession();
		String sql = "SELECT distinct(program_code) FROM employee_table order by registered_date desc";
		List<Object[]> rows;
		List<ProgramCodes> pragramCodes = new ArrayList<ProgramCodes>();
		try {

			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(sql.toString());
			rows = query.list();
			session.getTransaction().commit();
			for (Object row : rows) {
				pragramCodes.add(new ProgramCodes(row.toString(), ""));
			}
			session.close();
		} catch (Exception ex) {
			session.getTransaction().rollback();
		} finally {
			session.close();

		}
		return pragramCodes;
	}

	@Override
	public List<EmployeeData> getAttendanceDataByFilter(Customfilter filter) {
		// TODO Auto-generated method stub
		return null;
	}
}
