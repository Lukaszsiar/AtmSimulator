package database;

import java.sql.*;
import java.time.LocalDateTime;

public class DataBase {

    static Connection getConnection(){
        String url = "jdbc:mysql://localhost:3306/AtmDb";
        String user = "root";
        String password = "admin";
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            return connection;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static User logIn(String login, int pin){
        Connection connection = DataBase.getConnection();
        User user = null;
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT UserId, UserFirstName, UserLastName, UserAccountBalance" +
                            " FROM User WHERE UserLogin = ? AND UserPin = ?");
            st.setString(1, login);
            st.setInt(2, pin);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
               int userId = rs.getInt(1);
               String firstName = rs.getString(2);
               String lastName = rs.getString(3);
               Double accountBalance = rs.getDouble(4);
               user = new User(userId, firstName, lastName, accountBalance);
                return user;
            }
            st.close();
            rs.close();
            connection.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }


    static void updateAccountBalance(int userId, double newAccountBalance){
        Connection connection = DataBase.getConnection();
        try {
            PreparedStatement updateSt = connection.prepareStatement(
                    "UPDATE User SET UserAccountBalance = ? WHERE UserId = ?");

            updateSt.setDouble(1, newAccountBalance);
            updateSt.setInt(2, userId);
            updateSt.executeUpdate();

            updateSt.close();
            connection.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    static void createNewTransaction(int userId, TransactionTypeEnum type, double value){
        Connection connection = DataBase.getConnection();
        try {
            PreparedStatement insertSt = connection.prepareStatement(
                    "INSERT INTO Transaction(TransactionValue, TransactionType, TransactionDate, UserId)" +
                            "VALUES(?, ?, ?, ?);");

        insertSt.setDouble(1, value);
        insertSt.setString(2, String.valueOf(type));
        String date = String.valueOf(LocalDateTime.now());
        insertSt.setString(3, date);
        insertSt.setInt(4, userId);
        insertSt.executeUpdate();

        insertSt.close();
        connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    static boolean userExist(int userId){
        Connection connection = DataBase.getConnection();
        boolean userExist = false;

        String query = "SELECT 1 FROM User WHERE UserId = " + userId;
        try {
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            if(rs.next()) userExist = true;

            rs.close();
            st.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userExist;
    }
}
