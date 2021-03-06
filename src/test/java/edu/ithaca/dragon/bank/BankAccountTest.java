package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        //Equivalence Class starting positive balance
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertEquals(200, bankAccount.getBalance());
        bankAccount = new BankAccount("a@b.com", 0);
        assertEquals(0, bankAccount.getBalance());
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance());

        //Equivalence Class Has balance
        BankAccount bankAccount1 = new BankAccount("a@c.com", 300);
        bankAccount1.withdraw(99);//valid withdraw
        assertEquals(201, bankAccount1.getBalance());
        bankAccount1.withdraw(1);//valid withdraw edgecase
        assertEquals(200, bankAccount1.getBalance());
        bankAccount1.withdraw(0.1);
        assertEquals(199.9, bankAccount1.getBalance(),10);
        bankAccount1.withdraw(0.01);
        assertEquals(199.89, bankAccount1.getBalance(), 10);
        assertThrows(InsufficientFundsException.class, ()-> bankAccount1.withdraw(201));
        assertEquals(199.89, bankAccount1.getBalance(), 10);
        assertThrows(InsufficientFundsException.class, ()-> bankAccount1.withdraw(20100));
        assertEquals(199.89, bankAccount1.getBalance(), 10);
        bankAccount1.withdraw(199.89);//perfect withdraw edgecase
        assertEquals(0, bankAccount1.getBalance(), 10);

        //Equivalence Class No balance
        BankAccount ba2 = new BankAccount("a@c.cm", 0);
        assertThrows(InsufficientFundsException.class, ()-> ba2.withdraw(1));
        assertEquals(0, ba2.getBalance());

        BankAccount ba3 = new BankAccount("a@c.cm", 200);
        assertThrows(IllegalArgumentException.class, ()-> ba3.withdraw(0));
        assertThrows(IllegalArgumentException.class, ()-> ba3.withdraw(1.001));
        assertThrows(IllegalArgumentException.class, ()-> ba3.withdraw(-14));
        assertThrows(IllegalArgumentException.class, ()-> ba3.withdraw(-0.001));

    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));
        assertFalse( BankAccount.isEmailValid(""));

        //Partitions of ""=invalid partition, address first, domain second, extension third

        //Equivalence class being in first partitions, with boundary value to index[0]->indexOf(@)
        //Multiple periods Partition tests
        assertFalse(BankAccount.isEmailValid("a..b@male.com"));
        assertTrue(BankAccount.isEmailValid("a.f.a@tah.com"));
        assertTrue(BankAccount.isEmailValid("a.ffff.a@gasd.asd"));
        assertFalse(BankAccount.isEmailValid("a...f@asdf.asd"));
        assertFalse(BankAccount.isEmailValid("abc.def@mail..com")); //Only one is allowed in the second half regardless

        //Slash partitionTest
        assertFalse(BankAccount.isEmailValid("abc-@mail.com"));
        assertTrue(BankAccount.isEmailValid("abc-d@mail.com"));

        //Invalid period Location
        assertFalse(BankAccount.isEmailValid(".abc@mail.com"));
        assertTrue(BankAccount.isEmailValid("a.a@gasd.com"));
        assertTrue(BankAccount.isEmailValid("abc.def@mail.com"));

        //Invalid Character Partitions
        assertFalse(BankAccount.isEmailValid("abc#def@mail.com"));
        assertFalse(BankAccount.isEmailValid("abc.def@mail#archive.com"));
        assertTrue(BankAccount.isEmailValid("abc@mail.com"));
        assertTrue(BankAccount.isEmailValid("abc_def@mail.com"));

        //Equivalence class Short or missing extentions
        assertFalse(BankAccount.isEmailValid("abc.def@mail.c"));
        assertFalse(BankAccount.isEmailValid("bc.def@mail"));
        assertFalse(BankAccount.isEmailValid("asd@mail."));
        assertTrue(BankAccount.isEmailValid("asd@asd.cc"));
        assertTrue(BankAccount.isEmailValid("asd@asd.ccc"));

        //Equivalence Class different extentions
        assertTrue(BankAccount.isEmailValid("abc.def@mail-archive.com"));
        assertTrue(BankAccount.isEmailValid("abc.def@mail.org"));
        assertTrue(BankAccount.isEmailValid("abc.def@mail.com"));

        //Equivalence Class @ symbols
        assertFalse(BankAccount.isEmailValid("as@as@asd.com"));
        assertFalse(BankAccount.isEmailValid("ASDF@ADSA.F@ASCOM"));
        assertFalse(BankAccount.isEmailValid("asdfasdfsadf.sad"));
        assertFalse(BankAccount.isEmailValid("asdfasdfasdf"));

        assertFalse(BankAccount.isEmailValid("asd%@asddff.coc"));
        assertFalse(BankAccount.isEmailValid("asd@asddff.coco"));
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@basd.com", 100.001));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@basd.com", -100));
    }

    @Test
    void isAmountValidTest(){
        assertTrue(BankAccount.isAmountValid(10));
        assertTrue(BankAccount.isAmountValid(10.0));
        assertTrue(BankAccount.isAmountValid(10.00));
        assertTrue(BankAccount.isAmountValid(10.000000000));
        assertTrue(BankAccount.isAmountValid(10.1));
        assertTrue(BankAccount.isAmountValid(10.11));
        assertTrue(BankAccount.isAmountValid(0.0000000));
        assertTrue(BankAccount.isAmountValid(0.1));
        assertTrue(BankAccount.isAmountValid(0.01));
        assertTrue(BankAccount.isAmountValid(0.00000000001));

        assertFalse(BankAccount.isAmountValid(10.001));
        assertFalse(BankAccount.isAmountValid(10.0001));
        assertFalse(BankAccount.isAmountValid(10.00000001));
        assertFalse(BankAccount.isAmountValid(-10));
        assertFalse(BankAccount.isAmountValid(-10.0));
        assertFalse(BankAccount.isAmountValid(-10.00));
        assertFalse(BankAccount.isAmountValid(-10.00000));
        assertFalse(BankAccount.isAmountValid(-10.1));
        assertFalse(BankAccount.isAmountValid(-10.01));
        assertFalse(BankAccount.isAmountValid(-10.001));
        assertFalse(BankAccount.isAmountValid(-0.1));
        assertFalse(BankAccount.isAmountValid(-0.01));
        assertFalse(BankAccount.isAmountValid(-0.001));
    }

    @Test
    void depositTest(){
        BankAccount bankAccount = new BankAccount("a@a.cc", 0);

        bankAccount.deposit(100);
        assertEquals(100, bankAccount.getBalance(), 10);
        bankAccount.deposit(0.1);
        assertEquals(100.1, bankAccount.getBalance(), 10);
        bankAccount.deposit(0.01);
        assertEquals(100.11, bankAccount.getBalance(), 10);

        assertThrows(IllegalArgumentException.class, ()-> bankAccount.deposit(-1));
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.deposit(0));
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.deposit(-1.01));
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.deposit(1.001));
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.deposit(-1.001));
    }

    @Test
    void transferTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@a.cc", 300);
        BankAccount bankAccount1 = new BankAccount("A@AA.CC", 0);

        bankAccount.transfer(100, bankAccount1);
        assertEquals(200, bankAccount.getBalance(), 10);
        assertEquals(100, bankAccount1.getBalance(), 10);
        bankAccount.transfer(0.1, bankAccount1);
        assertEquals(199.9, bankAccount.getBalance(), 10);
        assertEquals(100.1, bankAccount1.getBalance(), 10);
        bankAccount.transfer(0.01, bankAccount1);
        assertEquals(199.89, bankAccount.getBalance(), 10);
        assertEquals(100.11, bankAccount1.getBalance(), 10);

        assertThrows(IllegalArgumentException.class, ()-> bankAccount.transfer(0, bankAccount1));
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.transfer(-1, bankAccount1));
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.transfer(-1.01, bankAccount1));
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.transfer(-1.0001, bankAccount1));
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.transfer(0.001, bankAccount1));

        assertThrows(InsufficientFundsException.class, ()->bankAccount.transfer(1000, bankAccount1));
    }



}