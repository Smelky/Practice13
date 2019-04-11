package crud;

import model.Project;
import util.JdbcConnector;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProjectOperations {
    private static final Logger LOGGER = Logger.getLogger(ProjectOperations.class.getName());
    private static final String SELECT_ID = "SELECT * FROM projects WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM projects";
    private static final String INSERT = "INSERT INTO projects(name, cost, date) VALUES(?, ?, ?))";
    private static final String UPDATE = "UPDATE projects SET name = ?, cost = ? date = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM projects WHERE id = ?";

    public Project selectById(int id) throws SQLException {
        ResultSet resultSet = null;
        try (Connection connection = JdbcConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID)) {
            assert connection != null;
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Project project = createProject(resultSet);
            return project;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            resultSet.close();
        }
        return null;
    }

    public List<Project> selectAll() throws SQLException {
        ResultSet resultSet = null;
        try (Connection connection = JdbcConnector.getConnection();
             Statement statement = connection.createStatement()) {
            assert connection != null;
            resultSet = statement.executeQuery(SELECT_ALL);
            List<Project> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(createProject(resultSet));
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

    public void insert(Project object) throws SQLException {
        PreparedStatement preparedStatement = null;
        try (Connection connection = JdbcConnector.getConnection()) {
            assert connection != null;
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1, object.getName());
            preparedStatement.setDouble(2, object.getCost());
            preparedStatement.setDate(3, object.getDate());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            preparedStatement.close();
        }
    }

    public void update(Project object) throws SQLException {
        PreparedStatement preparedStatement = null;
        try (Connection connection = JdbcConnector.getConnection()) {
            assert connection != null;
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, object.getName());
            preparedStatement.setDouble(2, object.getCost());
            preparedStatement.setDate(3, object.getDate());
            preparedStatement.setInt(4, object.getId());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            preparedStatement.close();
        }
    }

    private Project createProject(ResultSet resultSet) throws SQLException {
        return new Project(resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getInt("cost"),
                resultSet.getDate("date"),
                resultSet.getInt("numofmembers"));
    }
}
