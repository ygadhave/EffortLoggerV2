package application;

import java.util.Optional;

public class AuthenticationManager {
	private Database database;
	
	// Constructor
	public AuthenticationManager(Database d) {
		database = d;
	}

    public boolean registerAccount(String username, String password, int privilege) {
        // Here you would typically hash the password
        String hashedPassword = hashPassword(password);

        Account newAccount = new Account(database.getNextAccountId(), username, hashedPassword, privilege);
        return database.saveAccount(newAccount);
    }

    public Optional<Account> login(String username, String password) {
        // Hash the provided password
        String hashedPassword = hashPassword(password);

        return database.getAccount(username)
                       .filter(account -> account.getPassword().equals(hashedPassword));
    }

    private String hashPassword(String password) {
        // Implement password hashing here
        // Placeholder for hashing logic
        return password; // This should be replaced with actual hashing
    }
}