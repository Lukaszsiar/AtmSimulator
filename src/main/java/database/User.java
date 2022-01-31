package database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    private int id;
    private String firstName;
    private String lastName;
    private Double accountBalance;

    public User(int id, String firstName, String lastName, Double accountBalance) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountBalance = accountBalance;
    }

    public int getId() {
        return id;
    }



}
