package S3.server;

import S3.cases.Archive;
import S3.cases.Case;
import S3.cases.Student;
import S3.parserXml.XmlReader;
import S3.user.User;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    private static List<User> users;
    private User user = null;
    private final Archive archive = new Archive();
    private static boolean isUnlocked = false;
    private String userType = "";

    public static void main(String[] args) {
        Server server = new Server();
        try(ServerSocket serverSocket = new ServerSocket(8083);
            Socket socket = serverSocket.accept();
            DataInputStream reader = new DataInputStream(socket.getInputStream());
            DataOutputStream writer = new DataOutputStream(socket.getOutputStream())){

            String request;

            while(socket.isConnected()){

                if (!isUnlocked){
                    request = reader.readUTF();
                    isUnlocked = server.authorization(request);
                    writer.writeBoolean(isUnlocked);
                    writer.writeUTF(server.userType);
                }

                else {
                    String response = server.mainMenu(reader.readUTF());
                    writer.writeUTF(response);
                }
            }
        }
        catch (IOException ex){
            throw new RuntimeException(ex);
        }
    }

    static {
        getUserFromXml();
    }

    private String mainMenu(String string){
        String response = null;
        int choose;

        String[] array = null;

        if (string.length() > 1){
            array = string.split(" ");
            choose = Integer.parseInt(array[0]);
            string = array[1];
        }
        else choose = Integer.parseInt(string);

        switch (choose){
            case 1 -> response = getCase();
            case 2 -> response = archive.searchByDateOfReceipt(Integer.parseInt(string));
            case 3 -> response = archive.searchByCaseNumber(Integer.parseInt(string));
            case 4 -> {
                switch (string){
                    case "1"-> response = archive.sortByRating();
                    case "2"-> response = archive.sortByCaseNumber();
                    case "3"-> response = archive.sortByDateOfReceipt();
                }
            }
            case 5-> {
                  assert array != null;
                  response = archive.insertCase(string);
            }
            case 6-> response = archive.updateCase(string);
            case 7-> response = archive.deleteCase(Integer.parseInt(string));
            case 8-> response = archive.countCasesAndStudents();
            case 9-> response = getStudent();
            case 10-> response = archive.deleteStudent(string);
            case 11->{
                assert array != null;
                response = archive.insertStudent(string);
            }
            case 12-> response = archive.updateStudent(string);
        }
        return response;
    }

    public String getCase(){
        StringBuilder response = new StringBuilder();
        for(Case c: archive.getCase()){
            response.append(c.toString()).append("\n");
        }
        return response.toString().trim();
    }

    public String getStudent(){
        StringBuilder response = new StringBuilder();
        for(Student s: archive.getStudent()){
            response.append(s.toString()).append("\n");
        }
        return response.toString().trim();
    }

    public boolean authorization(String string){
        boolean unlocked = false;

        if (string.length() <= 2) {
            return true;
        }

        String[] array = string.split(", ");
        String login = array[0].trim();
        String password = array[1].trim();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getLogin().equalsIgnoreCase(login)){

                if (users.get(i).getPassword().equalsIgnoreCase(password)) {
                    if (users.get(i).getUsersTypes().getName().equalsIgnoreCase("Admin")){
                        userType = users.get(i).getUsersTypes().getName();
                    }
                    else if (users.get(i).getUsersTypes().getName().equalsIgnoreCase("User")){
                        userType = users.get(i).getUsersTypes().getName();

                    }

                    unlocked = true;
                    user = users.get(i);
                }
                else {
                    System.out.println("Неверный пароль");
                }
            }
        }

        System.out.println("!!!!" + getUser());
        System.out.println("    " + userType);

        if (!unlocked){
            System.out.println("Такого пользователя нету в системе.");
        }
        return unlocked;
    }

    public User getUser(){
        return user;
    }

    public static void getUserFromXml(){
        try {
            users = XmlReader.readXml();
        }
        catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }
}
