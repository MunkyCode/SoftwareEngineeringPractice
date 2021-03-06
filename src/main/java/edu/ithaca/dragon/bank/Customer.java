package edu.ithaca.dragon.bank;

import java.util.ArrayList;

public class Customer {
    private String id;
    private String password;
    private Account checking;

    public Customer( String idIn,  String passwordIn){
        id = idIn;
        password = passwordIn;
        checking = null;
    }

    public void deposit(double amount){
        checking.deposit(amount);
    }

    public void withdraw(double amount)throws IllegalArgumentException, InsufficientFundsException{
        if(checking == null)throw new IllegalArgumentException("Account Not Created");
        checking.withdraw(amount);
    }

    public double getBalance(){
        if(checking == null)throw new IllegalArgumentException("No account");
        return checking.getBalance();
    }

    public boolean checkCredentials(String idIn, String passwordIn){
        return false;
    }

    public void createAccount(double startingBalance) throws IllegalArgumentException{
        if(checking != null)throw new IllegalArgumentException("Account already Exists");
        if(!CheckingAccount.isAmountValid(startingBalance)) throw new IllegalArgumentException("Invalid Starting balance");
        checking = new CheckingAccount(id, startingBalance);
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }


    public static boolean isEmailValid(String email){
        ArrayList<Character> validChar = validCharsList();


        int atId = email.indexOf('@'); //gets the index of the first @
        if(atId== -1) return false; //if no @ invalid email
        if(email.lastIndexOf('@')!=atId) return false; // if last @ is not the first @ invalid email
        if(atId == 0||atId == email.length()-1) return false; // if the @ is the first or the last char the email is invalid

        int lastPeriod = email.lastIndexOf('.');
        if(lastPeriod==-1)return false;//if there is no period there is no extension
        if(lastPeriod<atId) return false; // if the @ is after the last . the email is invalid
        if(lastPeriod!= email.length()-3&&lastPeriod!= email.length()-4)return false;//the extension can only be 2 or 3 chars.

        if(isSpecialChar(email.charAt(0))||isSpecialChar(email.charAt(email.length()-1))) return false; // if the first or last char are special the email is invalid

        // loops through the email from front to back to check for multiple special chars in a row and invalid chars
        for(int x = 0; x < email.length(); x++){
            //if current char not in list it is invalid
            if(!validChar.contains(email.charAt(x)))return false;
            //if current char not the first or last char, and is special, if the one after it is special the email is invalid
            if( (x!=0&&x!=email.length()-1) && (isSpecialChar(email.charAt(x))) && (isSpecialChar(email.charAt(x+1))) ) return false;
        }
        return true;
    }

    private static boolean isSpecialChar(char e){
        return e=='-'||e=='_'||e=='@'||e=='.';
    }

    private static ArrayList<Character> validCharsList (){
        ArrayList<Character> validChar = new ArrayList<Character>();
        for(int x = 0; x < 26; x++){
            validChar.add((char)(x+65));
            validChar.add((char)(x+97));
        }
        for(int x = 0; x < 10; x++){
            validChar.add((char)(x+'0'));
        }
        validChar.add('-');
        validChar.add('_');
        validChar.add('@');
        validChar.add('.');
        return validChar;
    }

    public void toggleFreeze(){
        checking.toggleFrozen();
    }

    public boolean getFrozen(){
        return checking.getFrozen();
    }
}
