package operations;

import java.util.ArrayList;

import support.ActualLocation;
import support.Location;
import support.Node;
import support.Requality;

public class TextOps {
	public static String nodeTextExtract(Node n) // extracts text of some Node - Header or Requality
	{
		String res = "";
		ArrayList<Node> lst = n.getChildren();
		for (int i = 0; i < lst.size(); i++)
		{
			if (lst.get(i).getType().equals("text"))
			{
				if (!res.endsWith(" ") && !lst.get(i).getText().startsWith(" ") 
					&& !res.endsWith(",") && !lst.get(i).getText().startsWith(",") 
					&& !res.endsWith(".") && !lst.get(i).getText().startsWith(".")
					&& !res.endsWith(";") && !lst.get(i).getText().startsWith(";")
					&& !res.endsWith(":") && !lst.get(i).getText().startsWith(":")
					&& (!res.equals("")))
					res += " ";
				res += lst.get(i).getText();
			} else if (lst.get(i).getType().equals("requality"))
			{
				String s = nodeTextExtract(lst.get(i));
				if (!res.endsWith(" ") && !s.startsWith(" ") 
						&& !res.endsWith(",") && !s.startsWith(",") 
						&& !res.endsWith(".") && !s.startsWith(".")
						&& !res.endsWith(";") && !s.startsWith(";")
						&& !res.endsWith(":") && !s.startsWith(":")
						&& (!res.equals("")))
						res += " ";
				res += s;
			} else
			{
				break;
			}
		}
		return res;
	}
	
	public static String sectionTextExtract(Node n) // extracts text whole section - header and paragraphs
	{
		if (!n.getAllText().equals(""))
			return n.getAllText();
		String res = "";
		ArrayList<Node> lst = n.getChildren();
		for (int i = 0; i < lst.size(); i++)
		{
			if (lst.get(i).getType().equals("text"))
			{
				if (!res.endsWith(" ") && !lst.get(i).getText().startsWith(" ") 
					&& !res.endsWith(",") && !lst.get(i).getText().startsWith(",") 
					&& !res.endsWith(".") && !lst.get(i).getText().startsWith(".")
					&& !res.endsWith(";") && !lst.get(i).getText().startsWith(";")
					&& !res.endsWith(":") && !lst.get(i).getText().startsWith(":")
					&& (!res.equals("")))
					res += " ";
				res += lst.get(i).getText();
			} else if (lst.get(i).getType().equals("requality"))
			{
				String s = sectionTextExtract(lst.get(i));
				if (!res.endsWith(" ") && !s.startsWith(" ") 
						&& !res.endsWith(",") && !s.startsWith(",") 
						&& !res.endsWith(".") && !s.startsWith(".")
						&& !res.endsWith(";") && !s.startsWith(";")
						&& !res.endsWith(":") && !s.startsWith(":")
						&& (!res.equals("")))
						res += " ";
				res += s;
			} else if (!lst.get(i).getType().startsWith("h")) // do we need texts of included sections???
			{
				String s = sectionTextExtract(lst.get(i));
				res += "\n" + s;
			}
		}
		n.setAllText(res);
		return res;
	}

	public static void createActualLocations()
	{
		/// add actual location lists to requalities
		for (int i = 0; i < InterfaceOps.reqs1.size(); i++)
		{
			//if (i == 15)
			//	System.out.println("a");
			Requality curReq = InterfaceOps.reqs1.get(i);
			String curActualLocationText = "";
			Location prevLocation = null;
			for (int j = 0; j < curReq.getLocationlist().size(); j++)
			{
				Location curLocation = curReq.getLocationlist().get(j);
				if ((j != 0) && (prevLocation.getNode().getParent().equals(curLocation.getNode().getParent())) 
						&& (prevLocation.getChildNumber() + 1 == curLocation.getChildNumber()))
				{
					String temp = TextOps.nodeTextExtract(curLocation.getNode());
					if (!curActualLocationText.endsWith(" ") && !temp.startsWith(" "))
						curActualLocationText += " ";
					curActualLocationText += temp;
				} else if (j != 0)
				{
					ActualLocation temp = new ActualLocation(curActualLocationText);
					temp.setPath(InterfaceOps.getLocationPath(prevLocation));
					if (!temp.getText().equals(""))
						curReq.addActualLocation(temp);
					String temps = TextOps.nodeTextExtract(curLocation.getNode());
					curActualLocationText = temps;
				} else
				{
					String temp = TextOps.nodeTextExtract(curLocation.getNode());
					curActualLocationText = temp;
				}
				prevLocation = curLocation;
			}
			ActualLocation temp = new ActualLocation(curActualLocationText);
			temp.setPath(InterfaceOps.getLocationPath(prevLocation));
			if (!temp.getText().equals(""))
				curReq.addActualLocation(temp);
			
			//System.out.println("checked");
		}
	}

	public static String regTransform(String s) {
		//String temp = s.replaceAll("\\s$", "").replaceAll("\\s\n", "\n");
		//temp = temp.replaceAll("\r?\n", " ").replaceAll("\t", " ").replaceAll("\\s{2,}", " ");
		//temp = temp.replaceAll("\\s[\\.]", ".").replaceAll("\\s[,]", ",");
		String temp = s.replaceAll("\\s+$", "");
		temp = temp.replaceAll("\\(", " (");
		temp = temp.replaceAll("\\[", " [");
		temp = temp.replaceAll("\t", " ");
		
		temp = temp.replaceAll("\\.", ". ").replaceAll(",", ", ");
		temp = temp.replaceAll("\\;", "; ").replaceAll(":", ": ");
		
		temp = temp.replaceAll("\\s+\n", "\n");
		temp = temp.replaceAll("\\s{2,}", " ");
		temp.replaceAll("\n{2,}", "\n");
		temp = temp.replaceAll("\\s\\.", ".").replaceAll("\\s,", ",");
		temp = temp.replaceAll("\\s\\;", ";").replaceAll("\\s:", ":");
		temp = temp.replaceAll("\\s\\)", ")").replaceAll("\\(\\s ", "(");
		temp = temp.replaceAll("\\s\\]", "]").replaceAll("\\[\\s ", "[");
		temp = temp.replaceAll("\\s+$", "").replaceAll("^\\s+", "");
		
		return temp;
	}
	
}
