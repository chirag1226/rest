package com.rest.DAO;

import java.util.List;

import com.rest.models.BarGraphData;
import com.rest.models.Customfilter;
import com.rest.models.Employee;
import com.rest.models.EmployeeData;
import com.rest.models.PieGraphData;
import com.rest.models.ProgramCodes;
import com.rest.models.Punch;
import com.rest.models.PunchModel;
import com.rest.models.SignUp;

public interface AttendanceDao {


	public boolean saveEmployee(Employee emp);

	public boolean savePunchIn(String reffId, String pbId);

	public List<Punch> getDataByDate(String date);

	public List<EmployeeData> getDataByFilter(Customfilter filter);

	public PunchModel getPunchData(String filter);

	public boolean makeEmployeeInvalidIfExist(Employee emp);

	public boolean signUp(SignUp userRegistrationDto);

	public SignUp signIn(SignUp userRegistrationDto);

	public PieGraphData getGraphDataByFilter(Customfilter filter);

	public List<BarGraphData> getBarGraphDataByFilter(Customfilter filter);

	boolean makeEmployeePunchInvalidIfExist(Employee emp);

	public String getCandidateByRef(String reff_id);

	public List<ProgramCodes> getProgramCodes();
}
