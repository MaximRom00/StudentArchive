package S3.parserXml;

import S3.user.Administrator;
import S3.user.User;

import S3.user.UsersTypes;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class SaxParserHandler extends DefaultHandler {
    private List<User> users = new ArrayList<>();

    private String password;
    private String login;
    private String usersTypes;

    private boolean isUser = false;
    private String currentTag;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentTag = qName;

        if (currentTag.equals("User")){
            isUser = true;
            usersTypes = attributes.getValue("usersTypes");
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
            if (qName.equals("User")){
                if (usersTypes.equalsIgnoreCase("Administrator")){
                    Administrator administrator = new Administrator(login, password);
                    administrator.setUsersTypes(UsersTypes.Administrator);
                    users.add(administrator);
                }
                else if (usersTypes.equalsIgnoreCase("User")){
                    User user = new User(login, password);
                    user.setUsersTypes(UsersTypes.User);
                    users.add(user);
                }
                isUser = false;
            }

        currentTag = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (currentTag == null){
            return;
        }

        if (isUser){
            if (currentTag.equals("password")){
                password = new String(ch, start, length);
            }
            else if (currentTag.equals("login")){
                login = new String(ch, start, length);
            }
        }
    }
    public List<User> getUsers() {
        return users;
    }
}
