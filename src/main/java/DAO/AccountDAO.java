package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.Account;
import Util.ConnectionUtil;
public class AccountDAO {
    private Connection connection;
    public AccountDAO() {
        this.connection = ConnectionUtil.getConnection();
    }
    

    // LOGIN USER(7)
    
    public Account getAccountByPassword(Account account) throws SQLException {
        String query = "SELECT * FROM account WHERE username = ? and password = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, account.username);
        statement.setString(2, account.password);
        ResultSet resultSet = statement.executeQuery();
        
        if (resultSet.next()) {
            Account account1 = new Account(
                resultSet.getInt("account_id"),
                resultSet.getString("username"),
                resultSet.getString("password")
                );
                return account1;
        } else {
            return null;
        }
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

    
    // user registration (8)


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
    public Account getById(String accountId) {
       return null;
    }
}