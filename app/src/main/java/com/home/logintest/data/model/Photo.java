package com.home.logintest.data.model;

public class Photo {

    private int id;
    private String photoName;
    private String photoPath;
    private int authorId;

    public Photo(int id, String photoName, String photoPath, int authorId) {
        this.id = id;
        this.photoName = photoName;
        this.photoPath = photoPath;
        this.authorId = authorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }
}
