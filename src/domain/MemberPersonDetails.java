package domain;

/**
 * Created by jovana on 6/29/16.
 */
public class MemberPersonDetails {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String addressLine1;
    private String addressLine2;
    private String zipCode;
    private String state;
    private String creditCardNumber;
    private String creditCardExpiration;
    private String accountCredit;
    private String discount;
    public boolean isValid;

    public MemberPersonDetails(String firstName,
                                String lastName,
                                String email,
                                String password,
                                String phoneNumber,
                                String addressLine1,
                                String addressLine2,
                                String zipCode,
                                String state,
                                String creditCardNumber,
                                String creditCardExpiration,
                                String accountCredit,
                                String discount,
                                boolean isValid){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.zipCode = zipCode;
        this.state = state;
        this.creditCardNumber = creditCardNumber;
        this.creditCardExpiration = creditCardExpiration;
        this.accountCredit = accountCredit;
        this.discount = discount;
        this.isValid = isValid;
    }

    public String getEmail() { return email;}

    public String getPassword() { return password;}

    public String getFirstName() { return firstName;}

    public String getLastName() {return lastName;}

    public String getPhoneNumber() {return phoneNumber;}

    public String getAddressLine1() {return addressLine1;}

    public String getAddressLine2() {return addressLine2;}

    public String getZipCode() {return zipCode;}

    public String getState() {return state;}

    public String getCreditCardNumber() {return creditCardNumber;}

    public String getCreditCardExpiration() {return creditCardExpiration;}

    public String getAccountCredit() {return accountCredit;}

    public String getDiscount() {return discount;}



}
