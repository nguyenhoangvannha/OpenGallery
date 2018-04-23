package com.example.nhvn.opengallery.data;

import android.print.PrinterId;

import java.util.Date;

public class ExifInfo {
    private String title;
    private String path;
    private String type;
    private int width;
    private int height;
    private float size;
    private String sizeDataType;
    private String date;
    private String maker;
    private String iso;
    private double aperture;
    private double exposureTime;
    private int shutterSpeed;
    private double focalDistance;
    private float resolution;
    private int orientation;

    public ExifInfo() {

    }

    @Override
    public String toString() {
        String result = "<b>Title</b> " + title + "<br/><b>Path</b> " + path + "<br/><b>Type</b> " + type + "<br/><b>Size</b> "
                + width + "x" + height + "<br/><b>Size</b> " + size + " " + sizeDataType + "<br/><b>Date</b> "
                + date + "<br/><b>ISO</b> " + iso + "<br/><b>Aperture</b> " + aperture
                + "<br/><b>Exposure time</b> " + exposureTime + " s"  + "<br/><b>Focal length</b> " + focalDistance + " mm"
                + "<br/><b>Orientation</b> " + orientation;
        return result;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public String getSizeDataType() {
        return sizeDataType;
    }

    public double getExposureTime() {
        return exposureTime;
    }

    public void setExposureTime(double exposureTime) {
        this.exposureTime = exposureTime;
    }

    public void setSizeDataType(String sizeDataType) {
        this.sizeDataType = sizeDataType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public double getAperture() {
        return aperture;
    }

    public void setAperture(double aperture) {
        this.aperture = aperture;
    }

    public int getShutterSpeed() {
        return shutterSpeed;
    }

    public void setShutterSpeed(int shutterSpeed) {
        this.shutterSpeed = shutterSpeed;
    }

    public double getFocalDistance() {
        return focalDistance;
    }

    public void setFocalDistance(double focalDistance) {
        this.focalDistance = focalDistance;
    }

    public float getResolution() {
        return resolution;
    }

    public void setResolution(float resolution) {
        this.resolution = resolution;
    }


}
