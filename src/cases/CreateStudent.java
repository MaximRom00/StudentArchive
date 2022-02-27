package S3.cases;

import S3.jdbc.dao.StudentDao;

public class CreateStudent {
    private static int id = 0;
    private static String firstName = "";
    private static String lastName = "";
    private static int course = 0;
    private static int groupNumber;
    private final static StudentDao studentDao = StudentDao.getInstance();

    public static String createStudent(String element){

        if (id == 0){
                id = Integer.parseInt(element);
                return "Введите имя. Имя должно содердать от 2 до 10 букв.";
        }

        if (firstName.isEmpty()){
            if(element.matches("[a-zA-Z]{2,10}")){
                firstName = element;
                return "Введите фамилию. Фамилия должна содердать от 3 до 12 букв.";
            }
            else{
                return "Введите имя. Имя должно содердать от 2 до 10 букв.";
            }
        }

        if (lastName.isEmpty()) {
            if (element.matches("[a-zA-Z]{2,12}")){
                lastName = element;
                return "Введите номер курса. Номер курса должен быть от 1 до 5.";
            }
            else {
                return "Введите фамилию. Фамилия должна содердать от 3 до 12 букв.";
            }
        }

        if (course == 0) {
            if (Integer.parseInt(element) >= 1 && Integer.parseInt(element) <= 5){
                course = Integer.parseInt(element);
                return "Введите номер группы. Номер группы должен содержать 6 цифр. и быть в диапазоне от 111111 до 999999.";
            }
            else{
                return "Введите номер курса. Номер курса должен быть от 1 до 5.";
            }
        }

        if (groupNumber == 0) {
            if (Integer.parseInt(element) >= 111111 && Integer.parseInt(element) <= 999999){
                groupNumber = Integer.parseInt(element);
            }
            else{
                return "Введите номер группы. Номер группы должен содержать 6 цифр. и быть в диапазоне от 111111 до 999999.";
            }
        }

        if (!firstName.isEmpty() && !lastName.isEmpty() && course != 0 && groupNumber != 0){
            studentDao.save(new Student(id, firstName, lastName, course, groupNumber));
        }
        return "Ok";
    }
}
