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
        // login User(7)

    public Account loginUser(Account account) throws SQLException {
        Account account1=accountDAO.getAccountByPassword(account);
        if(account != null && account1 != null && account.username != null && account1.username != null && account.password != null && account1.password != null && account.username.equals(account1.username) && account.password.equals(account1.password)){
            return accountDAO.getAccountByPassword(account);
        } else{
            return null;
        }
    }


    // USER REGISTRARTION TEST (8)
    
    public Account getAccountByUsername(String str){
        Account account=accountDAO.getAccountByUsername(str);
        if(account!=null){
            return account;
        }
        return null;
    }
    public Account registerUser(Account account) {
        Account savedAccount = accountDAO.createAccount(account);
        return savedAccount ;
    }
}