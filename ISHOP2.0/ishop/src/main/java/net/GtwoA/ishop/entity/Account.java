package net.GtwoA.ishop.entity;

import net.GtwoA.ishop.model.CurrentAccount;

public class Account extends AbstractEntity<Integer> implements CurrentAccount {


	private static final long serialVersionUID = 977906676393142028L;
	
	private String name;
	private String email;
	
	public Account(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}
	
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
		@Override
	public String getDescription() {
		 
		return name + "("+ email + ")";
		}
		
		
	@Override
	public String toString() {
		return String.format("Account [id=%s, name=%s, email=%s]",getId(), name, email);
	}

	
	
	
}
