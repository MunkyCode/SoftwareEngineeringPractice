package edu.ithaca.dragon.bank;

import java.math.BigDecimal;

public class CheckingAccount extends Account {

    public CheckingAccount(String ID, double balance){
        super(ID, balance);
    }
    public void deposit(double amount){
        if (isAmountValid(amount)){
            balance += amount;
        }
        else {
            throw new IllegalArgumentException("invalid amount ");

        }
    }
    public void withdraw(double amount) throws InsufficientFundsException, IllegalArgumentException{
        if(!CheckingAccount.isAmountValid(amount))throw new IllegalArgumentException("Not a valid Amount");
        if(Double.compare(amount, 0.0)==0)throw new IllegalArgumentException("Cannot Withdraw zero dollars");
        if(Double.compare(this.balance-amount, 0.0)==-1)throw new InsufficientFundsException("Not enough Funds");
        else this.balance -= amount;


    }

    public void transfer(Account transferTo, double amount) throws InsufficientFundsException{}

    public static boolean isAmountValid(double amountIn){
        if (amountIn < 0) return false;
        double scale = Math.pow(10, 9);
        amountIn = Math.round(amountIn*scale)/scale;
        if(Double.compare(amountIn, Math.round(amountIn*100)/100.0)!= 0) return false;
        else return true;
    }
}
