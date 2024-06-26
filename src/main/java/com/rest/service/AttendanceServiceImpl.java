package com.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.DAO.AttendanceDao;
import com.rest.hr.models.HrEmployeeAttendanceData;
import com.rest.hr.models.HrEmployeeDashBoardData;
import com.rest.hr.models.HrEmployeeDataFilter;
import com.rest.hr.models.HrFullEmployeeData;
import com.rest.models.BarGraphData;
import com.rest.models.Customfilter;
import com.rest.models.Employee;
import com.rest.models.EmployeeData;
import com.rest.models.FullDataRegistration;
import com.rest.models.FullUIDataObject;
import com.rest.models.PieGraphData;
import com.rest.models.ProgramCodes;
import com.rest.models.Punch;
import com.rest.models.PunchModel;
import com.rest.models.SignUp;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	@Autowired
	AttendanceDao dao;

	@Override
	public FullDataRegistration saveEmployee(Employee emp) {
		// TODO Auto-generated method stub
		return dao.saveEmployee(emp);
	}

	@Override
	public PunchModel savepunchIn(String reffId) {
		// TODO Auto-generated method stub
		return dao.savePunchIn(reffId);
	}

	@Override
	public List<Punch> getDataByDate(String date) {
		// TODO Auto-generated method stub
		return dao.getDataByDate(date);
	}

	@Override
	public FullUIDataObject getDataByFilter(Customfilter filter) {
		// TODO Auto-generated method stub
		return dao.getDataByFilter(filter);
	}

	@Override
	public PunchModel getPunchData(String filter) {
		// TODO Auto-generated method stub
		return dao.getPunchData(filter);
	}

	@Override
	public boolean makeEmployeeInvalidIfExist(Employee emp) {
		// TODO Auto-generated method stub
		return dao.makeEmployeeInvalidIfExist(emp);
	}

	@Override
	public boolean makeEmployeePunchInvalidIfExist(Employee emp) {
		// TODO Auto-generated method stub
		return dao.makeEmployeePunchInvalidIfExist(emp);
	}

	@Override
	public boolean signUp(SignUp userRegistrationDto) {
		// TODO Auto-generated method stub
		return dao.signUp(userRegistrationDto);
	}

	@Override
	public SignUp signIn(SignUp userRegistrationDto) {
		// TODO Auto-generated method stub
		return dao.signIn(userRegistrationDto);
	}

	@Override
	public PieGraphData getGraphDataByFilter(Customfilter filter) {
		// TODO Auto-generated method stub
		return dao.getGraphDataByFilter(filter);
	}

	@Override
	public List<BarGraphData> getBarGraphDataByFilter(Customfilter filter) {
		// TODO Auto-generated method stub
		return dao.getBarGraphDataByFilter(filter);
	}

	@Override
	public String getCandidateByRef(String reff_id) {
		// TODO Auto-generated method stub
		return dao.getCandidateByRef(reff_id);
	}

	@Override
	public List<ProgramCodes> getProgramCodes() {
		// TODO Auto-generated method stub
		return dao.getProgramCodes();
	}

	@Override
	public List<EmployeeData> getAttendanceDataByFilter(Customfilter filter) {
		// TODO Auto-generated method stub
		return dao.getAttendanceDataByFilter(filter);
	}

	@Override
	public HrEmployeeDashBoardData getHrEmployeeData(HrEmployeeDataFilter filter) {
		// TODO Auto-generated method stub
		return dao.getHrEmployeeData(filter);
	}

	@Override
	public boolean hrDivisionDataUpload(List<HrEmployeeAttendanceData> data) {
		// TODO Auto-generated method stub
		return dao.hrDivisionDataUpload(data);
	}

	@Override
	public boolean updateHrEmployeeDashBoardHmaCanteenDays(HrEmployeeDataFilter data) {
		// TODO Auto-generated method stub
		return dao.updateHrEmployeeDashBoardHmaCanteenDays(data);
	}

	@Override
	public HrEmployeeDashBoardData getHrAdminData(HrEmployeeDataFilter filter) {
		// TODO Auto-generated method stub
		return dao.getHrAdminData(filter);
	}

}
