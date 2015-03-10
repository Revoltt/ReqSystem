package support;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.output.*;

public class DOMStructBuilder {
	public static Document getDocumentFromFile(String filename)
	{
		try {
            SAXBuilder parser = new SAXBuilder();
            parser.setIgnoringBoundaryWhitespace(true);
            FileReader fr = new FileReader(filename);
            Document rDoc = parser.build(fr);
            Element body = rDoc.getRootElement().getChildren().get(1);
            List<Content> c = body.cloneContent();
            Element root = new Element("body");
            for (int i = 0; i < c.size(); i++)
            {
            	root.addContent(c.get(i).detach());
            }
            Document res = new Document(root);
            return res;
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
		return null;
	}
	
	public static void main(String[] args) throws IOException
	{
		Document test = getDocumentFromFile("Draft_ETSI_TS_103 097 v1.1.12.xhtml");
		Tree t = new Tree();
		t.makeTreeFromDoc(test);
		XMLOutputter x = new XMLOutputter(Format.getPrettyFormat());
		PrintStream out = new PrintStream("output.txt");
		x.output(test, out);
	}
}
