package support;

import java.util.Stack;
public class MyStack extends Stack<Node> {
	public boolean headerLookUp(Node x)
	{
		@SuppressWarnings("unchecked")
		Stack<Node> s = (Stack<Node>) this.clone();
		while (!s.empty())
		{
			Node temp = s.pop();
			if (temp.getType().equals(x.getType()))
				return true;
		}
		return false;
	}
}
