package support;

import java.util.ArrayList;

public class ActualLocation {
	private String text;
	private ArrayList<Node> path;
	private int pos; // used only when restoring locations by actual location
	
	public ActualLocation(String t) { text = t; pos = 0; }
	public void setPos(int p) { pos = p; }
	public int getPos() {return pos; }
	public void setText(String t) { text = t; }
	public String getText() { return text; }
	public void setPath(ArrayList<Node> p) { path = p; }
	public ArrayList<Node> getPath() { return path; }
}
