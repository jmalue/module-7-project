package org.wcci.reviews;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Review {

	@Id
	@GeneratedValue
	private long id;

	private String name;
	private String description;

	@ManyToMany
	private Collection<Album> albums;
	
	@OneToMany(mappedBy = "review")
	private Collection<Track> track;

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Review() {
	}

	public Collection<Track> getTrack() {
		return track;
	}

	public Review(String name, String description, Album... albums) {
		this.name = name;
		this.description = description;
		this.albums = new HashSet<>(Arrays.asList(albums));
	}

	public Collection<Album> getAlbums() {
		return albums;
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
		Review other = (Review) obj;
		if (id != other.id)
			return false;
		return true;
	}


}
