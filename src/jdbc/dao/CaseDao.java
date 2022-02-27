package S3.jdbc.dao;

import S3.cases.Case;
import S3.cases.Student;
import S3.jdbc.utill.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CaseDao {
    private static final CaseDao INSTANCE = new CaseDao();
    private static final String DELETE_SQL = "delete from cases where student_id = ?";
    private static final String SAVE_SQL = "insert into cases(student_id, dateOfReceipt, caseNumber, rating)" +
            "values (?, ?, ?, ?)";
    private static final String UPDATE_SQL = "update cases set student_id = ?, dateOfReceipt = ?, caseNumber = ?,rating = ? where student_id = ?";
    private static final String FIND_BY_ID = "select * from cases join student on student_id = id where student_id = ?";
    private static final String FIND_ALL = "select * from cases join student on student_id = id";

    private CaseDao(){}


    public boolean delete(int student_id){
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(DELETE_SQL)){
            statement.setInt(1, student_id);

            return statement.executeUpdate() > 0;
        }
        catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    public Case findById(int id){
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)){
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            Case searchCase = null;
            if (result.next()){
                searchCase = buildCase(result);
            }
            return searchCase;
        }
        catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    public List<Case> findAll(){
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            List<Case> cases = new ArrayList<>();
            while (resultSet.next()){
                cases.add(buildCase(resultSet));
            }
            return cases;
        }
        catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    public Case save(Case insertCase){
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(SAVE_SQL)){
            statement.setInt(1, insertCase.getStudentId());
            statement.setInt(2, insertCase.getYear());
            statement.setInt(3, insertCase.getCaseNumber());
            statement.setDouble(4, insertCase.getStudentRating());

            statement.executeUpdate();
            return insertCase;
        }
        catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    public void update(Case updateCase){
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)){
            statement.setInt(1, updateCase.getStudentId());
            statement.setInt(2, updateCase.getYear());
            statement.setInt(3, updateCase.getCaseNumber());
            statement.setDouble(4, updateCase.getStudentRating());
            statement.setInt(5, updateCase.getStudentId());
            statement.executeUpdate();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    private Case buildCase(ResultSet result) throws SQLException {
        Student student = new Student(
                result.getInt("id"),
                result.getString("firstName"),
                result.getString("lastName"),
                result.getInt("course"),
                result.getInt("groupNumber"));
        return new Case(
                student,
                result.getInt("dateOfReceipt"),
                result.getInt("caseNumber"),
                result.getDouble("rating"));
    }


    public static CaseDao getInstance() {
        return INSTANCE;
    }
}
