package com.deb.videofy;

public class file {
    private String Date,Filename;

    public file(String date, String filename) {
        Date = date;
        Filename = filename;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getFilename() {
        return Filename;
    }

    public void setFilename(String filename) {
        Filename = filename;
    }
}
