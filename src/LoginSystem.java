public class LoginSystem extends LoginSystemBase {

    // Class for storing user information
    private class User {
        private String email;
        private int passwordHash;

        public User(String email, int passwordHash) {
            this.email = email;
            this.passwordHash = passwordHash;
        }
    }

    User hashtable[] = new User[101];
    int numUsers = 0;

    @Override
    public int size() {
        return hashtable.length;
    }

    @Override
    public int getNumUsers() {
        return numUsers;
    }

    @Override
    public int hashCode(String key) {
        int hashCode = key.charAt(0);

        for (int i = 1; i < key.length(); i++) {
            hashCode *= 31;
            hashCode += key.charAt(i);
        }

        return hashCode;
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

    /**
     * Compressed the hashcode.
     * @param hashCode hashcode to compress.
     * @return compressed hashcode.
     */
    public int compressHash(int hashCode) {
        return hashCode % this.size();
    }

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
