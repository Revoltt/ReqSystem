package support;

public class Location {
	private Node src;
	
	public Location(Node s) { src = s; }
	public Node getNode() { return src; }
	public int getChildNumber()
	{
		Node p = src.getParent();
		for (int i = 0; i < p.getChildren().size(); i++)
		{
			if (p.getChildren().get(i).equals(src))
				return i;
		}
		return -1;
	}
}
