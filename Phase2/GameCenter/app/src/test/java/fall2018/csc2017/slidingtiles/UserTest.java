package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UserTest {

    User user = new User("a","b","c",1,2,3);

    /**
     * Test getting the email of a user
     */
    @Test
    public void testGetEmail() {
        assertTrue(user.getEmail().equals("a"));
    }

    /**
     * Test setting the email of a user
     */
    @Test
    public void testSetEmail() {
        user.setEmail("d");
        assertTrue(user.getEmail().equals("d"));

    }
    /**
     * Test getting the password of a user
     */
    @Test
    public void testGetPassword() {
        assertTrue(user.getPassword().equals("b"));
    }

    /**
     * Test setting the password of a user
     */
    @Test
    public void testSetPassword() {
        user.setPassword("e");
        assertTrue(user.getPassword().equals("e"));

    }

    /**
     * Test getting the nickname of a user
     */
    @Test
    public void testGetNickname() {
        assertTrue(user.getNickname().equals("c"));
    }

    /**
     * Test setting the nickname of a user
     */
    @Test
    public void testSetNickname() {
        user.setNickname("f");
        assertTrue(user.getNickname().equals("f"));

    }

    /**
     * Test getting the user's score for the game sliding tiles
     */
    @Test
    public void testGetMMSliding() {
        assertTrue(user.getMMSliding().equals(1));
    }

    /**
     * Test setting the user's score for the game sliding tiles
     */
    @Test
    public void testSetMMSliding() {
        user.setMMSliding(10);
        assertTrue(user.getMMSliding().equals(10));
    }

    /**
     * Test getting the user's score for the game matching tiles
     */
    @Test
    public void testGetMMMatching() {
        assertTrue(user.getMMMatching().equals(2));
    }

    /**
     * Test setting the user's score for the game matching tiles
     */
    @Test
    public void testSetMMMatching() {
        user.setMMMatching(764);
        assertTrue(user.getMMMatching().equals(764));
    }

    /**
     * Test getting the user's score for the game 2048
     */
    @Test
    public void testGetMM2048() {
        assertTrue(user.getMM2048().equals(3));
    }

    /**
     * Test setting the user's score for the game 2048
     */
    @Test
    public void testSetMM2048() {
        user.setMM2048(100);
        assertTrue(user.getMM2048().equals(100));
    }

}
