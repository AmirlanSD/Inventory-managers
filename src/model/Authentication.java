package model;

import model.objects.User;

public class Authentication {

    public static User login(String username, String password)
            throws NullPointerException {
        for (User user: User.users) {
            if ((((password.equals(user.getPassword())))) && (((username.equals(user.getUsername()))))) {
                return user;
            };
        };
        throw new NullPointerException();
    }
}
