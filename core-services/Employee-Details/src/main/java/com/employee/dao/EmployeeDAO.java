package com.employee.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.employee.model.Employee;

@Component
public class EmployeeDAO {
	
	// Dummy database. Initialize with some dummy values.
		private static List<Employee> employees;
		{
			employees = new ArrayList();
			employees.add(new Employee(1, "Siddharth", "Hyderabad", "Telugu"));
			employees.add(new Employee(2, "Ranjay", "Bangalore", "Hindi"));
			employees.add(new Employee(3, "Thomas", "Kerala", "Malayalam"));
		}

	// Returns list of employees from dummy database
		public List<Employee> list() {
			return employees;
		}

   // Return employees object for given id from dummy database. If employees is present returns data or else null is returned
		public Employee get(int id) {

			for (Employee c : employees) {
				if (c.getId()==(id)) {
					return c;
				}
			}
			return null;
		}	 

		

}
