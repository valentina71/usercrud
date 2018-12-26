package it.zanichelli.garotta.usercrud.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("restriction")
@XmlRootElement(name = "listaccounts")
public class ListAccount {

	private List<Account> accounts;

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

}
