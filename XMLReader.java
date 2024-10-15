import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XMLReader
{
    public XMLReader() {}


    public static Document convertStringToDocument(String xmlStr)
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        try
        {
            builder = factory.newDocumentBuilder();
            return builder.parse( new InputSource( new StringReader(xmlStr)));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> ReadXMLString(String xmlString)
    {
        Document document;

        NodeList nodelist;
        Element root, element;
        List<String> tags = new ArrayList<>();

        try 
        {
            document = convertStringToDocument(xmlString);

            assert document != null;
            root = document.getDocumentElement();
            System.out.println("La radice : " + root.getNodeName());

            nodelist = root.getElementsByTagName("laboratorio");

            if(nodelist.getLength() > 0)
            {
                for(int i = 0; i < nodelist.getLength(); i++) 
                {
                    element = (Element)nodelist.item(i);

                    String tag;
                    tag = "laboratorio: " + element.getAttribute("nome") + "\n";

                    NodeList switchNodeList = element.getElementsByTagName("switch");

                    for(int j=0; j < switchNodeList.getLength(); j++)
                    {
                        Element switchNode = (Element)switchNodeList.item(j);

                        tag += "\tswitch (cod: " + switchNode.getAttributes().getNamedItem("cod").getTextContent() + "): " + switchNode.getAttributes().getNamedItem("mac").getTextContent() + "\n";
                        tag += "\t\tmarca: " + switchNode.getElementsByTagName("marca").item(0).getTextContent() + "\n";
                        tag += "\t\tmodello: " + switchNode.getElementsByTagName("modello").item(0).getTextContent() + "\n";
                        tag += "\t\tnporte: " + switchNode.getElementsByTagName("nporte").item(0).getTextContent() + "\n";
                        tag += "\t\tstato: " + switchNode.getElementsByTagName("stato").item(0).getTextContent() + "\n";
                        tag += "\t\tlivello: " + switchNode.getElementsByTagName("livello").item(0).getTextContent() + "\n";


                        NodeList PcNodeList = switchNode.getElementsByTagName("pc");

                        if(PcNodeList.getLength()==0)
                            break;

                        tag += "\t\tPc collegati: \n";
                        for(int a=0; a < PcNodeList.getLength(); a++)
                        {
                            Element pcNode = (Element)PcNodeList.item(a);

                            tag += "\t\t\tNome: " + pcNode.getAttributes().getNamedItem("nome").getTextContent() + "; mac: " + pcNode.getAttributes().getNamedItem("mac").getTextContent() + "; IP: " + pcNode.getAttributes().getNamedItem("ipAddr").getTextContent() + "\n";
                            tag += "\t\t\tmarca: " + pcNode.getElementsByTagName("marca").item(0).getTextContent() + "\n";
                            tag += "\t\t\tmodello: " + pcNode.getElementsByTagName("modello").item(0).getTextContent() + "\n";
                            tag += "\t\t\tvelocità processore: " + pcNode.getElementsByTagName("vproc").item(0).getTextContent() + "GHz\n";
                            tag += "\t\t\tram: " + pcNode.getElementsByTagName("ram").item(0).getTextContent() + "GB\n";
                            tag += "\t\t\tdisco rigido: " + pcNode.getElementsByTagName("disco").item(0).getTextContent() + "GB\n";
                        }
                    }
                    tags.add(tag);
                }
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return tags;
    }
}