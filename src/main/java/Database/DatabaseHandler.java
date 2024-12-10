package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends Configs {
    private Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }
    public void addFormula(String formula) {
        String insertQuery = "INSERT INTO autoplagiat (formula) VALUES (?)";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insertQuery);
            preparedStatement.setString(1, formula);
            preparedStatement.executeUpdate();
            System.out.println("формула добавлена");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("ошибка при добавлении формулы");
        } finally {
            // Закрытие подключения
            try {
                if (dbConnection != null && !dbConnection.isClosed()) {
                    dbConnection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}