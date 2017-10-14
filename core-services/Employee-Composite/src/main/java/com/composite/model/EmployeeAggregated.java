package com.composite.model;

import java.util.List;

public class EmployeeAggregated {
	 int id;
	 String name, city, language;
	 List<RatingModel> rating;
	 List<SalaryModel> salary;
	 
	 
	public EmployeeAggregated(int id, String name, String city, String language, List<RatingModel> rating,
			List<SalaryModel> salary) {
		super();
		this.id = id;
		this.name = name;
		this.city = city;
		this.language = language;
		this.rating = rating;
		this.salary = salary;
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
	public List<RatingModel> getRating() {
		return rating;
	}
	public void setRating(List<RatingModel> rating) {
		this.rating = rating;
	}
	public List<SalaryModel> getSalary() {
		return salary;
	}
	public void setSalary(List<SalaryModel> salary) {
		this.salary = salary;
	}
	

}
