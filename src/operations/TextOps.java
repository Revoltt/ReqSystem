package operations;

import java.util.ArrayList;

import support.Node;

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
			} else
			{
				String s = sectionTextExtract(lst.get(i));
				res += "\n" + s;
			}
		}
		return res;
	}

}
