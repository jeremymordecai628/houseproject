package com.modex.model;

public class ClientData {
    private String id;
    private String name;
    private String downloadID;

    // Constructor (3 parameters)
    public ClientData(String id, String name, String downloadID) {
        this.id = id;
        this.name = name;
        this.downloadID = downloadID;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDownloadID() {
        return downloadID;
    }

    // (Optional but recommended) setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDownloadID(String downloadID) {
        this.downloadID = downloadID;
    }
}
