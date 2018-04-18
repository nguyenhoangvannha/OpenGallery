package com.example.nhvn.opengallery.data;

import java.util.ArrayList;

public class Album {
    private String name;
    private String path;
    private ArrayList<String> photos;

    public Album(String name, String path, ArrayList<String> photos) {
        this.name = name;
        this.path = path;
        this.photos = photos;
    }

    public Album() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ArrayList<String> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }

    @Override
    public String toString() {
        return "Name=" + name + " Path=" +  path;
    }
}
