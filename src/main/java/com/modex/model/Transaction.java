package com.modex.model;

import java.time.LocalDateTime;

public class Transaction {
	//Declar variables
	private String trans_id;
	private LocalDateTime paymentdate;
	private double amount;
	private String paid_id;
	private double balance;
	private Status status;

	public enum Status {
		Succeful,
		Failure,
		Complete,
		Incomplete,
		Hold
	}

	public Transaction(String trans_id, LocalDateTime paymentdate, double amount, String paid_id, double balance, Status status){
		this.trans_id=trans_id;
		this.paymentdate=paymentdate;
		this.amount=amount;
		this.paid_id=paid_id;
		this.balance=balance;
		this.status=status;
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

	public double get_balance(){
		return balance;
	}

	public  String get_paid_id(){
		return paid_id;
	}

	public Status get_status() {
		return status;
	}
}
