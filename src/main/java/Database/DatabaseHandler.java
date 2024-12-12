package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends Configs {
    private Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(connectionString, dbUser, dbPass);
    }

    public boolean isFormulaExists(String formula) {
        String query = "SELECT COUNT(*) AS count FROM autoplagiat WHERE formula = ?";
        try {
            dbConnection = getDbConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setString(1, formula);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt("count") > 0) {
                return true; // Формула уже существует
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ошибка при проверке существования формулы: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return false; // Формулы нет в базе данных
    }

    public void addFormula(String formula) {
        String insertQuery = "INSERT INTO autoplagiat (formula) VALUES (?)";
        try {
            dbConnection = getDbConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement(insertQuery);
            preparedStatement.setString(1, formula);
            preparedStatement.executeUpdate();
            System.out.println("Формула добавлена: " + formula);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ошибка при добавлении формулы: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection();
        }
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
            e.printStackTrace();
        } finally {
            try {
                if (dbConnection != null && !dbConnection.isClosed()) {
                    dbConnection.close();
                }
            } catch (SQLException e) {
                System.err.println("Ошибка при закрытии соединения: " + e.getMessage());
            }
        }
        return formulas;
    }
}
