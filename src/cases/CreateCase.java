package S3.cases;

import S3.jdbc.dao.CaseDao;

import java.util.List;

public class CreateCase {
    private final static CaseDao caseDao = CaseDao.getInstance();
    public static String createCase(String element, List<Integer> list){

        if (list.size() != 3){
            list.add(Integer.parseInt(element));
        }

        int id = list.get(0);
        int year = 0;
        int caseNumber = 0;
        double rating = 0;

        if (list.size() >= 2){
            if(list.get(1) >= 2011 && list.get(1) <= 2020){
                year = list.get(1);
                System.out.println("year - " + year);
            }
            else{
                list.remove(list.get(1));
                return "Неверный ввод. Год должен быть в диапазоне от 2011 до 2020г.";
            }
        }
        else return "Введите год. Год должен быть в диапазоне от 2011 до 2020г.";

        if (list.size() >= 3) {
            if (list.get(2) > 99 && list.get(2) < 200){
                caseNumber = list.get(2);
                System.out.println("caseNumber - " + caseNumber);
            }
            else {
                list.remove(list.get(2));
                return "Неверный ввод. Номер должен быть в диапазоне от 100 до 199.";
            }
        }
        else return  "Введите номер дела,который должен быть в диапазоне от 100 до 199.";


        if (element.contains(".")) {
            if (Double.parseDouble(element) > 1 && Double.parseDouble(element) < 10){
                rating = Double.parseDouble(element);
                System.out.println("rating - " + rating);
            }
            else{
                return "Неверный ввод. Рейтинг должен быть в диапазоне от 1 до 10.";
            }
        }
        else return  "Введите рейтинг студента. Рейтинг должен быть в диапазоне от 1 до 10.";

        if (year != 0 && caseNumber != 0 && rating != 0){
            caseDao.save(new Case(id, year, caseNumber, rating));
            list.clear();
        }
        return "Ok";
    }
}
