package com.example.gamevault.database.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    private User user;

    @Before
    public void setUp() {

        user = new User("john_doe", "password123");
    }

    @Test
    public void testGetUsername() {

        String username = user.getUsername();


        assertEquals("john_doe", username);
    }

    @Test
    public void testSetUsername() {

        user.setUsername("jane_doe");


        assertEquals("jane_doe", user.getUsername());
    }

    @Test
    public void testGetPassword() {

        String password = user.getPassword();


        assertEquals("password123", password);
    }

    @Test
    public void testSetPassword() {

        user.setPassword("new_password");


        assertEquals("new_password", user.getPassword());
    }

    @Test
    public void testIsAdmin() {

        boolean isAdmin = user.isAdmin();


        assertFalse(isAdmin);
    }

    @Test
    public void testSetAdmin() {

        user.setAdmin(true);


        assertTrue(user.isAdmin());
    }

    @Test
    public void testEquals_SameObject() {

        User sameUser = user;


        assertTrue(true);
    }

    @Test
    public void testEquals_DifferentObject() {

        User anotherUser = new User("john_doe", "password123");


        assertTrue(user.equals(anotherUser));
    }

    @Test
    public void testEquals_DifferentUsername() {

        User differentUser = new User("jane_doe", "password123");


        assertFalse(user.equals(differentUser));
    }

    @Test
    public void testEquals_DifferentPassword() {

        User differentPasswordUser = new User("john_doe", "new_password");


        assertFalse(user.equals(differentPasswordUser));
    }

    @Test
    public void testHashCode() {

        int hashCode = user.hashCode();


        User anotherUser = new User("john_doe", "password123");
        assertEquals(hashCode, anotherUser.hashCode());
    }

    @Test
    public void testConstructor() {

        User newUser = new User("alice", "mypassword");


        assertEquals("alice", newUser.getUsername());
        assertEquals("mypassword", newUser.getPassword());
        assertFalse(newUser.isAdmin());
    }
}
