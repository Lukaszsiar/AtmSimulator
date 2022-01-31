package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MoneyOperations {

    public List<Transacrion> getTransactionList(int userId) {
        Connection connection = DataBase.getConnection();
        List<Transacrion> transactions = new ArrayList<>();
        String query = "SELECT TransactionValue, TransactionType, TransactionDate FROM Transaction WHERE UserId = "+ userId;

        try{
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Double value = rs.getDouble(1);
                TransactionTypeEnum type = TransactionTypeEnum.valueOf(rs.getString(2));
                Date date = rs.getDate(3);
                transactions.add(new Transacrion(value, type, (java.sql.Date) date));
            }
            rs.close();
            st.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }


    public Double getUserAccountBalance(int userId){
        Connection connection = DataBase.getConnection();
        double accountBalance = 0;
        String query = "SELECT UserAccountBalance FROM User WHERE UserId = "+ userId;
        try{
            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();

            if (rs.next()) accountBalance = rs.getDouble(1);

            rs.close();
            st.close();
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return accountBalance;
    }


    public void depositMoney(int userId, double value){
        value = Math.abs(value);
        Double accountBalance = getUserAccountBalance(userId);
        Double newAccountBalance = accountBalance + value;

        DataBase.updateAccountBalance(userId, newAccountBalance);
        DataBase.createNewTransaction(userId, TransactionTypeEnum.MONEY_DEPOSIT, value);
    }

    public void withdrawMoney(int userId, double value){
        value = Math.abs(value);
        double accountBalance = getUserAccountBalance(userId);

        if(accountBalance < value){
            System.out.println("You don't have enought money at your bank account");
            return;
        }
        double newAccountBalance = accountBalance - value;

        DataBase.updateAccountBalance(userId, newAccountBalance);
        DataBase.createNewTransaction(userId, TransactionTypeEnum.MONEY_WITHDRAW, value);
    }

    public void transferMoney(int userId, int targetId, double value){
        if (!DataBase.userExist(targetId)) return;

        value = Math.abs(value);
        double userAccountBalance = getUserAccountBalance(userId);

        if(userAccountBalance < value){
            System.out.println("You don't have enought money at your bank account");
            return;
        }
        double targetAccountBalance = getUserAccountBalance(targetId);
        double newUserAccountBalance = userAccountBalance - value;
        double newTargetAccountBalance = targetAccountBalance + value;


        DataBase.updateAccountBalance(userId, newUserAccountBalance);
        DataBase.createNewTransaction(userId, TransactionTypeEnum.OUTGOING_TRANSFER, value);
        DataBase.updateAccountBalance(targetId, newTargetAccountBalance);
        DataBase.createNewTransaction(targetId, TransactionTypeEnum.INCOMING_TRANSFER, value);
    }
}
