package support;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;

public class Tree {
	private Node root;
	private int counter = 0;
	public PrintStream out;
	private MyStack headers = new MyStack();
	
	public Node getRoot()
	{
		return root;
	}
	
	public void printJDOMTree(Element e, String space)  
	{
		if (!e.getName().equals("font"))
			out.println(space + e.getName());
		List<Content> c = e.getContent();
		for (int i = 0; i < c.size(); i++)
		{
			if (c.get(i).getClass().equals(Element.class))
			{
				printJDOMTree((Element)c.get(i), space + " ");
			}
			else
			{
				out.println(c.get(i).getValue());
			}
		}
	}

	
	public void printMyTree(Node e, String space) 
	{
		out.print(space + e.getType() + " " + e.getNum());
		if (e.getType().equals("requality"))
		{
			out.print(" " + e.getA() + " " + e.getId());
		}
		out.println();
		List<Node> c = e.getChildren();
		for (int i = 0; i < c.size(); i++)
		{
			if (!c.get(i).getType().equals("text"))
			{
				printMyTree(c.get(i), space + " ");
			}
			else
			{
				out.println(space + "text");
				out.println(c.get(i).getText());
			}
		}
	}	

	private void makeTree(Element e, Node cur)
	{
		List<Content> c = e.getContent();
		boolean aTag = true;
		for (int i = 0; i < c.size(); i++)
		{
			if (c.get(i).getClass().equals(Element.class))
			{
				if (aTag && ((Element)c.get(i)).getName().equals("a") && cur.getType().equals("requality"))
				{
					cur.setA(true);
					aTag = false;
				} 
				else if (((Element)c.get(i)).getName().equals("font") || 
						((Element)c.get(i)).getName().equals("i") || 
						((Element)c.get(i)).getName().equals("b") || 
						((Element)c.get(i)).getName().equals("blockquote") || 
						((Element)c.get(i)).getName().equals("a") ||
						((Element)c.get(i)).getName().equals("sup") ||
						((Element)c.get(i)).getName().equals("img") || 
						((Element)c.get(i)).getName().equals("br"))
					makeTree((Element)c.get(i), cur);
				else if (((Element)c.get(i)).getName().equals("span") && 
						(((Element)c.get(i)).getAttribute("class") != null) &&
						((Element)c.get(i)).getAttribute("class").getValue().startsWith("requality_text"))
				{
					Node temp = new Node((Element)c.get(i), counter);
					counter++;
					temp.setType("requality");
					temp.setId(((Element)c.get(i)).getAttribute("class").getValue().substring(18));
					temp.setParent(cur);
					temp.setDepth(cur.getDepth() + 1);
					cur.addChild(temp);
					makeTree((Element)c.get(i), temp);
				}
				else
				{
					Node temp = new Node((Element)c.get(i), counter);
					counter++;
					temp.setParent(cur);
					temp.setDepth(cur.getDepth() + 1);
					if (temp.getType().startsWith("h"))
						if (!headers.headerLookUp(temp))
						{
							cur.addChild(temp);
							makeTree((Element)c.get(i), temp);
							headers.push(temp);
							cur = temp;
						} else
						{
							Node tempHeader = headers.pop();
							while (!temp.getType().equals(tempHeader.getType()))
							{
								tempHeader = headers.pop();
							}
							cur = tempHeader.getParent();
							temp.setParent(cur);
							temp.setDepth(cur.getDepth() + 1);
							cur.addChild(temp);
							makeTree((Element)c.get(i), temp);
							headers.push(temp);
							cur = temp;
						}
					else 
					{
						cur.addChild(temp);
						makeTree((Element)c.get(i), temp);
					}
				}
			}
			else
			{
				Node temp = new Node();
				temp.setParent(cur);
				temp.setDepth(cur.getDepth() + 1);
				temp.setType("text");
				temp.setText(c.get(i).getValue());
				cur.addChild(temp);
			}
		}
	}
	
	public void makeTreeFromDoc(Document doc) throws FileNotFoundException
	{
		//out = new PrintStream("tagsMy.txt");
		Element r = doc.getRootElement();
		root = new Node(r, counter);
		counter++;
		makeTree(r, root);
		//printMyTree(root,"");
		//out = new PrintStream("tags.txt");
		//printJDOMTree(r,"");
	}
}
