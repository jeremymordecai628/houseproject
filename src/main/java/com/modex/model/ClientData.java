package com.modex.model;

public class ClientData {
    private String id;
    private String name;
    private String downloadid;

    public ClientData(String id, String name) {
        this.id = id;
        this.name = name;
	this.downloadid=downloadid;
    }

    public String  getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getDownloadID(){
	    return downloadid;
    }
}
