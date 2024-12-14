package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends Configs {
    private Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:sqlite:" + dbPath;
        Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection(connectionString);
    }

    public void initializeDatabase() {
        String createTableQuery = """
            CREATE TABLE IF NOT EXISTS autoplagiat (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                formula TEXT NOT NULL UNIQUE
            );
        """;

        try {
            dbConnection = getDbConnection();
            Statement statement = dbConnection.createStatement();
            statement.execute(createTableQuery);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ошибка при создании таблицы: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    public boolean isFormulaExists(String formula) {
        String query = "SELECT COUNT(*) AS count FROM autoplagiat WHERE formula = ?";
        try {
            dbConnection = getDbConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setString(1, formula);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt("count") > 0) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ошибка при проверке существования формулы: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return false;
    }

    public void addFormula(String formula) {
        String insertQuery = "INSERT INTO autoplagiat (formula) VALUES (?)";
        try {
            dbConnection = getDbConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement(insertQuery);
            preparedStatement.setString(1, formula);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ошибка при добавлении формулы: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    public List<String> getAllFormulas() {
        List<String> formulas = new ArrayList<>();
        String selectQuery = "SELECT formula FROM autoplagiat";

        try {
            dbConnection = getDbConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                formulas.add(resultSet.getString("formula"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ошибка при загрузке формул: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return formulas;
    }


    private void closeConnection() {
        try {
            if (dbConnection != null && !dbConnection.isClosed()) {
                dbConnection.close();
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при закрытии соединения: " + e.getMessage());
        }
    }
}
