public class LoginSystem extends LoginSystemBase {

    // Class for storing user information
    private static class User {
        private final String email;
        private int passwordHash;
        private boolean deleted;

        public User(String email, int passwordHash) {
            this.email = email;
            this.passwordHash = passwordHash;
            this.deleted = false; // Not deleted by default
        }

        /**
         * Gets the plaintext email.
         * @return Email of the user.
         */
        public String getEmail() {
            return email;
        }

        /**
         * Return hashed password of the user.
         * @return Hash code of user's password.
         */
        public int getPasswordHash() {
            return passwordHash;
        }

        /**
         * Check whether a user has been deleted from the system.
         * @return True if user has been deleted, false otherwise.
         */
        public boolean isDeleted() {
            return deleted;
        }

        /**
         * Set a user to be deleted from the system.
         */
        public void setDeleted() {
            this.deleted = true;
        }
    }

    private User hashtable[] = new User[101];
    private int numUsers = 0;

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
        // Triple size of hashmap if not enough space
        if (numUsers == this.size()) {
            User newHashtable[] = new User[numUsers * 3];

            // Take existing users (who have not been deleted) and insert them into correct indexes of new hashmap
            for (int i = 0; i < numUsers; i++) {
                User user = this.hashtable[i];
                if (!(user.isDeleted()) && (user != null)) {
                    newHashtable[this.hashCode(user.getEmail()) % (numUsers * 3)] = user;
                }
            }
            this.hashtable = newHashtable;
        }

        // Search for next suitable index to add user into, and check if user is already in the system if there is
        // a hash collision
        int index = this.compressHash(this.hashCode(email));

        // Keep searching while there are entries in the hashtable and the entries are not "deleted"
        while ((this.hashtable[index] != null) && !(this.hashtable[index].isDeleted())) {
            // Check emails match (in case there are hash collisions)
            if (this.hashtable[index].getEmail().equals(email)) {
                return false;
            }
            index = compressHash(index + 1);
        }

        // Add user into hashtable if we have found a suitable position (will replace deleted records if one exists)
        this.hashtable[index] = new User(email, this.hashCode(password));
        this.numUsers += 1;
        return true;
    }

    @Override
    public boolean removeUser(String email, String password) {
        int index = this.searchUsers(email);
        // Check user exists
        if (index == -1) {
            return false;
        } else {
            // Check passwords match
            if (this.hashtable[index].getPasswordHash() == this.hashCode(password)) {
                // Set user to be "deleted" from system so that linear probing search will still work properly
                this.hashtable[index].setDeleted();
                this.numUsers -= 1;
                return true;
            }
        }
        return false;
    }

    @Override
    public int checkPassword(String email, String password) {
        int index = this.searchUsers(email);

        if (index > -1) {
            // Check passwords match
            if (this.hashtable[index].getPasswordHash() == this.hashCode(password)) {
                return index;
            } else {
                return -2;
            }
        }
        return -1;
    }

    @Override
    public boolean changePassword(String email, String oldPassword, String newPassword) {
        int index = this.searchUsers(email);

        if (index != -1) {
            // Set new password if old password given is correct
            if (this.hashtable[index].getPasswordHash() == this.hashCode(oldPassword)) {
                this.hashtable[index].passwordHash = this.hashCode(newPassword);
                return true;
            }
        }
        return false; // User not found or oldPassword incorrect
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
        int index = compressHash(this.hashCode(email));
        int startIndex = index;

        // Keep searching while there are entries in the hashtable
        while ((this.hashtable[index] != null)) {
            // Check emails match (in case there are hash collisions)
            if (this.hashtable[index].getEmail().equals(email)) {
                if (this.hashtable[index].isDeleted()) {
                    return -1;
                } else {
                    return index;
                }
            }
            index = compressHash(index + 1);

            // Stop searching if we have returned to starting position
            if (index == startIndex) {
                return -1;
            }
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
