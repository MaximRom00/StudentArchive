package S3.parserXml;

import S3.user.User;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class XmlReader {
    public static void main(String[] args) throws ParserConfigurationException, SAXException {
        readXml();
    }

    public static List<User> readXml() throws ParserConfigurationException, SAXException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SaxParserHandler saxParserHandler = new SaxParserHandler();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        File file = new File("C:\\Users\\ххх\\IdeaProjects\\StudentArchive\\src\\S3\\parserXml\\file.xml");

        try {
            saxParser.parse(file,saxParserHandler);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return saxParserHandler.getUsers();
    }
}
