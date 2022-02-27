package S3.user;

public enum UsersTypes {
    Administrator("Admin"),
    User("User");

    private String name;

    UsersTypes(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "UsersTypes{" +
                "name='" + name + '\'' +
                '}';
    }
}
