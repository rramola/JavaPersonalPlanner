public class User {
    private String  email;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    public User(String email, String firstName, String lastName, String username, String password){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public String getEmail(){
        return email;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }
}
