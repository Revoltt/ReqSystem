package operations;

import java.util.ArrayList;

import support.ActualLocation;
import support.Location;
import support.Node;
import support.Requality;

public class ReqRestorer {
	
	public static void restoreActualLocation(Requality r, ActualLocation l)
	{
		int index = ReqExtractor.isFound(r.getId(), InterfaceOps.reqs2);
		if ( index == -1)
		{
			Requality cur = new Requality();
			cur.setId(r.getId());
			cur.addActualLocation(l);
			InterfaceOps.reqs2.add(cur);
		} else
		{
			InterfaceOps.reqs2.get(index).addActualLocation(l);
		}
	}
	
	private static String letters = new String("abcdefghijklmnopqrstuvwxyz0123456789"); 
	private static int letterCount(String s)
	{
		String temp = s.toLowerCase();
		int x = 0;
		for (int i = 0; i < temp.length(); i++)
			if (letters.contains("" + temp.charAt(i)))
				x++;
		return x;
	}
	
	private static Node findLowestSection(ArrayList<Node> path)
	{
		int i = path.size() - 1;
		while (i >= 0)
		{
			if (path.get(i).getType().startsWith("h") || path.get(i).getType().equals("body"))
				break;
			i--;
		}
		return path.get(i);
	} 
	
	private static void addLocationInTextNode(Node x, int start, int finish)
	{
		String text = x.getText();
		int i = 0;
		int startPos = 0;
		int endPos = 0;
		int letterCounter = 0;
		while (true)
		{
			if (letterCounter == start)
				startPos = i;
			if (letterCounter == finish)
			{
				endPos = i - 1;
				break;
			}
			if (i == text.length())
			{
				endPos = i - 1;
				break;
			}
			endPos = i;
			if (letters.contains("" + text.toLowerCase().charAt(i)))
			{
				letterCounter++;
			}
			i++;
		}
		
		// i - index where we should cut the node text
		x.setText(text.substring(0, startPos));
		// add a requality node right after this
		int pos = x.getChildNumber() + 1;
		Node n;
		if (startPos == 0)
		{
			n = x;
			pos--;
		} else
			n = new Node();
		n.setParent(x.getParent());
		n.setDepth(x.getDepth());
		n.setType("requality");
		n.setId(curReq.getId());
		n.setText("");
		if (curReq.getLocationlist().size() == 0)
			n.setA(true);
		else
			n.setA(false);
		if (startPos != 0)
			x.getParent().insertChild(n, pos);
		// add a text node to requality node
		Node t = new Node();
		t.setParent(n);
		t.setDepth(n.getDepth() + 1);
		t.setText(text.substring(startPos, endPos + 1));
		t.setType("text");
		n.addChild(t);
		// add the rest of the text after requality, if there is any
		text = text.substring(endPos + 1);
		if (!text.equals(""))
		{
			t = new Node();
			t.setText(text);
			t.setDepth(n.getDepth());
			t.setParent(n.getParent());
			t.setType("text");
			t.getParent().insertChild(t, pos + 1);
		}
	}
	
	private static int beforeLocationLetters = 0;
	private static int curLetterCount = 0;
	private static int locationLetters = 0;
	private static void goTree(Node x)
	{
		if (curLetterCount >= beforeLocationLetters + locationLetters)
		{
		}else if (!x.getType().equals("text"))
		{
			// if x is not text, then go to it's children
			for (int i = 0; i < x.getChildren().size(); i++)
			{
				goTree(x.getChildren().get(i));
			}
		} else
		{
			// x is text => we need to decide whether we skip it or not
			int nodeLetters = letterCount(x.getText());
			if (curLetterCount + nodeLetters <= beforeLocationLetters)
			{
				//skipping
				curLetterCount += nodeLetters;
			} else if (curLetterCount + nodeLetters >= beforeLocationLetters + locationLetters)
			{
				// all location is in one text Node
				// we need to separate this text node in three parts
				// before the location, the location itself, and after
				addLocationInTextNode(x, beforeLocationLetters - curLetterCount, beforeLocationLetters - curLetterCount + locationLetters);
				curLetterCount += nodeLetters;
			}
		}
	}
	
	public static void restoreActualLocationInTree(ActualLocation src)
	{
		// count the amount of letters in section before the start of location
		String section = TransferManager.extractLowestSectionText(src.getPath());
		String text = TextOps.regTransform(section);
		if (text.indexOf(src.getText()) == src.getPos())
		{
			System.out.println("Nice");
		}
		String textPart = text.substring(0, src.getPos());
		curLetterCount = 0;
		locationLetters = letterCount(src.getText());
		beforeLocationLetters = letterCount(textPart); // number of letters before the start of actual location
		// use DFS to walk in the section of the tree
		goTree(findLowestSection(src.getPath()));
		
	}
	
	private static Requality curReq;
	public static void restoreLocationsInTree()
	{
		for (int i = 0; i < InterfaceOps.reqs2.size(); i++)
		{
			curReq = InterfaceOps.reqs2.get(i);
			for (int j = 0; j < curReq.getActualLocationlist().size(); j++)
			{
				restoreActualLocationInTree(curReq.getActualLocationlist().get(j));
			}
		}
	}
}
