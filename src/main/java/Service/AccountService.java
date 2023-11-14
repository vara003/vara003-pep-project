package Service;
import java.sql.SQLException;
import DAO.AccountDAO;
import Model.Account;
public class AccountService {
    private AccountDAO accountDAO;
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    // RETRIVE ALL MESSAGES TEXT


    public Account registerUser(Account account) {
        // Check if the username is valid and not already taken
        if (account.getUsername() == null || account.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        // Check if the password meets the minimum length requirement
        if (account.getPassword().length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters long");
        }

        // Check if the username is already taken
        if (accountDAO.getAccountByUsername(account.getUsername()) != null) {
            throw new IllegalArgumentException("Username is already taken");
        }

        // If all checks pass, create the account
        return accountDAO.createAccount(account);
    }



    //public Account registerUser(Account account) {
        // Additional validation logic can be added here
        //return accountDAO.createAccount(account);
    //}
     //public Account loginUser(Account account) {
        // Additional validation logic can be added here
        //return accountDAO.getAccountByPassword(account);

        // login User

    public Account loginUser(Account account) throws SQLException {
        if (accountDAO.verifyLogin(account.username, account.password)) {

            return accountDAO.getAccountByPassword(account.getUsername());
            if(account!=null)
        } else 
            return null;
        }

    // Additional account-related methods based on business logic


    //USER REGISTRATION TEST()

    public Account getAccountByUsername(String str){
        Account account=accountDAO.getAccountByUsername(str);
        if(account!=null){
            return account;
        }
        return null;
    }
}