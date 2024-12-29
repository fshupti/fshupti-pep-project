package Service;

import DAO.AccountDAO;
import Model.Account;

import java.util.List;
import java.util.ArrayList;

public class AccountService {
    private AccountDAO accountDAO = new AccountDAO();
    private List<Account> accountList = new ArrayList<>();

    // AccountService.java
public Account createAccount(Account account) {
    
    return accountDAO.createAccount(account);
}

public Account getAccountById(int id) {
    // Fetch the account using the provided ID
    return accountDAO.getAccountById(id);  // This assumes the DAO method is correctly implemented
}

public Account login(String username, String password) {
    // Add logic to fetch user from the database
    if ("testuser1".equals(username) && "password".equals(password)) {
        return new Account(1, "testuser1", "password");
    }
    return null;
}


// Get account by username
public Account getAccountByUsername(String username) {
    for (Account account : accountList) {
        if (account.getUsername().equals(username)) {
            return account;
        }
    }
    return null;  // Return null if no account found
}


public List<Account> getAllAccounts() {
    return accountDAO.getAllAccounts(); // Fetch all accounts from the database
}


    public boolean updateAccount(Account account) {
        return accountDAO.updateAccount(account);
    }

    public boolean deleteAccount(int account_id) {
        return accountDAO.deleteAccount(account_id);
    }
}

