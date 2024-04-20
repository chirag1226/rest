package com.rest.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.models.BarGraphData;
import com.rest.models.Customfilter;
import com.rest.models.Employee;
import com.rest.models.EmployeeData;
import com.rest.models.FileNamePath;
import com.rest.models.FullDataRegistration;
import com.rest.models.FullUIDataObject;
import com.rest.models.PieGraphData;
import com.rest.models.ProgramCodes;
import com.rest.models.Punch;
import com.rest.models.PunchModel;
import com.rest.models.SignUp;
import com.rest.service.AttendanceService;

@RestController
@RequestMapping("/hal-hma-attendance/")
public class HomeController {

	@Autowired
	AttendanceService service;

	@CrossOrigin(origins = "*")
	@GetMapping("")
	public List<FileNamePath> home() {

		// return new Employee("fn","mn","ln","add","det","1222",new RfId("232432") );
		return this.test();
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = "addemployee", consumes = "application/json")
	public FullDataRegistration punchIn(@RequestBody Employee emp) {
		service.makeEmployeeInvalidIfExist(emp);
		service.makeEmployeePunchInvalidIfExist(emp);

		return service.saveEmployee(emp);
	}

	@CrossOrigin(origins = "*")
	@PostMapping("{reff_id}")
	public PunchModel punchIn(@PathVariable String reff_id) {
		PunchModel emp = service.getPunchData(reff_id);
		if (emp != null && service.savepunchIn(reff_id, emp.getPbId())) {
			return emp;
		} else {
			return new PunchModel(false, "Please try again", "");
		}
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = "getDataByDate")
	public List<Punch> getDataByDate(@RequestBody String date)

	{
		return service.getDataByDate(date);
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = "getDataByFilter")
	public FullUIDataObject getDataByFilter(@RequestBody Customfilter filter)

	{
		return service.getDataByFilter(filter);
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = "/signUp")
	public boolean signUp(@RequestBody SignUp userRegistrationDto) {
		return service.signUp(userRegistrationDto);
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = "/signIn")
	public SignUp signIn(@RequestBody SignUp userRegistrationDto) {
		return service.signIn(userRegistrationDto);
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = "getPieGraphDataByFilter")
	public PieGraphData getGraphDataByFilter(@RequestBody Customfilter filter)

	{
		return service.getGraphDataByFilter(filter);
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = "getBarGraphDataByFilter")
	public List<BarGraphData> getBarGraphDataByFilter(@RequestBody Customfilter filter)

	{
		return service.getBarGraphDataByFilter(filter);

	}

	/*
	 * public class ReadFilesFromDirectory { public static void main(String[] args)
	 * { // Directory path String directoryPath = "C:\\Users\\nishi\\Desktop";
	 * 
	 * // Creating a File object with the directory path File directory = new
	 * File(directoryPath);
	 * 
	 * // Checking if the provided path is a directory if (directory.isDirectory())
	 * { // Getting list of all files in the directory File[] files =
	 * directory.listFiles();
	 * 
	 * // Iterating through the files and printing their names if (files != null) {
	 * for (File file : files) { System.out.println(file.getName()); } } } else {
	 * System.out.println("The provided path is not a directory."); } } }
	 */

	public static List<FileNamePath> test() {

		// Directory path

		// String directoryPath = "C:\\Users\\nishi\\Desktop";

		// Get the path to the desktop directory on the C drive

		String directoryPath = "C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\files";

		// Creating a File object with the directory path

		File directory = new File(directoryPath);
		List<FileNamePath> objs = new ArrayList<FileNamePath>();

		// Checking if the provided path is a directory

		if (directory.isDirectory()) {

			// Getting list of all files in the directory

			File[] files = directory.listFiles();

			// Iterating through the files and printing their names and paths

			if (files != null) {

				for (File file : files) {
					objs.add(new FileNamePath(file.getName(), file.getAbsolutePath()));

					System.out.println("File Name: " + file.getName());

					System.out.println("File Path: " + file.getAbsolutePath());

					System.out.println(); // Adding an empty line for clarity

				}

			}

		} else {

			System.out.println("The provided path is not a directory.");

		}
		return objs;
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = "getProgramCodes")
	public List<ProgramCodes> getProgramCodes()

	{
		return service.getProgramCodes();
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = "getAttendanceDataByFilter")
	public List<EmployeeData> getAttendanceDataByFilter(@RequestBody Customfilter filter)

	{
		return service.getAttendanceDataByFilter(filter);
	}

}
