package com.rest.DAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
import com.rest.models.EmployeeData;
import com.rest.models.PieGraphData;
import com.rest.models.ProgramCodes;
import com.rest.models.Punch;
import com.rest.models.PunchModel;
import com.rest.models.Punch_xref;
import com.rest.models.RfId;
import com.rest.models.SignUp;

@Repository
public class AttendanceDaoImpl implements AttendanceDao {
	
	@Autowired
	SessionFactory sf;

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
	        
	        if(firstLower.compareTo(current)<=0 && firstUpper.compareTo(current)>=0) {
	        	punchSlot="Forenoon Punch In";
	        }
	        else if(secondLower.compareTo(current)<=0 && secondUpper.compareTo(current)>=0) {
	        	punchSlot="Afternoon Punch In";
	        }
	        else if(thirdLower.compareTo(current)<=0) {
	        	punchSlot = "Punch Out";
	        }
	        else {
	        	punchSlot = "Invalid Punch";
	        }
		
			session.save(new Punch_xref(new RfId(reffId),date,date,date,true,punchSlot,pbId));
		
			session.getTransaction().commit();
			
			return true;
			}
		catch(Exception ex)
		{
			
		}
		finally {
			session.close();
		}
		return false;
	}

	@Override
	public List<Punch> getDataByDate(String date) {
		
		
		
		Session session = sf.openSession();
		
		try {
		
			session.beginTransaction();
			
			Query query = session.createQuery("from Punch_XREF_ENTITY where date = :c1");
			
			query.setString("c1", date);
			
			List<Punch> punches = query.list();
		
			session.getTransaction().commit();
			
			return punches;
			}
		catch(Exception ex)
		{
			
		}
		finally {
			session.close();
		}
		return null;
	}

	@Override
	public List<EmployeeData> getDataByFilter(Customfilter filter) {
		
		Session session = sf.openSession();
        StringBuilder sql = new StringBuilder();
        List<EmployeeData> employeesData=null;
        
        try {
        	
        	session.beginTransaction();
        	
        	if(!filter.isAllData()) {
        
        sql.append("select * from employee_table as t1 join (select emp.pb_id  as pb_id_temp, min(punch.time) as time,punch.date,punch.punch_slot from employee_table emp join punch_xref_table punch  where emp.pb_id = punch.pb_id and punch.time>'07:30:00' and punch.time <'09:00:00' group by emp.pb_id,punch.date) as t2 where t1.pb_id = t2.pb_id_temp ");
		
		if(!filter.getPbId().equals("")) {
			sql.append(" and t1.pb_id = '"+filter.getPbId()+"'");
		}
		if(!filter.getDate().equals("")) {
			sql.append(" and (t2.date >= '"+filter.getDate()+"' and t2.date <= '"+filter.getEndDate()+"')");
		}
		if(!filter.getProgramCode().equals("")) {
			sql.append(" and t1.program_code = '"+filter.getProgramCode()+"'");
		}
		employeesData = new ArrayList<EmployeeData>();
		SQLQuery query = session.createSQLQuery(sql.toString());
		List<Object[]> rows = query.list();
		for(Object[] row : rows){
			
			EmployeeData emp = new EmployeeData(row[3].toString(), row[0].toString(), null, row[1].toString(), row[2].toString(), row[4].toString(),row[5].toString(), row[9].toString(), row[10].toString(),row[11].toString());
			employeesData.add(emp);
		}
		
		
		
		
         sql = new StringBuilder();
        
        sql.append("select * from employee_table as t1 join (select emp.pb_id  as pb_id_temp, min(punch.time) as time,punch.date,punch.punch_slot from employee_table emp join punch_xref_table punch  where emp.pb_id = punch.pb_id and punch.time>'12:00:00' and punch.time <'13:30:00' group by emp.pb_id,punch.date) as t2 where t1.pb_id = t2.pb_id_temp ");
		
		if(!filter.getPbId().equals("")) {
			sql.append(" and t1.pb_id = '"+filter.getPbId()+"'");
		}
		if(!filter.getDate().equals("")) {
			sql.append(" and (t2.date >= '"+filter.getDate()+"' and t2.date <= '"+filter.getEndDate()+"')");
		}
		if(!filter.getProgramCode().equals("")) {
			sql.append(" and t1.program_code = '"+filter.getProgramCode()+"'");
		}
		 
		 query = session.createSQLQuery(sql.toString());
		 rows = query.list();
		for(Object[] row : rows){
			
			EmployeeData emp = new EmployeeData(row[3].toString(), row[0].toString(), null, row[1].toString(), row[2].toString(), row[4].toString(),row[5].toString(), row[9].toString(), row[10].toString(),row[11].toString());
	
			employeesData.add(emp);
		}
		
		
         sql = new StringBuilder();
        
        sql.append("select * from employee_table as t1 join (select emp.pb_id  as pb_id_temp, max(punch.time) as time,punch.date,punch.punch_slot from employee_table emp join punch_xref_table punch  where emp.pb_id = punch.pb_id and punch.time>'15:30:00' and punch.time <'23:59:59' group by emp.pb_id,punch.date) as t2 where t1.pb_id = t2.pb_id_temp ");
		
		if(!filter.getPbId().equals("")) {
			sql.append(" and t1.pb_id = '"+filter.getPbId()+"'");
		}
		if(!filter.getDate().equals("")) {
			sql.append(" and (t2.date >= '"+filter.getDate()+"' and t2.date <= '"+filter.getEndDate()+"')");
		}
		if(!filter.getProgramCode().equals("")) {
			sql.append(" and t1.program_code = '"+filter.getProgramCode()+"'");
		}
		
		 query = session.createSQLQuery(sql.toString());
		rows = query.list();
		session.getTransaction().commit();
		for(Object[] row : rows){
			
			EmployeeData emp = new EmployeeData(row[3].toString(), row[0].toString(), null, row[1].toString(), row[2].toString(), row[4].toString(),row[5].toString(), row[9].toString(), row[10].toString(),row[11].toString());
			
			employeesData.add(emp);
		}
        
        	}
        else {
        	
        	sql.append("select * from employee_table as t1 join (select emp.pb_id  as pb_id_temp, time,punch.date,punch.punch_slot from employee_table emp join punch_xref_table punch  where emp.pb_id = punch.pb_id ) as t2 where t1.pb_id = t2.pb_id_temp ");
    		
    		if(!filter.getPbId().equals("")) {
    			sql.append(" and t1.pb_id = '"+filter.getPbId()+"'");
    		}
    		if(!filter.getDate().equals("")) {
    			sql.append(" and (t2.date >= '"+filter.getDate()+"' and t2.date <= '"+filter.getEndDate()+"')");
    		}
    		if(!filter.getProgramCode().equals("")) {
    			sql.append(" and t1.program_code = '"+filter.getProgramCode()+"'");
    		}
    		employeesData = new ArrayList<EmployeeData>();
    		SQLQuery query = session.createSQLQuery(sql.toString());
    		List<Object[]> rows = query.list();
    		for(Object[] row : rows){
    			
    			EmployeeData emp = new EmployeeData(row[3].toString(), row[0].toString(), null, row[1].toString(), row[2].toString(), row[4].toString(),row[5].toString(), row[9].toString(), row[10].toString(),row[11].toString());
    			employeesData.add(emp);
    		}
        	
        }
        }
        catch(Exception ex) {
        	
        }
        finally {
        	session.close();
        }
		
		return employeesData;
	}


	@Override
	public PunchModel getPunchData(String filter) {
		Session session = sf.openSession();
		
		try {
		
			session.beginTransaction();
			
			Query query = session.createQuery("from EMPLOYEE_ENTITY where is_valid = true and reff_id_reff_id = :c1");
			
			query.setString("c1", filter);
			
			Employee emp = (Employee) query.getSingleResult();
		
			session.getTransaction().commit();
			   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");  
			   LocalDateTime now = LocalDateTime.now();
			   if(!emp.equals(null)) {
				   PunchModel obj = new PunchModel(true,emp.getName()+ " has punched at "+ dtf.format(now),emp.getPbId());
				   return obj;
			   }
			   else {
				   return new PunchModel(false,"Please try again","");
			   }
			
			
			}
		catch(Exception ex)
		{
			
			//exception catch
		}
		finally {
			session.close();
		}
		return null;
	}

	@Override
	public boolean makeEmployeeInvalidIfExist(Employee emp) {
		Session session = sf.openSession();
		try {
		
		
		session.beginTransaction();
        StringBuilder sql = new StringBuilder();
        
        sql.append("UPDATE EMPLOYEE_TABLE SET is_valid = false  WHERE reff_id_reff_id = '"+emp.getReffId().getReffId()+"'");
		
		SQLQuery query = session.createSQLQuery(sql.toString());
		int ans = query.executeUpdate();
		session.getTransaction().commit();
		return true;
		}
		catch(Exception ex)
		{
			
		}
		finally {
			session.close();
		}
		return false;
	}
	
	@Override
	public boolean makeEmployeePunchInvalidIfExist(Employee emp) {
		Session session = sf.openSession();
		try {
		
		
		session.beginTransaction();
        StringBuilder sql = new StringBuilder();
        
        sql.append("UPDATE PUNCH_XREF_TABLE SET valid = false  WHERE reff_id_reff_id = '"+emp.getReffId().getReffId()+"'");
		
		SQLQuery query = session.createSQLQuery(sql.toString());
		int ans = query.executeUpdate();
		session.getTransaction().commit();
		return true;
		}
		catch(Exception ex)
		{
			
		}
		finally {
			session.close();
		}
		return false;
	}

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
			
			return true;
			}
		catch(Exception ex)
		{
			
		}
		finally {
			session.close();
		}
		return false;
	}

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
			
			return obj;
			}
		catch(Exception ex)
		{
			
			
		}
		finally {
			session.close();
			
		}
		return null;
	}

	@Override
	public boolean saveEmployee(Employee emp) {
		Session session = sf.openSession();
		emp.setProgramCode(emp.getProgramCode().toLowerCase());
		try {
		
			session.beginTransaction();
			session.save(emp);

			session.getTransaction().commit();

			
			return true;
			}
		catch(Exception ex)
		{
			try {
				
				
				session.beginTransaction();
		        StringBuilder sql = new StringBuilder();
		        
		        sql.append("UPDATE EMPLOYEE_TABLE SET designation = '"+emp.getDesignation()+"', division = '"+emp.getDivision()+"', name = '"+emp.getName()+"', phone_no = '"+emp.getPhoneNo()+"', program_code = '"+emp.getProgramCode()+"', is_valid = true , reff_id_reff_id = '"+emp.getReffId().getReffId()+"'  WHERE pb_id = '"+emp.getPbId()+"'");
				
				SQLQuery query = session.createSQLQuery(sql.toString());
				int ans = query.executeUpdate();
				session.getTransaction().commit();
				return true;
				}
				catch(Exception ex1)
				{
					
				}
		}
		finally {
			session.close();
			
		}
		return false;
	}

	@Override
	public PieGraphData getGraphDataByFilter(Customfilter filter) {
		Session session = sf.openSession();
		String count="";
		Object o=null;
		try {
			session.beginTransaction();
		 StringBuilder sql = new StringBuilder();
	        
	        sql.append("select count(*) from\r\n"
	        		+ "((select * from employee_table as t1 join (select emp.pb_id  as pb_id_temp, min(punch.time) as time,punch.date,punch.punch_slot from employee_table emp join punch_xref_table punch  where emp.pb_id = punch.pb_id and punch.time>'07:30:00' and punch.time <'09:00:00' group by emp.pb_id,punch.date) as t2 where t1.pb_id = t2.pb_id_temp) as table1\r\n"
	        		+ "  inner join \r\n"
	        		+ " (select * from employee_table as t1 join (select emp.pb_id  as pb_id_temp, min(punch.time) as time,punch.date,punch.punch_slot from employee_table emp join punch_xref_table punch  where emp.pb_id = punch.pb_id and punch.time>'12:00:00' and punch.time <'13:30:00' group by emp.pb_id,punch.date) as t2 where t1.pb_id = t2.pb_id_temp) as table2\r\n"
	        		+ " inner join \r\n"
	        		+ " (select * from employee_table as t1 join (select emp.pb_id  as pb_id_temp, max(punch.time) as time,punch.date,punch.punch_slot from employee_table emp join punch_xref_table punch  where emp.pb_id = punch.pb_id and punch.time>'15:30:00' and punch.time <'23:59:59' group by emp.pb_id,punch.date) as t2 where t1.pb_id = t2.pb_id_temp) as table3 \r\n"
	        		+ " on table1.pb_id = table2.pb_id and table2.pb_id=table3.pb_id) where (table1.date>= '"+filter.getDate()+"' and table1.date<= '"+filter.getEndDate()+"') and(table2.date>= '"+filter.getDate()+"' and table2.date<= '"+filter.getEndDate()+"') and (table3.date>= '"+filter.getDate()+"' and table3.date<= '"+filter.getEndDate()+"')");
			if(!filter.getProgramCode().equals("")) {
				sql.append(" and (table1.program_code= '"+filter.getProgramCode()+"' and table2.program_code= '"+filter.getProgramCode()+"' and table3.program_code= '"+filter.getProgramCode()+"')");
			}
			if(!filter.getPbId().equals("")) {
				sql.append(" and (table1.pb_id= '"+filter.getPbId()+"' and table2.pb_id= '"+filter.getPbId()+"' and table3.pb_id= '"+filter.getPbId()+"')");
			}
	        
	        SQLQuery query = session.createSQLQuery(sql.toString());
			List<Object[]> rows = query.list();
			session.getTransaction().commit();
				
				 o=rows.get(0);
				System.out.print(o);
			

		}
		catch(Exception ex) {
			System.out.println(ex);		}
		finally {
			session.close();
		}
		
		
		 session = sf.openSession();
		List<EmployeeData> employeesData = new ArrayList<EmployeeData>();
		try {
			session.beginTransaction();
		 StringBuilder sql = new StringBuilder();
	        
	        sql.append("select * from employee_table where is_valid = '1'");
			if(!filter.getProgramCode().equals("")) {
				sql.append(" and (program_code= '"+filter.getProgramCode()+"')");
			}
			if(!filter.getPbId().equals("")) {
				sql.append(" and pb_id = '"+filter.getPbId()+"'");
			}

	        
	        
	        SQLQuery query = session.createSQLQuery(sql.toString());
			List<Object[]> rows = query.list();
			session.getTransaction().commit();
			for(Object[] row : rows){
				
				EmployeeData emp = new EmployeeData();
				employeesData.add(emp);
			}
				
		}
		catch(Exception ex) {
			System.out.println(ex);		}
		finally {
			session.close();
		}
			
			if(o != null)
				return new PieGraphData(employeesData.size()-Integer.parseInt(o.toString()),Integer.parseInt(o.toString()));
			else {
				return new PieGraphData(employeesData.size()-0,0);
			}
	}
	

	@Override
	public List<BarGraphData> getBarGraphDataByFilter(Customfilter filter) {
		// TODO Auto-generated method stub
		Session session = sf.openSession();
        StringBuilder sql = new StringBuilder();
        List<EmployeeData> employeesData=null;
        List<BarGraphData> graphDataList = new ArrayList<>();
        
        try {
        	
        	session.beginTransaction();
        
        sql.append("select * from employee_table as t1 join (select emp.pb_id  as pb_id_temp, min(punch.time) as time,punch.date,punch.punch_slot from employee_table emp join punch_xref_table punch  where emp.pb_id = punch.pb_id and punch.time>'07:30:00' and punch.time <'09:00:00' group by emp.pb_id,punch.date) as t2 where t1.pb_id = t2.pb_id_temp ");
		
		if(!filter.getDate().equals("")) {
			sql.append(" and (t2.date >= '"+filter.getDate()+"' and t2.date <= '"+filter.getEndDate()+"')");
		}
		if(!filter.getProgramCode().equals("")) {
			sql.append(" and t1.program_code = '"+filter.getProgramCode()+"'");
		}
		if(!filter.getPbId().equals("")) {
			sql.append(" and t1.pb_id = '"+filter.getPbId()+"'");
		}

		employeesData = new ArrayList<EmployeeData>();
		SQLQuery query = session.createSQLQuery(sql.toString());
		List<Object[]> rows = query.list();
		for(Object[] row : rows){
			
			EmployeeData emp = new EmployeeData(row[3].toString(), row[0].toString(), null, row[1].toString(), row[2].toString(), row[4].toString(),row[5].toString(), row[9].toString(), row[10].toString(),row[11].toString());
			employeesData.add(emp);
		}
		
		
		graphDataList.add(new BarGraphData("7:30am to 9:00am", employeesData.size()));
		
         sql = new StringBuilder();
        
        sql.append("select * from employee_table as t1 join (select emp.pb_id  as pb_id_temp, min(punch.time) as time,punch.date,punch.punch_slot from employee_table emp join punch_xref_table punch  where emp.pb_id = punch.pb_id and punch.time>'12:00:00' and punch.time <'13:30:00' group by emp.pb_id,punch.date) as t2 where t1.pb_id = t2.pb_id_temp ");
		
		if(!filter.getDate().equals("")) {
			sql.append(" and (t2.date >= '"+filter.getDate()+"' and t2.date <= '"+filter.getEndDate()+"')");
		}
		if(!filter.getProgramCode().equals("")) {
			sql.append(" and t1.program_code = '"+filter.getProgramCode()+"'");
		}
		if(!filter.getPbId().equals("")) {
			sql.append(" and t1.pb_id = '"+filter.getPbId()+"'");
		}

		employeesData = new ArrayList<EmployeeData>();
		 query = session.createSQLQuery(sql.toString());
		 rows = query.list();
		for(Object[] row : rows){
			
			EmployeeData emp = new EmployeeData(row[3].toString(), row[0].toString(), null, row[1].toString(), row[2].toString(), row[4].toString(),row[5].toString(), row[9].toString(), row[10].toString(),row[11].toString());
	
			employeesData.add(emp);
		}
		
		graphDataList.add(new BarGraphData("12:00 to 1:30pm", employeesData.size()));
		
		
         sql = new StringBuilder();
        
        sql.append("select * from employee_table as t1 join (select emp.pb_id  as pb_id_temp, max(punch.time) as time,punch.date,punch.punch_slot from employee_table emp join punch_xref_table punch  where emp.pb_id = punch.pb_id and punch.time>'15:30:00' and punch.time <'23:59:59' group by emp.pb_id,punch.date) as t2 where t1.pb_id = t2.pb_id_temp ");
		
		if(!filter.getDate().equals("")) {
			sql.append(" and (t2.date >= '"+filter.getDate()+"' and t2.date <= '"+filter.getEndDate()+"')");
		}
		if(!filter.getProgramCode().equals("")) {
			sql.append(" and t1.program_code = '"+filter.getProgramCode()+"'");
		}
		if(!filter.getPbId().equals("")) {
			sql.append(" and t1.pb_id = '"+filter.getPbId()+"'");
		}

		employeesData = new ArrayList<EmployeeData>();
		
		 query = session.createSQLQuery(sql.toString());
		rows = query.list();
		session.getTransaction().commit();
		for(Object[] row : rows){
			
			EmployeeData emp = new EmployeeData(row[3].toString(), row[0].toString(), null, row[1].toString(), row[2].toString(), row[4].toString(),row[5].toString(), row[9].toString(), row[10].toString(),row[11].toString());
			
			employeesData.add(emp);
		}
		
		graphDataList.add(new BarGraphData("after 3:30pm", employeesData.size()));
        }
        catch(Exception ex) {
        	
        }
        finally {
        	session.close();
        }
 
		
		return graphDataList;
	}

	@Override
	public String getCandidateByRef(String reff_id) {
return "";

}

	@Override
	public List<ProgramCodes> getProgramCodes() {
		// TODO Auto-generated method stub
		Session session = sf.openSession();
		String sql = "SELECT distinct(program_code) FROM employee_table";
		List<Object[]> rows;
		List<ProgramCodes> pragramCodes = new ArrayList<ProgramCodes>();
		 try {
	        	
	        	session.beginTransaction();
	        	SQLQuery query = session.createSQLQuery(sql.toString());
			rows = query.list();
			session.getTransaction().commit();
			for(Object row : rows){
				pragramCodes.add(new ProgramCodes(row.toString(),""));
			}
		 }
		 catch(Exception ex)
		 {
			 
		 }
		 finally {
			 session.close();
		 }
		return pragramCodes;
	}
}
