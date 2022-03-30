package lt.meetingApp.employee;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
	
	public Employee getEmployeeById(Integer id) {
		
		Employee employee = new Employee(
				1,
				"Lukas", 
				"Petraitis");
		
		Employee employee2 = new Employee(
				2,
				"Kukas", 
				"Ketraitis");
		
		Employee employee3 = new Employee(
				3,
				"Bukas", 
				"Betraitis");
		
		Employee employee4 = new Employee(
				4,
				"Bukas", 
				"Betraitis");
		
		List<Employee> employees = new ArrayList();
		employees.add(employee);
		employees.add(employee2);
		employees.add(employee3);
		employees.add(employee4);
		
		
		return employees.stream().filter(emp -> emp.getId() == id).findFirst().get();
	}
}
