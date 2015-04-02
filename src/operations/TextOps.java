package operations;

import java.util.ArrayList;

import support.Node;

public class TextOps {
	public static String littleTextExtractor(Node n) // extracts text of some Node - Header or Requality
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
					&& (!res.equals("")))
					res += " ";
				res += lst.get(i).getText();
			} else if (lst.get(i).getType().equals("requality"))
			{
				String s = littleTextExtractor(lst.get(i));
				if (!res.endsWith(" ") && !s.startsWith(" ") 
						&& !res.endsWith(",") && !s.startsWith(",") 
						&& !res.endsWith(".") && !s.startsWith(".")
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
}
