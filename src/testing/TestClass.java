package testing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import operations.InterfaceOps;

public class TestClass {
public static void testPosix() throws IOException
{
	@SuppressWarnings("resource")
	BufferedReader reader = new BufferedReader(new FileReader("POSIX_NAMES.txt"));
    String line;
    ArrayList<String> posix = new ArrayList<String>();
    while ((line = reader.readLine()) != null) {
        posix.add(line);
    }
    String res = "0 0";
	for (int i = 0; i < posix.size(); i++)
	{
		String[] cur = InterfaceOps.run("posix/v3/" + posix.get(i), "posix/v4/" + posix.get(i)).split(" ");
		String[] temp = res.split(" ");
		int tr = Integer.valueOf(temp[0])+ Integer.valueOf(cur[0]);
		int all = Integer.valueOf(temp[1])+ Integer.valueOf(cur[1]);
		res = tr + " " + all;
	}
	System.out.println(res);
}


}
