package application;

import java.io.Serializable;

/* Made by Troy Reiling, member of Team Tu12 in CSE360 Fall 2023
This is the Account class for the authentication prototype.
*/

class Account implements Serializable {
	private static final long serialVersionUID = 1L;
    private int id;
    private String username;
    private String password;
    private int privilege;

    public Account(int id, String username, String password, int privilege) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.privilege = privilege;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getPrivilege() {
        return privilege;
    }
}
