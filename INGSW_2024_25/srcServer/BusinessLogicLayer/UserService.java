package BusinessLogicLayer;

import DataAccessLayer.DatabaseManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    private DatabaseManager dbManager;

    public UserService() {
        dbManager = new DatabaseManager();
    }

    public boolean authenticateUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
        ResultSet rs = dbManager.executeQuery(query);

        try {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void close() {
        dbManager.closeConnection();
    }
}
