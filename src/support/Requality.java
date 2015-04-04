package support;

import java.util.ArrayList;

import support.Location;

public class Requality {
	private String id;
	private ArrayList<Location> locations;
	private ArrayList<String> actualLocations;
	public Requality() { id = ""; locations = new ArrayList<Location>();  actualLocations = new ArrayList<String>(); }
	public void setId(String sid) { id = sid; }
	public String getId() { return id; }
	public void addLocation(Location l) { locations.add(l); }
	public ArrayList<Location> getLocationlist() { return locations; }
	public void addActualLocation(String l) { actualLocations.add(l); }
	public ArrayList<String> getActualLocationlist() { return actualLocations; }
}
