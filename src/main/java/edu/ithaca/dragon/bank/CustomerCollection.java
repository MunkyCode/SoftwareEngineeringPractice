package edu.ithaca.dragon.bank;

import java.util.ArrayList;

public class CustomerCollection {
    private ArrayList<Customer> customers;

    public CustomerCollection(){
        customers = new ArrayList<Customer>();
    }

    public void addCustomer(String idIn, String passwordIn){}

    public void deposit(String ID, double amount){}

    public void withdraw(String ID, double amount) throws IllegalArgumentException {
        for(int i=0;i< customers.size();i++){
            if (customers.get(i).getId()==ID){
                customers.get(i).withdraw(amount);
                return;
            }
        }
        //throw new IllegalArgumentException("No such Account");
    }

    public double getBalance(String ID){

        return -1234.00;
    }

    public boolean checkCredentials(String ID, String password){
        return false;
    }

    public void createAccount(String actID, double startingBalance){}

    public void closeAccount(String actID){}

    public void closeCustomer(String actID){}

}
