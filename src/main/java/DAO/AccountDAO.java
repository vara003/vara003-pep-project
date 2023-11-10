package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Account;
import Util.ConnectionUtil;
public class AccountDAO {
    private Connection connection;
    public AccountDAO() {
        this.connection = ConnectionUtil.getConnection();
    }
    public Account getAccountByUsernameAndPassword(Account account){
        String query = "SELECT * FROM Account WHERE username = ? AND password = ?";
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1,account.getUsername());
            preparedStatement.setString(2, account.getPassword());
    
            ResultSet resultSet = preparedStatement.executeQuery();
            int b=resultSet.getInt("Account_id");
    
            if (resultSet.next()) {
                return new Account(b,resultSet.getString("Username"),resultSet.getString("Password"));

            }

        } catch (SQLException e) {
            e.printStackTrace(); 
        // Handle the exception according to your application's error handling strategy
        }
        return null;
    }
    public Account createAccount(Account account) {
        String query = "INSERT INTO Account (username, password) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();

            ResultSet rs=preparedStatement.getGeneratedKeys();
            while(rs.next()){
                
                return new Account(rs.getInt("Account_Id"),rs.getString("Username"),rs.getString("Password"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's error handling strategy
        }
        return null;
    }

    public Account getAccountByUsername(String username) {
        String query = "SELECT * FROM Account WHERE username = ?";
        Account account = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int accountId = resultSet.getInt("account_id");
                String password = resultSet.getString("password");

                account = new Account(accountId, username, password);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's error handling strategy
        }

        return account;
    }

    // Other account-related methods
}
