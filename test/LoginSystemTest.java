import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LoginSystemTest {

    /** Login system for the tests. */
    LoginSystem system;

    /** Set up a new LoginSystem for each test. */
    @Before
    public void setUp() {
        system = new LoginSystem();
    }

    /** Test initial system with no entries. */
    @Test
    public void testInitialSystem() {
        assertEquals(101, system.size());
        assertEquals(0, system.getNumUsers());
    }

    /** Test hash function is correct. */
    @Test
    public void testHashCode() {
        assertEquals(97, system.hashCode("a"));
        assertEquals(96354, system.hashCode("abc"));
        assertEquals(2109703065, system.hashCode("GQHTMP"));
        assertEquals(system.hashCode("GQHTMP"), system.hashCode("H2HTN1"));
    }

    /** Test compression function. */
    @Test
    public void testCompression() {
        assertEquals(97, system.compressHash(system.hashCode("a")));
        assertEquals(0, system.compressHash(system.hashCode("abc")));
        assertEquals(16 , system.compressHash(system.hashCode("GQHTMP")));
    }

    /** Test search function recognises a user does not exist. */
    @Test
    public void testSearchNotExists() {
        assertEquals(-1, system.searchUsers("a@b.c"));
    }

    /** Test adding users to the system and searching for the users. */
    @Test
    public void testAddUser() {
        // Test adding a single user and searching for them
        system.addUser("a@b.c", "L6ZS9");
        assertEquals(94, system.searchUsers("a@b.c"));

        // Test adding another user whose hash code does not collide with existing hashes
        system.addUser("d@e.f", "L6ZS9");
        assertEquals(83, system.searchUsers("d@e.f"));

        // Test adding two users whose hash code collides with an existing hash (test that linear probing works as
        // expected)
        system.addUser("GQHTMP", "L6ZS9");
        assertEquals(16, system.searchUsers("GQHTMP"));

        system.addUser("H2HTN1", "L6ZS9");
        assertEquals(17, system.searchUsers("H2HTN1"));

        // Test adding a user that already exists
        assertFalse(system.addUser("H2HTN1", "L6ZS9"));

        // Test getting number of users in system
        assertEquals(4, system.getNumUsers());
    }

    /** Test deleting a user from the system. */
    @Test
    public void testRemoveUser() {
        // Test removing a user from the system
        system.addUser("GQHTMP", "L6ZS9");
        assertEquals(16, system.searchUsers("GQHTMP"));
        assertTrue(system.removeUser("GQHTMP", "L6ZS9"));
        assertEquals(-1, system.searchUsers("GQHTMP"));

        // Test adding a new user into the deleted position
        system.addUser("H2HTN1", "L6ZS9");
        assertEquals(16, system.searchUsers("H2HTN1"));

        // Test adding a new user into a linearly probed position which has been deleted
        assertTrue(system.removeUser("H2HTN1", "L6ZS9"));
        system.addUser("GQHTMP", "L6ZS9");
        assertEquals(16, system.searchUsers("GQHTMP"));
        assertEquals(-1, system.searchUsers("H2HTN1"));
        system.addUser("H2HTN1", "L6ZS9");
        assertEquals(17, system.searchUsers("H2HTN1"));

        // Test removing a user that does not exist
        assertFalse(system.removeUser("abcde", "a"));

        // Test removing a user with an incorrect password
        assertFalse(system.removeUser("H2HTN1", "A"));

        // Test getting number of users in system
        assertEquals(2, system.getNumUsers());
    }

    /** Test checking a user's password in the system. */
    @Test
    public void testCheckPassword() {
        // Check password for user who does not exist
        assertEquals(-1, system.checkPassword("a@b.c", "L6ZS9"));
        system.addUser("a@b.c", "L6ZS9");

        // Check incorrect password given
        assertEquals(-2, system.checkPassword("a@b.c", "ZZZZZZ"));

        // Check correct password given
        assertEquals(94, system.checkPassword("a@b.c", "L6ZS9"));

        // Check removing user and then checking password
        system.removeUser("a@b.c", "L6ZS9");
        assertEquals(-1, system.checkPassword("a@b.c", "L6ZS9"));

        // Check password of users who have the same hash
        system.addUser("H2HTN1", "AAAAA");
        system.addUser("GQHTMP", "L6ZS9");
        assertEquals(16, system.checkPassword("H2HTN1", "AAAAA"));
        assertEquals(17, system.checkPassword("GQHTMP", "L6ZS9"));

        // Test checking password of a user who has a hash collision with another user (who has been removed)
        system.removeUser("H2HTN1", "AAAAA");
        assertEquals(17, system.searchUsers("GQHTMP"));
    }

    /** Test changing a user's password in the system. */
    @Test
    public void testChangePassword() {
        // Change password for user who does not exist
        assertFalse(system.changePassword("H2HTN1", "AAAAA", "AAAAA"));

        // Change password for existing user
        system.addUser("H2HTN1", "AAAAA");
        assertTrue(system.changePassword("H2HTN1", "AAAAA", "BBBBB"));
        assertEquals(16, system.checkPassword("H2HTN1", "BBBBB"));

        // Attempt to change password but give incorrect oldPassword
        assertFalse(system.changePassword("H2HTN1", "CCCCC", "DDDDD"));

        // Change password of a user who has a hash collision
        system.addUser("GQHTMP", "CCCCC");
        assertTrue(system.changePassword("GQHTMP", "CCCCC", "DDDDD"));
        assertEquals(17, system.checkPassword("GQHTMP", "DDDDD"));
    }
}
