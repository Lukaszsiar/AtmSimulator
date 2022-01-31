import database.DataBase;
import database.MoneyOperations;
import database.Transacrion;
import database.User;

import java.util.*;

public class Interface {
    private String prompt = "ATM>";

    Scanner scanner = new Scanner(System.in);
    String userInput;


    void startAtm(boolean isProgramRunning){

        User user = logIn();
        int userId = user.getId();

        HashMap<CommandEnum, Runnable> commandMap = new HashMap<>(){{
            put(CommandEnum.HISTORY, () -> showHistory(userId));
            put(CommandEnum.WITHDRAW, () -> withdraw(userId));
            put(CommandEnum.DEPOSIT, () -> deposit(userId));
            put(CommandEnum.TRANSFER, () -> transfer(userId) );
            put(CommandEnum.STATUS, () -> showAccountBalance(userId));
            put(CommandEnum.HELP, () -> showHelp());
            put(CommandEnum.EXIT, () -> exit());
        }};
        while (isProgramRunning){
            System.out.print(prompt);
            userInput = scanner.nextLine();
            userInput = userInput.replaceAll(" ","_").toUpperCase();
            try {
                commandMap.get(CommandEnum.valueOf(userInput)).run();
            }catch (IllegalArgumentException e){
                System.out.println("Wrong command. Type 'help' to see all available commands.");
            }
        }
    }


    User logIn() {
        boolean flag = true;
        User user = null;
        while (flag) {
            System.out.println("Login: ");
            String loginInput = scanner.nextLine();
            System.out.println("Pin: ");
            int pinInput = Integer.valueOf(scanner.nextLine());

            user = DataBase.logIn(loginInput, pinInput);
            if (user == null) {
                System.out.println("Invalid login or pin");
            } else flag = false;
        }
        System.out.println("Logged in");
        return user;
    }


    void showHistory(int userId){
        MoneyOperations operation = new MoneyOperations();
        List<Transacrion> transactions = operation.getTransactionList(userId);
        for (Transacrion t: transactions) {
            System.out.println(t.getTransactionInfo());
        }
    }

    void showHelp(){
        List<Enum> commands = Arrays.asList(CommandEnum.values());
        System.out.println(commands);
    }

    void showAccountBalance(int userId){
        MoneyOperations operation = new MoneyOperations();
        System.out.println("Your account balance is: "+ operation.getUserAccountBalance(userId));
    }

    void deposit(int userId){
        MoneyOperations operation = new MoneyOperations();
        System.out.println("Specify amount: ");
        Double amountInput = Double.valueOf(scanner.nextLine());
        operation.depositMoney(userId, amountInput);
    }

    void withdraw(int userId){
        MoneyOperations operation = new MoneyOperations();
        System.out.println("Specify amount: ");
        Double amountInput = Double.valueOf(scanner.nextLine());
        operation.withdrawMoney(userId, amountInput);
    }

    void transfer(int userId){
        MoneyOperations operation = new MoneyOperations();
        System.out.println("Specify amount: ");
        Double amountInput = Double.valueOf(scanner.nextLine());
        System.out.println("UserId to send money: ");
        int targetId = Integer.valueOf(scanner.nextLine());
        operation.transferMoney(userId, targetId, amountInput);
    }


    void exit(){
        System.exit(0);
    }


}
