import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        String fileName = "data.xml";
        List<Employee> list = parseXML(fileName);
        String json = listToJson(list);
        writeString(json);
    }

    private static void writeString(String json) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("data.json"))) {
            writer.write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String listToJson(List list) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        String json = gson.toJson(list);
        return json;
    }

    private static List<Employee> parseXML(String fileName) throws ParserConfigurationException, IOException, SAXException {
        List<Employee> list = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(fileName));
        Node root = document.getDocumentElement();
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Employee employee = new Employee();
                employee.setId(Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent()));
                employee.setFirstName(element.getElementsByTagName("firstName").item(0).getTextContent());
                employee.setLastName(element.getElementsByTagName("lastName").item(0).getTextContent());
                employee.setCountry(element.getElementsByTagName("country").item(0).getTextContent());
                employee.setAge(Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent()));
                list.add(employee);
            }
        }
        return list;
    }
}