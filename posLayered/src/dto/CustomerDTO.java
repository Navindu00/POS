package dto;

public class CustomerDTO {
    private int id;
    private String name;
    private String address;
    private String email;
    private int postalCode;
    
    public CustomerDTO() {
    }

    public CustomerDTO(int id, String name, String address, String email, int postalCode) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.postalCode = postalCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "Customer [id=" + id + ", name=" + name + ", address=" + address + ", email=" + email + ", postalCode="
                + postalCode + "]";
    }
}
