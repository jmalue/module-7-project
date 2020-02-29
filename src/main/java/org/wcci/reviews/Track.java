package org.wcci.reviews;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Track {

	@Id
	@GeneratedValue
	private long id;

	private String title;

	@ManyToOne
	private Review review;
	
	

	public Track() {

	}

	public Track(String title, Review review) {
		this.title = title;
		this.review = review;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Track other = (Track) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
	

}
