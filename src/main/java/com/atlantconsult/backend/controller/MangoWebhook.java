package com.atlantconsult.backend.controller;

import java.io.Serializable;

public class MangoWebhook implements Serializable {

	private static final long serialVersionUID = 6277341888190856658L;
	private String callerNumber;
	private String gaCid;
	private String yaCid;

	public MangoWebhook(String callerNumber, String gaCid, String yaCid) {
		super();
		this.callerNumber = callerNumber;
		this.gaCid = gaCid;
		this.yaCid = yaCid;
	}

	public MangoWebhook() {
		super();
		this.callerNumber = "";
		this.gaCid = "";
		this.yaCid = "";
	}

	public String getCallerNumber() {
		return callerNumber;
	}

	public String getGaCid() {
		return gaCid;
	}

	public String getYaCid() {
		return yaCid;
	}

}
