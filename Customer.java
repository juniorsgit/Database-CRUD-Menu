/** Project: SoloLab Assignment 3
 * Purpose Details: To create a CRUD menu for MySQL, MongoDB, Redis, and Blockchain databases
 * Course: IST242
 * Author: Junior Diaz
 * Date Developed: 2/16/2024
 * Last Date Changed: 2/18/2024
 * Rev: 1

 */

public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private String gender;

    public Customer(int id, String firstName, String lastName, int age, String email, String gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
