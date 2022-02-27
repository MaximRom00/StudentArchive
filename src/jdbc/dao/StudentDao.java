package S3.jdbc.dao;

import S3.cases.Student;
import S3.jdbc.utill.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {

    private static final StudentDao INSTANCE = new StudentDao();

    private static final String DELETE_SQL = "delete from student where lastname = ?";
    private static final String SAVE_SQL = "insert into student(id, firstName, lastName, course, groupNumber)" +
            "values (?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "update student set id = ?, firstname = ?, lastname = ?, course = ?,groupnumber = ? where id = ?";
    private static final String FIND_BY_ID = "select * from student where id = ?";
    private static final String FIND_ALL = "select * from student";

    private StudentDao(){

    }

    public boolean delete(String lastName){
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(DELETE_SQL)){
            statement.setString(1, lastName);
            return statement.executeUpdate() > 0;
        }
        catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    public Student save(Student student){
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(SAVE_SQL)){
            statement.setInt(1, student.getId());
            statement.setString(2, student.getFirstName());
            statement.setString(3, student.getLastName());
            statement.setInt(4, student.getCourse());
            statement.setInt(5, student.getGroupNumber());

            statement.executeUpdate();
            return student;
        }
        catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    public Student findById(int id){
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)){
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            Student student = null;
            if (result.next()){
                student = buildStudent(result);
            }
            return student;
        }
        catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    public List<Student> findAll(){
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            List<Student> students = new ArrayList<>();
            while (resultSet.next()){
                students.add(buildStudent(resultSet));
            }
            return students;
        }
        catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    private Student buildStudent(ResultSet result) throws SQLException {
        return new Student(
                result.getInt("id"),
                result.getString("firstName"),
                result.getString("lastName"),
                result.getInt("course"),
                result.getInt("groupNumber"));
    }

    public void update(Student student){
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)){
            statement.setInt(1, student.getId());
            statement.setString(2, student.getFirstName());
            statement.setString(3, student.getLastName());
            statement.setInt(4, student.getCourse());
            statement.setInt(5, student.getGroupNumber());
            statement.setInt(6, student.getId());
            statement.executeUpdate();
        }
        catch (SQLException ex){
           ex.printStackTrace();
        }
    }

    public static StudentDao getInstance(){
        return INSTANCE;
    }
}
