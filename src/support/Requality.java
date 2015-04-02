package support;

import java.util.ArrayList;

import support.Location;

public class Requality {
	private String id;
	private ArrayList<Location> locations; 
	private ArrayList<String> bigLocations;
	public Requality() { id = ""; locations = new ArrayList<Location>(); bigLocations = new ArrayList<String>(); }
	public void setId(String sid) { id = sid; }
	public String getId() { return id; }
	public void addLocation(Location l) { locations.add(l); }
	public void addBigLocation(String s) { bigLocations.add(s); }
	public ArrayList<Location> getLocationlist() { return locations; }
	public ArrayList<String> getBigLocationlist() { return bigLocations; }
}
