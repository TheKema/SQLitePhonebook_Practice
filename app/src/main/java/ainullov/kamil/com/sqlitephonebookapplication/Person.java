package ainullov.kamil.com.sqlitephonebookapplication;

public class Person {

    private String name;
    private String phoneNumber;
    private String desc;
    private int id;

    public Person(String name, String phoneNumber, String desc, int id) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.desc = desc;
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
