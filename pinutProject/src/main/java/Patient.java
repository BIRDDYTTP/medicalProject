public class Patient {
    private String id;
    private String name;
    private String lastname;
    private String address;
    private int age;

    public Patient(String id, String name, String lastname, String address, int age) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.address = address;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }
}
