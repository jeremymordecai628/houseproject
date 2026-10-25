package com.modex.model;

import java.time.LocalDateTime;

public class Transaction {
	//Declar variables
	private String trans_id;
	private LocalDateTime paymentdate;
	private double amount;
	private String paid_id;

	public Transaction(String trans_id, LocalDateTime paymentdate, double amount, String paid_id){
		this.trans_id=trans_id;
		this.paymentdate=paymentdate;
		this.amount=amount;
		this.paid_id=paid_id;
	}
	
	public String get_transid(){
		return trans_id;
	}

	public LocalDateTime  get_paymentdate(){
		return paymentdate;
	}

	public double get_amount(){
		return amount;
	}

	public  String get_paid_id(){
		return paid_id;
	}

}
