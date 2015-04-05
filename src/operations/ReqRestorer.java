package operations;

import java.util.ArrayList;

import support.ActualLocation;
import support.Requality;

public class ReqRestorer {
	
	public static void restoreActualLocation(Requality r, ActualLocation l)
	{
		int index = ReqExtractor.isFound(r.getId(), InterfaceOps.reqs2);
		if ( index == -1)
		{
			Requality cur = new Requality();
			cur.addActualLocation(l);
			InterfaceOps.reqs2.add(cur);
		} else
		{
			InterfaceOps.reqs2.get(index).addActualLocation(l);
		}
	}
}
