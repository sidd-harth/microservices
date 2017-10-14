package com.salary.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.salary.model.Salary;

@Component
public class SalaryDAO {
	
	// Dummy database. Initialize with some dummy values.
		private static List<Salary> salaries;
		{
			salaries = new ArrayList<Salary>();
			salaries.add(new Salary(1, 30000, 40000, 400000));
			salaries.add(new Salary(2, 40000, 20000, 500000));
			salaries.add(new Salary(3, 45000, 60000, 600000));
		}

	// Returns list of salaries from dummy database
		public List<Salary> list() {
			return salaries;
		}

   // Return salary object for given id from dummy database. If employees is present returns data or else null is returned
		public Salary get(int id) {

			for (Salary c : salaries) {
				if (c.getId()==(id)) {
					return c;
				}
			}
			return null;
		}	 

		

}
