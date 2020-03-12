package dataProviders;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author madhubabu
 * @date 12-Mar-2020
 * @desc TODO
 */
public class XMLFileReader 
{
	public static void readXML(String xmlFilePath) throws Exception
	{
		try 
		{
			File fXmlFile = new File(xmlFilePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
					
			doc.getDocumentElement().normalize();

			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
					
			NodeList nList = doc.getElementsByTagName("staff");
					
			Node nNode = nList.item(0);	
			nNode.getNodeName();
			Element eElement = (Element) nNode;
			System.out.println(eElement);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
