package Hello.bean;

import java.util.Date;

/**
 * Created by Alex on 2017/7/21.
 */
public class User {
    String name;
    int age;
    String position;
    String phone;
    Date date;

    public User(String name, int age, String position, String phone) {
        this.name = name;
        this.age = age;
        this.position = position;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
