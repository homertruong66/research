package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;

public class BankDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = -4713228468785959182L;

	private String branch;
	private String name;
	private String number;
	private String username;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "BankDto{" +
				"branch='" + branch + '\'' +
				", name='" + name + '\'' +
				", number='" + number + '\'' +
				", username='" + username + '\'' +
				'}';
	}
}