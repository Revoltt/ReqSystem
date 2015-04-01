package support;

import java.util.ArrayList;

import support.Location;

public class Requality {
	private String id;
	private ArrayList<Location> locations; 
	
	public Requality() { id = ""; locations = new ArrayList<Location>(); }
	public void setId(String sid) { id = sid; }
	public String getId() { return id; }
	public void addLocation(Location l) { locations.add(l); }
	public ArrayList<Location> getLocationlist() { return locations; }
}
