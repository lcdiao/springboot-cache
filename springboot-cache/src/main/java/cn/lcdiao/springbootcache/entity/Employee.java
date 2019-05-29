package cn.lcdiao.springbootcache.entity;

public class Employee {
    private Integer id;

    private Integer gender;

    private Integer did;

    private String lastName;

    private String email;

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", gender=" + gender +
                ", did=" + did +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public Employee(Integer id, Integer gender, Integer did, String lastName, String email) {
        this.id = id;
        this.gender = gender;
        this.did = did;
        this.lastName = lastName;
        this.email = email;
    }

    public Employee() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName == null ? null : lastName.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }
}