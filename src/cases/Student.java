package S3.cases;

import S3.jdbc.dao.StudentDao;

import java.util.List;
import java.util.Scanner;
/*
Возможности класса:
- изменение курса студента;
- изменение группы студента;
- изменение рейтинга студента;
- изменение всех параметров дела.
 */
public class Student {
    private final int id;
    private final static int startId = 1;
    private final String lastName;
    private final String firstName;
    private final int course;
    private final int groupNumber;
    private final StudentDao studentDao = StudentDao.getInstance();

    public Student(int id, String firstName, String lastName, int course, int groupNumber) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.course = course;
        this.groupNumber = groupNumber;
    }

    public String update(int id, List<String> list){
        String returnLine = "";

        switch (list.get(0)){
            case "1" -> {
                if (list.size() == 2){
                    if (list.get(1).matches("[a-zA-Z]{2,10}")){
                        studentDao.update(new Student(
                                id, list.get(1), this.getLastName(),
                                this.getCourse(), this.getGroupNumber()));

                        return "ok";
                    }
                    return "Имя должно содердать от 3 до 10 букв.";
                }
                else return "Введите имя.";
            }

            case "2" -> {
                if (list.size() == 2){
                    if (list.get(1).matches("[a-zA-Z]{2,12}")){
                        studentDao.update(new Student(
                                id, this.getFirstName(), list.get(1),
                                this.getCourse(), this.getGroupNumber()));

                        return "ok";
                    }
                    return "Неверный ввод. Фамилия должна содердать от 3 до 12 букв.";
                }
                else return "Введите фамилию.";
            }

            case "3" -> {
                if (list.size() == 2){
                    if (Integer.parseInt(list.get(1)) >= 1 && Integer.parseInt(list.get(1)) <= 5){
                        studentDao.update(new Student(
                                id, this.getFirstName(),this.getLastName(),
                                Integer.parseInt(list.get(1)), this.getGroupNumber()));

                        return "ok";
                    }
                    return "Неверный ввод. Номер курса должен быть от 1 до 5.";
                }
                else return "Введите номер курса.";
            }

            case "4" -> {
                if (list.size() == 2){
                    if (Integer.parseInt(list.get(1)) >= 111111 && Integer.parseInt(list.get(1)) <= 999999){
                        studentDao.update(new Student(
                                id, this.getFirstName(),this.getLastName(),
                                this.getCourse(), Integer.parseInt(list.get(1))));

                        return "ok";
                    }
                    return "Неверный ввод.  Номер группы должен содержать 6 цифр. и быть в диапазоне от 111111 до 999999.";
                }
                else return "Введите номер группы.";
            }
        }
        return returnLine;
    }

    public int getId(){
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getCourse() {
        return course;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    @Override
    public String toString() {
        return  "Id: " + id + ", " + lastName + " " + firstName +
                ", курс - " + course + ", номер группы: " + groupNumber;
    }
}
