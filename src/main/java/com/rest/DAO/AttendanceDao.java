package com.rest.DAO;

import java.util.List;

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

public interface AttendanceDao {

	public FullDataRegistration saveEmployee(Employee emp);

	public PunchModel savePunchIn(String reffId);

	public List<Punch> getDataByDate(String date);

	public FullUIDataObject getDataByFilter(Customfilter filter);

	public PunchModel getPunchData(String filter);

	public boolean makeEmployeeInvalidIfExist(Employee emp);

	public boolean signUp(SignUp userRegistrationDto);

	public SignUp signIn(SignUp userRegistrationDto);

	public PieGraphData getGraphDataByFilter(Customfilter filter);

	public List<BarGraphData> getBarGraphDataByFilter(Customfilter filter);

	boolean makeEmployeePunchInvalidIfExist(Employee emp);

	public String getCandidateByRef(String reff_id);

	public List<ProgramCodes> getProgramCodes();

	public List<EmployeeData> getAttendanceDataByFilter(Customfilter filter);

	public HrEmployeeDashBoardData getHrEmployeeData(HrEmployeeDataFilter filter);

	public boolean hrDivisionDataUpload(List<HrEmployeeAttendanceData> data);

	public boolean updateHrEmployeeDashBoardHmaCanteenDays(HrEmployeeDataFilter data);

	public HrEmployeeDashBoardData getHrAdminData(HrEmployeeDataFilter filter);
}
