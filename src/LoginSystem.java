public class LoginSystem extends LoginSystemBase {

    // Class for storing user information
    private class User {
        private String email;
        private int passwordHash;

        public User(String email, int passwordHash) {
            this.email = email;
            this.passwordHash = passwordHash;
        }

        public String getEmail() {
            return email;
        }

        public int getPasswordHash() {
            return passwordHash;
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
        // Triple size of hashtable if not enough space
        if (numUsers == this.size()) {
            User newHashtable[] = new User[numUsers * 3];

            // Copy over existing users
            for (int i = 0; i < numUsers; i++) {
                newHashtable[i] = this.hashtable[i];
            }
        }

        // Search for user in the system
        int index = this.compressHash(this.hashCode(email));

        // Keep searching while there are entries in the hashtable
        while (this.hashtable[index] != null) {
            // Check emails match (in case there are hash collisions)
            if (this.hashtable[index].getEmail().equals(email)) {
                return false;
            }
            index = compressHash(index + 1);
        }

        // Add user into hashtable if we have found a suitable position
        this.hashtable[index] = new User(email, this.hashCode(password));
        return true;
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

    /**
     * Search for a specific user in the system.
     * @param email email of user to search for.
     * @return -1 if user not found. Index where user is stored in the hashtable.
     */
    public int searchUsers(String email) {
        int index = this.hashCode(email);

        // Keep searching while there are entries in the hashtable
        while (this.hashtable[index] != null) {
            // Check emails match (in case there are hash collisions)
            if (this.hashtable[index].getEmail().equals(email)) {
                return index;
            }
            index = compressHash(index + 1);
        }
        return -1;
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
