package Service;

import DAO.AccountDAO;
import Model.Account;

import java.util.List;

public class AccountService {
    private AccountDAO accountDAO = new AccountDAO();

    public Account createAccount(Account account) {
        return accountDAO.createAccount(account);
    }

    public Account getAccountById(int account_id) {
        return accountDAO.getAccountById(account_id);
    }

    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

    public boolean updateAccount(Account account) {
        return accountDAO.updateAccount(account);
    }

    public boolean deleteAccount(int account_id) {
        return accountDAO.deleteAccount(account_id);
    }
}

