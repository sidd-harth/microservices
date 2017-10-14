package com.employee.model;

public class Employee {
		
		int id;
		String name, city, language;
		
		public Employee(int id, String name, String city, String language) {
			super();
			this.id = id;
			this.name = name;
			this.city = city;
			this.language = language;
		}
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getLanguage() {
			return language;
		}
		public void setLanguage(String language) {
			this.language = language;
		}

}