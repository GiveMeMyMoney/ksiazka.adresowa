package model;

/**
 * Created by Marcin on 2015-10-21.
 */
public class Contact {
    int id;
    String name, surname, province, address, postalCode, city;

    public Contact(int id, String name, String surname, String province, String address, String postalCode, String city) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.province = province;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
    }

    public Contact(String name, String surname, String province, String address, String postalCode, String city) {
        this.name = name;
        this.surname = surname;
        this.province = province;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
    }

    //GETTERs:
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getProvince() {
        return province;
    }
    public String getAddress() {
        return address;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public String getCity() {
        return city;
    }

    //SETTERs:
    public void setName(String name) {
        this.name = name;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", province='" + province + '\'' +
                ", address='" + address + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
