package application;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());

            // Convert byte array into signum representation
            BigInteger number = new BigInteger(1, hashedBytes);

            // Convert message digest into hex value
            StringBuilder hexString = new StringBuilder(number.toString(16));

            // Pad with leading zeros
            while (hexString.length() < 32) {
                hexString.insert(0, '0');
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null; // Ideally handle the exception more gracefully
        }
    }
}