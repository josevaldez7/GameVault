package com.example.gamevault;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.gamevault.database.entities.User;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    User user;

    @Test
    public void userTest(){
        user = new User("testUser", "testPassword");
        assertNotNull(user);
        assertEquals(user.getUsername(), "testUser");
        assertEquals(user.getPassword(), "testPassword");
        assertFalse(user.isAdmin());
    }

    @Test
    public void changeUserName(){
        user = new User("testUser", "testPassword");
        user.setUsername("newUserName");
        assertNotEquals("testUser", user.getUsername());
        assertEquals("newUserName", user.getUsername());
    }

    @Test
    public void changePassWord(){
        user = new User("testUser", "testPassword");
        user.setPassword("newPassWord");
        assertNotEquals("testPassword", user.getUsername());
        assertEquals("newPassWord", user.getPassword());
    }

}