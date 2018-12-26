package it.zanichelli.garotta.usercrud.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class Response {
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public ListAccount getListAccount() {
		return listAccount;
	}

	public void setListAccount(ListAccount listAccount) {
		this.listAccount = listAccount;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ListUsername getListUsername() {
		return listUsername;
	}

	public void setListUsername(ListUsername listUsername) {
		this.listUsername = listUsername;
	}

	private String message;

	private String errorType;

	private ListAccount listAccount;
	
	private ListUsername listUsername;

	private Account account;

	private String code;

}
