package com.example.mt_2016.marathon_blood.Models;

/**
 * Created by BabarMustafa on 2/14/2017.
 */

//public class User {
//}

public class User {
    private String UID;
    private String Name;
    private String Email;
    private String Country;
    private String City;
    private String Password;
    private String GEnder;
    private String Blood_Group;
    private String Profile_image;
    private String realAdmin;
    private String groupName;

    public User() {
    }

    public User(String UID) {
        this.UID = UID;
    }

    public User(String UID, String name, String email, String country, String city, String password, String GEnder, String bloodGroup) {
        this.UID = UID;
        Name = name;
        Email = email;
        Country = country;
        City = city;
        Password = password;
        this.GEnder = GEnder;
        Blood_Group = bloodGroup;
    }
    public User(String UID, String name, String email, String country, String city, String password, String GEnder, String bloodGroup, String profile_image) {
        this.UID = UID;
        Name = name;
        Email = email;
        Country = country;
        City = city;
        Password = password;
        this.GEnder = GEnder;
        Blood_Group = bloodGroup;
        Profile_image = profile_image;
    }
    public User(String UID, String name, String email, String country, String city, String password
            , String GEnder, String bloodGroup, String profile_image,String realAdmin,String groupName) {
        this.UID = UID;
        Name = name;
        Email = email;
        Country = country;
        City = city;
        this.groupName =groupName;
        Password = password;
        this.GEnder = GEnder;
        Blood_Group = bloodGroup;
        Profile_image = profile_image;
        this.realAdmin=realAdmin;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getRealAdmin() {
        return realAdmin;
    }

    public void setRealAdmin(String realAdmin) {
        this.realAdmin = realAdmin;
    }

    public String getProfile_image() {
        return Profile_image;
    }

    public void setProfile_image(String profile_image) {
        Profile_image = profile_image;
    }

    public String getUID() {
        return UID;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getGEnder() {
        return GEnder;
    }

    public void setGEnder(String GEnder) {
        this.GEnder = GEnder;
    }

    public String getBlood_Group() {
        return Blood_Group;
    }

    public void setBlood_Group(String blood_Group) {
        Blood_Group = blood_Group;
    }
}
