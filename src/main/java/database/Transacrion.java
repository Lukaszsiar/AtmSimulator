package database;

import java.sql.Date;

public class Transacrion {
    private double value;
    private TransactionTypeEnum type;
    private Date date;

    public Transacrion(double value, TransactionTypeEnum type, Date date) {
        this.value = value;
        this.type = type;
        this.date = date;
    }

    public String getTransactionInfo(){
        String result = "Value: "+ value +" Type: "+ type +" Date: "+ date;
        return result;
    }
}
