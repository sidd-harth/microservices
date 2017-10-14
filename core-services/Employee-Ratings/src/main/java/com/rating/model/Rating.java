package com.rating.model;

public class Rating {
		
		int id;
		String level, type, short_type;
		

		public Rating(int id, String level, String type, String short_type) {
			super();
			this.id = id;
			this.level = level;
			this.type = type;
			this.short_type = short_type;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getLevel() {
			return level;
		}
		public void setLevel(String level) {
			this.level = level;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getShort_type() {
			return short_type;
		}
		public void setShort_type(String short_type) {
			this.short_type = short_type;
		}

}