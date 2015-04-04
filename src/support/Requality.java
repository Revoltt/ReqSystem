package support;

import java.util.ArrayList;

import support.Location;

public class Requality {
	private String id;
	private ArrayList<Location> locations;
	private ArrayList<ActualLocation> actualLocations;
	public Requality() { id = ""; locations = new ArrayList<Location>();  actualLocations = new ArrayList<ActualLocation>(); }
	public void setId(String sid) { id = sid; }
	public String getId() { return id; }
	public void addLocation(Location l) { locations.add(l); }
	public ArrayList<Location> getLocationlist() { return locations; }
	public void addActualLocation(ActualLocation l) { actualLocations.add(l); }
	public ArrayList<ActualLocation> getActualLocationlist() { return actualLocations; }
}
