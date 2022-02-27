package S3.cases;

import S3.jdbc.dao.CaseDao;

import java.util.List;

/*
Класс содержит дела студентов:
- общая информация о студенте(номер дела, дата поступления, рейтинг студента.);
- id дела (база cases) соответствует id студента из базы student.

Обычный пользователь может только просматривать дела студентов.
Администратор может вносить изменения в дела студентов:

Возможности класса:
- изменение курса студента;
- изменение группы студента;
- изменение рейтинга студента;
- изменение всех параметров дела.

 */
public class Case {
    private Student student;
    private final int caseNumber;
    private final double studentRating;
    private final CaseDao caseDao = CaseDao.getInstance();
    private int studentId;
    private final int year;

    public Case(Student student, int year, int caseNumber, double studentRating) {
        this.student = student;
        this.year = year;
        this.caseNumber = caseNumber;
        this.studentRating = studentRating;
    }

    public Case(int studentId, int year, int caseNumber, double studentRating) {
        this.studentId = studentId;
        this.year = year;
        this.caseNumber = caseNumber;
        this.studentRating = studentRating;
    }

    public String update(int id, List<Integer> list){
        String returnLine = "";

        switch (list.get(0)){
            case 1 -> {
                if (list.size() == 2){
                    if (list.get(1) >= 2011 && list.get(1) <= 2020){
                        caseDao.update(new Case(
                                id, list.get(1),
                                this.getCaseNumber(), this.getStudentRating()));

                        return "ok";
                    }
                    return "Год должен быть в диапазоне от 2011 до 2020г.";
                }
                else return "Введите год.";
            }

            case 2 -> {
                if (list.size() == 2){
                    if (list.get(1) >= 100 && list.get(1) <= 199){
                        caseDao.update(new Case(
                                id, getYear(),
                                list.get(1), this.getStudentRating()));

                        return "ok";
                    }
                    return "Неверный ввод. Номер должен быть в диапазоне от 100 до 199.";
                }
                else return "Введите номер дела.";
            }

            case 3 -> {
                if (list.size() == 2){
                    if (list.get(1) >= 1 && list.get(1) <= 10){
                        caseDao.update(new Case(
                                id, getYear(),
                                this.getCaseNumber(), list.get(1)));

                        return "ok";
                    }
                    return "Неверный ввод. Рейтинг должен быть в диапазоне от 1 до 10.";
                }
                else return "Введите рейтинг.";
            }
        }
        return returnLine;
    }

    public Student getStudent() {
        return student;
    }

    public int getCaseNumber() {
        return caseNumber;
    }

    public double getStudentRating() {
        return studentRating;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return  student + ", дата поступления: " + year + ", номер дела - " + caseNumber +
                ", рейтинг - " + studentRating + ", характеристика студента: ";
    }
}
