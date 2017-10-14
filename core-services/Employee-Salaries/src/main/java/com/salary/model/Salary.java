package com.salary.model;

public class Salary {
		
		int id, monthly, variable, ctc;

		public Salary(int id, int monthly, int variable, int ctc) {
			super();
			this.id = id;
			this.monthly = monthly;
			this.variable = variable;
			this.ctc = ctc;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getMonthly() {
			return monthly;
		}

		public void setMonthly(int monthly) {
			this.monthly = monthly;
		}

		public int getVariable() {
			return variable;
		}

		public void setVariable(int variable) {
			this.variable = variable;
		}

		public int getCtc() {
			return ctc;
		}

		public void setCtc(int ctc) {
			this.ctc = ctc;
		}
		
		


}