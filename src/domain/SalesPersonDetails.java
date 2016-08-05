package domain;

/**
 * Created by jovana on 6/14/16.
 */
public class SalesPersonDetails {

    private String email;
    private String password;
    private String table;
    private String firstName;
    private String lastName;
    private int stylistID;
    public boolean isValid;

    public SalesPersonDetails(String email, String password, String table, String firstName, String lastName, boolean isValid){
        this.email = email;
        this.password = password;
        this.table = table;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isValid = isValid;
    }

    public String getEmail() { return email;}

    public String getPassword() { return password;}

    public String getTable() { return table;}

    public String getFirstName() {return firstName;}

    public String getLastName() {return lastName;}

    public String getFullName() {return firstName + " " + lastName;}
    public int getStylistID() {return stylistID;}
}
