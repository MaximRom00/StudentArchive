package S3.user;


import S3.cases.Archive;

public class User {
    private String login;
    private String password;
    private UsersTypes usersTypes;
    private Archive archive = new Archive();

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UsersTypes getUsersTypes() {
        return usersTypes;
    }

    public void setUsersTypes(UsersTypes usersTypes) {
        this.usersTypes = usersTypes;
    }

    @Override
    public String toString() {
        return  usersTypes.getName() + ". login - " + login + ", password: " + password ;
    }
}
