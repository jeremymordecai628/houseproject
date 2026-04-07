package com.modex.model;

public class services { 
	private String AccountNo;
	private  String servicename;

	public services(String AccountNo , String servicename){
		this.AccountNo=AccountNo;
		this.servicename=servicename;
	}

	public String Account() {
		return AccountNo;
	}

	public String Name(){
		return servicename;
	}
}
