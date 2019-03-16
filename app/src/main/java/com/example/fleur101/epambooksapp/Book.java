package com.example.fleur101.epambooksapp;

import com.google.firebase.Timestamp;

import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * Created by acer on 16.03.2019.
 */

public class Book {
    private String id;
    private String title;
    private List<String> authors;
    private String isbn;
    private String description;
    private String imgURL;
    private String publisher;
    private Timestamp publish_date;

    public Book(){
        super();
    }

    public Book (String id, String title, List<String>  authors,  String isbn, String description, String imgURL,  String publisher, Timestamp publish_date){
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.isbn = isbn;
        this.description = description;
        this.imgURL = imgURL;
        this.publisher = publisher;
        this.publish_date = publish_date;
    }

    public void setTitle(String bookName) {
        this.title = title;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPublish_date(Timestamp publish_date) {
        this.publish_date = publish_date;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getDescription() {
        return description;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getPublisher() {
        return publisher;
    }

    public Timestamp getPublish_date() {
        return publish_date;
    }



}
