package kr.lesile.processes.transaction.log.common.customer;

public class Account 
{
	private String accountNo = null;
	private Integer balance = null;
	private String dateOfAccountOpen = null;
	
	
	
	
	
	public Account( String accountNo, Integer balance, String dateOfAccountOpen )
	{
		this.accountNo = accountNo;
		this.balance = balance;
		this.dateOfAccountOpen = dateOfAccountOpen;
	}
	
	
	
	
	
	public String getAccountNo()
	{
		return this.accountNo;
	}
	
	
	
	public Integer getBalance()
	{
		return this.balance;
	}
	
	
	
	public void setBalance( Integer balance )
	{
		this.balance = balance;
	}
	
	
	
	public String getDateOfAccountOpen()
	{
		return this.dateOfAccountOpen;
	}
	
	
}
