package crud;

import model.Developer;
import org.apache.log4j.Logger;
import util.JdbcConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeveloperOperations {
    private String pathToTheQuery = "src/main/resources/queries/";
    private static final Logger LOGGER = Logger.getLogger(DeveloperOperations.class.getName());
    private static final String SELECT_ID = "SELECT * FROM developers WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM developers";
    private static final String INSERT = "INSERT INTO developers(name, age, gender, salary) VALUES(?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE developers SET name = ?, age = ?, gender = ?, salary = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM developers WHERE id = ?";

    public Developer selectById(int id) throws SQLException {
        ResultSet resultSet = null;
        try (Connection connection = JdbcConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID)) {
            assert connection != null;
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Developer developer = createDeveloper(resultSet);
            return developer;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return null;
        } finally {
            resultSet.close();
        }
    }

    public List<Developer> selectAll() throws SQLException {
        ResultSet resultSet = null;
        try (Connection connection = JdbcConnector.getConnection();
             Statement statement = connection.createStatement()) {
            assert connection != null;
            resultSet = statement.executeQuery(SELECT_ALL);
            List<Developer> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(createDeveloper(resultSet));
            }
            return result;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            resultSet.close();
        }
        return Collections.emptyList();
    }

    public void deleteById(int id) throws SQLException {
        PreparedStatement preparedStatement = null;
        try (Connection connection = JdbcConnector.getConnection()) {
            assert connection != null;
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            preparedStatement.close();
        }
    }

    public void insert(Developer object) throws SQLException {
        PreparedStatement preparedStatement = null;
        try (Connection connection = JdbcConnector.getConnection()) {
            assert connection != null;
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1, object.getName());
            preparedStatement.setInt(2, object.getAge());
            preparedStatement.setDouble(3, object.getSalary());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            preparedStatement.close();
        }
    }

    public void update(Developer object) throws SQLException {
        PreparedStatement preparedStatement = null;
        try (Connection connection = JdbcConnector.getConnection()) {
            assert connection != null;
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, object.getName());
            preparedStatement.setInt(2, object.getAge());
            preparedStatement.setDouble(3, object.getSalary());
            preparedStatement.setInt(4, object.getId());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            preparedStatement.close();
        }
    }

    private Developer createDeveloper(ResultSet resultSet) throws SQLException {
        return new Developer(resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getInt("age"),
                resultSet.getInt("salary"));
    }
}
