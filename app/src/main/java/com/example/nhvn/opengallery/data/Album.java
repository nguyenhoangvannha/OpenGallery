package com.example.nhvn.opengallery.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable{
    private String name;
    private String path;
    private ArrayList<String> medias;

    public Album(String name, String path, ArrayList<String> photos) {
        this.name = name;
        this.path = path;
        this.medias = photos;
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

    public ArrayList<String> getMedias() {
        return medias;
    }

    public void setMedias(ArrayList<String> medias) {
        this.medias = medias;
    }

    @Override
    public String toString() {
        return " Name:" + name + " Path:" +  path;
    }
}
