public class LoginSystem extends LoginSystemBase {

    @Override
    public int size() {
        /* Add your code here! */
        return 0;
    }

    @Override
    public int getNumUsers() {
        /* Add your code here! */
        return 0;
    }

    @Override
    public int hashCode(String key) {
        /* Add your code here! */
        return 0;
    }

    @Override
    public boolean addUser(String email, String password) {
        /* Add your code here! */
        return false;
    }

    @Override
    public boolean removeUser(String email, String password) {
        /* Add your code here! */
        return false;
    }

    @Override
    public int checkPassword(String email, String password) {
        /* Add your code here! */
        return 0;
    }

    @Override
    public boolean changePassword(String email, String oldPassword, String newPassword) {
        /* Add your code here! */
        return false;
    }

    /* Add any extra functions below */

    public static void main(String[] args) {
        /*
         * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
         * REMOVE THE MAIN METHOD BEFORE SUBMITTING TO THE AUTOGRADER
         * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
         * The following main method is provided for simple debugging only
         */
        LoginSystem loginSystem = new LoginSystem();
        assert loginSystem.hashCode("GQHTMP") == loginSystem.hashCode("H2HTN1");
        assert loginSystem.size() == 101;

        assert loginSystem.checkPassword("a@b.c", "L6ZS9") == -1;
        loginSystem.addUser("a@b.c", "L6ZS9");
        assert loginSystem.checkPassword("a@b.c", "ZZZZZZ") == -2;
        assert loginSystem.checkPassword("a@b.c", "L6ZS9") == 94;
        loginSystem.removeUser("a@b.c", "L6ZS9");
        assert loginSystem.checkPassword("a@b.c", "L6ZS9") == -1;
    }
}
