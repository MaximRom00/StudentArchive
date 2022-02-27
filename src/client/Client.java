package S3.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try(Socket socket = new Socket("localhost", 8083);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream input = new DataInputStream(socket.getInputStream())){

            while (socket.isConnected()){
                getAuthorization(out, input);
                System.out.println(input.readUTF());
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Меню запросов на сервер
    public static void menu(DataOutputStream writer, DataInputStream reader){
        int choose = 1;
        String request;
        String response = "";
        try {
            String userTypes = reader.readUTF();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }


        while (choose != 0){
            System.out.println("Меню для обычного пользователя:" +
             "\n1 - просмотр всех дел в архиве;\n2 - поиск дел по году поступления;\n3 - поиск дел по номеру дела;\n4 - сортировка дел;" +
            "\nМеню для админа:\n5 - добавить дело в архив;\n6 - изменение дела;\n7 - удаление дела по id;" +
            "\n8 - получение кол-ва дел в архиве и студентов;\n9 - просмотр всех студенто;" +
            "\n10 - удаление студента из базы студентов;\n11 - добавление студента в базу студентов;" +
            "\n12 - изменение студента.");
            try {
                choose = scanner.nextInt();
                switch (choose){
                    case 1-> {
                        request = "1";
                        writeToServer(writer, request);
                        response = reader.readUTF();
                        System.out.println(response);
                    }
                    case 2-> {
                        System.out.println("Для поиска укажите год дела:");
                        request = "2" + " " + scanner.next();
                        writeToServer(writer,request);
                        System.out.println(reader.readUTF());
                    }
                    case 3-> {
                        System.out.println("Для поиска укажите номер дела:");
                        request = "3" + " " + scanner.next();
                        writeToServer(writer,request);
                        System.out.println(reader.readUTF());
                    }
                    case 4-> {
                        System.out.println("1- Сортировка дел студентов по рейтингу;\n" +
                                           "2- Сортировка дел студентов по номеру дела;\n" +
                                           "3- Сортировка по году поступления;");
                        request = "4" + " " + scanner.next();
                        writeToServer(writer,request);
                        System.out.println(reader.readUTF());
                    }
                    case 5->{
                        System.out.println("Для добавления нового дела в архив, следует проверить, " +
                                "есть ли у студента дела. Ведите id студента для поиска ");
                        request = "5" + " " + scanner.next();
                        writeToServer(writer,request);

                        while (true){
                            response = reader.readUTF();
                            System.out.println(response);
                            if(response.equalsIgnoreCase("ok")){
                                System.out.println("Дело добавлено.");
                                break;
                            }
                            request = "5" + " " + scanner.next();
                            writeToServer(writer,request);
                        }
                    }
                    case 6 ->{
                        System.out.println("Ведите id дела,которое хотите изменить.");
                        request = "6" + " " + scanner.next();
                        writeToServer(writer,request);

                        while (true){
                            response = reader.readUTF();
                            System.out.println(response);
                            if(response.equalsIgnoreCase("ok")){
                                System.out.println("Дело обновлено.");
                                break;
                            }
                            request = "6" + " " + scanner.next();
                            writeToServer(writer,request);
                        }
                    }

                    case 7 -> {
                        System.out.println("Введите id дела для удаления:");
                        request = "7" + " " + scanner.next();
                        writeToServer(writer,request);
                        System.out.println(reader.readUTF());
                    }
                    case 8 ->{
                        request = "8";
                        writeToServer(writer,request);
                        System.out.println(reader.readUTF());
                    }
                    case 9 ->{
                        request = "9";
                        writeToServer(writer, request);
                        response = reader.readUTF();
                        System.out.println(response);
                    }
                    case 10 ->{
                        System.out.println("Введите фамилию студента для удаления");
                        request = "10" + " " + scanner.next();
                        writeToServer(writer,request);
                        System.out.println(reader.readUTF());
                    }
                    case 11 ->{
                        System.out.println("Ведите id студента: ");
                        request = "11" + " " + scanner.next();
                        writeToServer(writer,request);

                        while (true){
                            response = reader.readUTF();
                            System.out.println(response);
                            if(response.equalsIgnoreCase("ok")){
                                System.out.println("Студент добавлен.");
                                break;
                            }
                            request = "11" + " " + scanner.next();
                            writeToServer(writer,request);
                        }
                    }
                    case 12 ->{
                        System.out.println("Ведите id студента,которого хотите изменить.");
                        request = "12" + " " + scanner.next();
                        writeToServer(writer,request);

                        while (true){
                            response = reader.readUTF();
                            System.out.println(response);
                            if(response.equalsIgnoreCase("ok")){
                                System.out.println("Студент обновлен.");
                                break;
                            }
                            request = "12" + " " + scanner.next();
                            writeToServer(writer,request);
                        }
                    }
                }
            }
            catch (InputMismatchException | IOException ex){
                System.out.println("Неверный ввод");
                scanner.next();
            }
        }
    }

    public static void writeToServer(DataOutputStream writer, String request){
        try {
            writer.writeUTF(request);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getAuthorization(DataOutputStream writer, DataInputStream reader) {
        boolean isAuthorization = false;
        String usersTypes = null;

        while(!isAuthorization){
            String request;

            System.out.print("Введите логин: ");
            request = scanner.nextLine().concat(", ");
            System.out.print("Введите пароль: ");
            request += scanner.nextLine();

            writeToServer(writer,request);

            try {
                isAuthorization = reader.readBoolean();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            if (isAuthorization){
                menu(writer, reader);
            }
            else System.out.println("Ошибка, проверьте вводимые данные.");
        }
    }
}
