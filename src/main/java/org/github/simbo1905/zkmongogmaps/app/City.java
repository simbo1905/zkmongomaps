package org.github.simbo1905.zkmongogmaps.app;

import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class City {
	@Id
	private String _id;
	
	private int pop = 0;
	
	private String state = null;
	
	private double[] loc;

	private String city;

	public City(String name, String state, int pop, double lat, double lon) {
		this.city = name;
		this.state = state;
		this.pop = pop;
		this.loc = new double[]{lat,lon};
	}	
	
	public City(String _id, String name, String state, int pop, double[] loc) {
		this._id = _id;
		this.city = name;
		this.state = state;
		this.pop = pop;
		this.loc = loc;
	}
	
	public City() {
		
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getPop() {
		return pop;
	}

	public String getState() {
		return state;
	}

	public void setPop(int pop) {
		this.pop = pop;
	}

	public void setState(String state) {
		this.state = state;
	}

	public double[] getLoc() {
		return loc;
	}

	public float getLng() {
		return (float) loc[0];
	}

	public float getLat() {
		return (float) loc[1];
	}

	public void setLat(float lat) {
		loc[1] = lat;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_id == null) ? 0 : _id.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + Arrays.hashCode(loc);
		result = prime * result + pop;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
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
		City other = (City) obj;
		if (_id == null) {
			if (other._id != null)
				return false;
		} else if (!_id.equals(other._id))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (!Arrays.equals(loc, other.loc))
			return false;
		if (pop != other.pop)
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "City [_id=" + _id + ", pop=" + pop + ", state=" + state
				+ ", loc=" + Arrays.toString(loc) + ", city=" + city + "]";
	}
	
}
