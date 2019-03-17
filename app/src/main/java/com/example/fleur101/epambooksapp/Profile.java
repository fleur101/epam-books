package com.example.fleur101.epambooksapp;


import java.util.List;

/**
 * Created by Assylkhanov Aslan on 17.03.2019.
 */
public class Profile {

    private String avatar_link;
    private String uid;
    private String email;
    private String firstname;
    private String lastname;
    private String phone;
    private String leech_rating;
    private String seed_rating;
    private List<String> books_owned;
    private List<String> books_taken;

    public String getAvatar_link() {
        return avatar_link;
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPhone() {
        return phone;
    }

    public String getLeech_rating() {
        return leech_rating;
    }

    public void setLeech_rating(String leech_rating) {
        this.leech_rating = leech_rating;
    }

    public String getSeed_rating() {
        return seed_rating;
    }

    public void setSeed_rating(String seed_rating) {
        this.seed_rating = seed_rating;
    }

    public List<String> getBooks_owned() {
        return books_owned;
    }

    public void setBooks_owned(List<String> books_owned) {
        this.books_owned = books_owned;
    }

    public List<String> getBooks_taken() {
        return books_taken;
    }

    public void setBooks_taken(List<String> books_taken) {
        this.books_taken = books_taken;
    }
}

