package com.rating.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.rating.model.Rating;

@Component
public class RatingDAO {
	
	// Dummy database. Initialize with some dummy values.
		private static List<Rating> ratings;
		{
			ratings = new ArrayList<Rating>();
			ratings.add(new Rating(1, "Level 5", "Distinguished Performance", "DP"));
			ratings.add(new Rating(2, "Level 3", "Good Performance", "GP"));
			ratings.add(new Rating(3, "Level 1", "Performance Needs Improvement", "PNI"));
		}

	// Returns list of ratings from dummy database
		public List<Rating> list() {
			return ratings;
		}

   // Return ratings object for given id from dummy database. If employees is present returns data or else null is returned
		public Rating get(int id) {

			for (Rating c : ratings) {
				if (c.getId()==(id)) {
					return c;
				}
			}
			return null;
		}	 

		

}
