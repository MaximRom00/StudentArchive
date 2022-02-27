package S3.cases;

import S3.jdbc.dao.CaseDao;
import S3.jdbc.dao.StudentDao;

import java.util.*;

/*
Класс содержит архив дел студентов.
Возможности класса, в зависимости от прав:
- добавление дела;
- удаление дела студента;
- сортировка дел студентов по рейтингу;
- сортировка дел студентов по номеру дела;
- сортировка по году поступления;
- поиск по году поступления;
- поиск по номеру дела;
- получение кол-ва дел в архиве и студентов;

- удаление студента;
- добавление студена;
 */
public class Archive {
    private final Scanner scanner = new Scanner(System.in);
    private final CaseDao caseDao = CaseDao.getInstance();
    private final StudentDao studentDao = StudentDao.getInstance();
    private boolean check = false;
    private final List<Integer> list = new ArrayList<>();
    private final List<String> stringList = new ArrayList<>();
    private Case updateCase = null;
    private Student updateStudent = null;

    public Archive(){
    }

    public String insertCase(String element){
        String returnLine = "";

        if (check){
            returnLine = CreateCase.createCase(element, list);
            if (returnLine.equalsIgnoreCase("ok")) {
                check = false;
                return "ok";
            }
           return returnLine;
        }

            if (studentDao.findById(Integer.parseInt(element)) == null){
                return "Данного студента нету в базе, для создания дела необходимо добавить студента в базу, после создать его дело.";
            }
            else if (caseDao.findById(Integer.parseInt(element)) == null){
                check = true;
                returnLine = CreateCase.createCase(element, list);
            }
            else{
                return "У данного студента уже есть личное дело.";
            }

        return returnLine;
    }

    public String deleteCase(int caseId){
        if (caseDao.delete(caseId)){
            return "Дело удалено.";
        }
        else return "Дело с таким Id не найдено.";
    }

    public String updateCase(String element){
        String returnLine = "";
        if (!check){
            updateCase = caseDao.findById(Integer.parseInt(element));
            check = true;

            if (updateCase != null){
                return "1 - изменить дату поступления;\n2 - изменить номер дела;" +
                        "\n3 - изменить рейтинг студента.";
            }
            else return "Дела с таким id не найдено.";

        }

        list.add(Integer.valueOf(element));
        returnLine = updateCase.update(updateCase.getStudent().getId(), list);

        if (list.size() == 2)
            list.remove(1);

        if (returnLine.equalsIgnoreCase("ok")){
            list.clear();
            check = false;
        }

        return returnLine;
    }


    public String insertStudent(String element){
        String returnLine = "";

        if (check){
            returnLine = CreateStudent.createStudent(element);
            if (returnLine.equalsIgnoreCase("ok")){
                check = false;
                return "ok";
            }
            return returnLine;
        }

         if (studentDao.findById(Integer.parseInt(element)) == null){
            check = true;
            returnLine = CreateStudent.createStudent(element);
        }
        else{
            return "Данный студент уже есть в базе.";
        }

        return returnLine;
    }

    public String deleteStudent(String lastName){
        if (studentDao.delete(lastName)){
            return "Студент удален.";
        }
        else return "Студент с такой фамилией не найден.";
    }

    public String updateStudent(String element){
            String returnLine = "";
            if (!check){
                updateStudent = studentDao.findById(Integer.parseInt(element));

                if (updateStudent == null){
                    return "Студент с таким id не найден.";
                }
                check = true;

                return "1 - изменить имя;\n2 - изменить фамилию;\n3 - изменить номер курса;" +
                        "\n4 - изменить номер группы.";
            }

            stringList.add(element);
            returnLine = updateStudent.update(updateStudent.getId(), stringList);

            if (list.size() == 2)
                list.remove(1);

            if (returnLine.equalsIgnoreCase("ok")){
                stringList.clear();
                check = false;
            }

            return returnLine;
    }

    public String sortByRating(){
        return getResultComparing(Comparator.comparing(Case::getStudentRating));
    }

    public String sortByCaseNumber(){
        return getResultComparing(Comparator.comparing(Case::getCaseNumber));
    }

    public String sortByDateOfReceipt(){
        return getResultComparing(Comparator.comparing(Case::getYear));
    }

    private String getResultComparing(Comparator<Case> comparing) {
        List<Case> archive = getCase();
        archive.sort(comparing);
        String result = "";
        for (Case string: archive){
            result += string.toString() + "\n";
        }
        return result.trim();
    }

    public String searchByCaseNumber(int caseNumber){
        String result = "";
        List<Case> archive = getCase();
        for (Case searchCaseNumber: archive){
            if (searchCaseNumber.getCaseNumber() == caseNumber){
                result = searchCaseNumber.toString();
            }
        }
        if (result.isEmpty()) result = "Ничего не найдено.";
        return result;
    }

    public String searchByDateOfReceipt(int dateOfReceipt){
        String result = "";
        List<Case> archive = getCase();
        for (Case searchYear: archive){
            if (searchYear.getYear() == dateOfReceipt){
                result += searchYear + "\n";
            }
        }
        if (result.isEmpty()) result = "Ничего не найдено.";
        return result.trim();
    }

    public String countCasesAndStudents(){
        List<Case> archive = getCase();
        List<Student> students = studentDao.findAll();
        String returnLine = "";

        returnLine = "Количество дел студентов: " + archive.size();
        returnLine += "\nКоличество студентов в базе: " + students.size();

        for (Case aCase : archive) {
            int id = aCase.getStudent().getId();
            for (int j = 0; j < students.size(); j++) {
                if (id == students.get(j).getId()) {
                    students.remove(j);
                }
            }
        }
        returnLine += "\nКоличество студентов без личных дел: " + students.size() + "\n" + students;

        return returnLine;
    }

    public List<Case> getCase(){
        return caseDao.findAll();
    }

    public List<Student> getStudent(){
        return studentDao.findAll();
    }
}
