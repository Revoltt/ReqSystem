package support;

import java.util.ArrayList;

public class ActualLocation {
	private String text;
	private ArrayList<Node> path;
	
	public ActualLocation(String t) { text = t; }
	public void setText(String t) { text = t; }
	public String getText() { return text; }
	public void setPath(ArrayList<Node> p) { path = p; }
	public ArrayList<Node> getPath() { return path; }
}
