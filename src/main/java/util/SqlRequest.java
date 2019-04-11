package util;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SqlRequest {
    private static final Logger LOGGER = Logger.getLogger(SqlRequest.class);

    public static void request(String request) {
        Connection connection = JdbcConnector.getConnection();
        assert connection != null;
        try (PreparedStatement statement = connection.prepareStatement(request);
             ResultSet resultSet = statement.executeQuery(request)) {
            while (resultSet.next()) {
                LOGGER.info(resultSet.getString(1));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void requestForLastTask(String request) {
        StringBuilder stringBuilder = new StringBuilder();
        Connection connection = JdbcConnector.getConnection();
        assert connection != null;
        try (PreparedStatement statement = connection.prepareStatement(request);
             ResultSet resultSet = statement.executeQuery(request)) {
            while (resultSet.next()) {
                stringBuilder.append(resultSet.getString(1));
                stringBuilder.append(" ");
                stringBuilder.append(resultSet.getString(2));
                stringBuilder.append(" ");
                stringBuilder.append(resultSet.getString(3));
                LOGGER.info(stringBuilder.toString());
                stringBuilder.setLength(0);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
