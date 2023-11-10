package Service;
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

    public Account registerUser(Account account) {
        // Additional validation logic can be added here
        return accountDAO.createAccount(account);
    }

    public Account loginUser(Account account) {
        // Additional validation logic can be added here
        return accountDAO.getAccountByUsernameAndPassword(account);

    // Additional account-related methods based on business logic


}
public Account getAccountByUsername(String str){
    return accountDAO.getAccountByUsername(str);
}
}
