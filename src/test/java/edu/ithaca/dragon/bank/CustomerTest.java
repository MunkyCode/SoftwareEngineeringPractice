package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @Test
    void isEmailValidTest(){
        assertTrue(Customer.isEmailValid( "a@b.com"));
        assertFalse( Customer.isEmailValid(""));

        //Partitions of ""=invalid partition, address first, domain second, extension third

        //Equivalence class being in first partitions, with boundary value to index[0]->indexOf(@)
        //Multiple periods Partition tests
        assertFalse(Customer.isEmailValid("a..b@male.com"));
        assertTrue(Customer.isEmailValid("a.f.a@tah.com"));
        assertTrue(Customer.isEmailValid("a.ffff.a@gasd.asd"));
        assertFalse(Customer.isEmailValid("a...f@asdf.asd"));
        assertFalse(Customer.isEmailValid("abc.def@mail..com")); //Only one is allowed in the second half regardless

        //Slash partitionTest
        assertFalse(Customer.isEmailValid("abc-@mail.com"));
        assertTrue(Customer.isEmailValid("abc-d@mail.com"));

        //Invalid period Location
        assertFalse(Customer.isEmailValid(".abc@mail.com"));
        assertTrue(Customer.isEmailValid("a.a@gasd.com"));
        assertTrue(Customer.isEmailValid("abc.def@mail.com"));

        //Invalid Character Partitions
        assertFalse(Customer.isEmailValid("abc#def@mail.com"));
        assertFalse(Customer.isEmailValid("abc.def@mail#archive.com"));
        assertTrue(Customer.isEmailValid("abc@mail.com"));
        assertTrue(Customer.isEmailValid("abc_def@mail.com"));

        //Equivalence class Short or missing extentions
        assertFalse(Customer.isEmailValid("abc.def@mail.c"));
        assertFalse(Customer.isEmailValid("bc.def@mail"));
        assertFalse(Customer.isEmailValid("asd@mail."));
        assertTrue(Customer.isEmailValid("asd@asd.cc"));
        assertTrue(Customer.isEmailValid("asd@asd.ccc"));

        //Equivalence Class different extentions
        assertTrue(Customer.isEmailValid("abc.def@mail-archive.com"));
        assertTrue(Customer.isEmailValid("abc.def@mail.org"));
        assertTrue(Customer.isEmailValid("abc.def@mail.com"));

        //Equivalence Class @ symbols
        assertFalse(Customer.isEmailValid("as@as@asd.com"));
        assertFalse(Customer.isEmailValid("ASDF@ADSA.F@ASCOM"));
        assertFalse(Customer.isEmailValid("asdfasdfsadf.sad"));
        assertFalse(Customer.isEmailValid("asdfasdfasdf"));

        assertFalse(Customer.isEmailValid("asd%@asddff.coc"));
        assertFalse(Customer.isEmailValid("asd@asddff.coco"));
    }

    @Test
    void constructorTest(){
        Customer c1 = new Customer("bob", "asdfasdf");
        assertEquals("bob", c1.getId());
        assertEquals("asdfasdf", c1.getPassword());
    }

    @Test
    void createAccount() throws Exception{
        Customer c1 = new Customer("bob", "1");
        c1.createAccount(100);
        assertEquals(100, c1.getBalance());
        Customer c2 = new Customer("bob", "1");
        c2.createAccount(200);
        assertEquals(200, c2.getBalance());
        assertThrows(IllegalArgumentException.class, ()->c2.createAccount(200));
        assertThrows(IllegalArgumentException.class, ()->c2.createAccount(300));
        Customer c3 = new Customer("bob", "1");
        assertThrows(IllegalArgumentException.class, ()-> c3.createAccount(100.001));
        assertThrows(IllegalArgumentException.class, ()-> c3.createAccount(-100));
    }

    @Test
    void getBalanceTest() throws Exception{
        Customer c1 = new Customer("bob", "1");
        c1.createAccount(100);
        assertEquals(100, c1.getBalance());
        Customer c2 = new Customer("bb", "1");
        c2.createAccount(200);
        assertEquals(200, c2.getBalance());
        Customer c3 = new Customer("bbb","password");

        assertThrows(IllegalArgumentException.class, ()-> c3.getBalance());
    }
}
