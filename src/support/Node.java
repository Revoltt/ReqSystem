package support;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;

public class Node {
	private String type;
	private String id; // for requality tags
	private boolean a; // for requality tags
	private List<Node> children;
	private Node parent;
	private int num; // number of line of the element
	private String text; // only for text nodes
	private int depth;
	
	public Node(Element e, int i) 
	{
		type = e.getName();
		id = null;
		a = false;
		depth = 0;
		num = i;
		children = new ArrayList<Node>();
		if (type.equals("span") && e.getAttribute("class").toString().startsWith("requality_text"))
		{
			type = "requality";
		}
	}

	public Node() {}
	
	public int getNum() { return num; }
	public void setNum(int d) { num = d; }
	public int getDepth() { return depth; }
	public void setDepth(int d) { depth = d; }
	public Node getParent() { return parent; }
	public void setParent(Node p) { parent = p; }
	public String getText() { return text; }
	public void setText(String l) { text = l; }
	public String getId() { return id; }
	public void setId(String i) { id = i; }
	public boolean getA() { return a; }
	public void setA(boolean i) { a = i; }
	public String getType() { return type; }
	public void setType(String t) { type = t; }
	public List<Node> getChildren() { return children; }
	public void addChild(Node x) { children.add(x); }
	public void clearChildren() {children = null; }
}