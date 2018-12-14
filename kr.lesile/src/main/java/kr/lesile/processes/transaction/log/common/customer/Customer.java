package kr.lesile.processes.transaction.log.common.customer;

import java.util.HashMap;

public class Customer 
{
	private String customerNo = null;
	private String customerName = null;
	private String dateOfJoin = null;
	
	private HashMap<String, Account> accountMap = null;

	
	

	
	public Customer( String customerNo, String customerName, String dateOfJoin )
	{
		this.customerNo = customerNo;
		this.customerName = customerName;
		this.accountMap = new HashMap<String, Account>();
		this.dateOfJoin = dateOfJoin;
	}
	
	
	
	
	
	public String getCustomerNo()
	{
		return this.customerNo;
	}
	
	
	
	public String getCustomerName()
	{
		return this.customerName;
	}
	
	
	
	public String getDateOfJoin()
	{
		return this.dateOfJoin;
	}
	
	
	
	public HashMap<String, Account> getAccountMap()
	{
		return this.accountMap;
	}
	
	
	
	public void openAccount( String accountNo, String dateOfAccountOpen )
	{
		Account newAccount = new Account( accountNo, 0, dateOfAccountOpen );
		
		synchronized( this.accountMap )
		{
			this.accountMap.put( accountNo, newAccount );
		}
	}
	
	
	
	public boolean deposit( String accountNo, int amount )
	{
		boolean result = false;
		
		Account account = this.accountMap.get( accountNo );
		
		if( account == null )
		{
			return result;
		}
		
		synchronized( account )
		{
			Integer curBalance = account.getBalance();
			
			if( curBalance != null )
			{
				account.setBalance( curBalance+amount );
				this.accountMap.put( accountNo, account );
				result = true;
			}
			
			return result;
		}
	}

	
	
	public boolean withdraw( String accountNo , int amount )
	{
		Account account = this.accountMap.get( accountNo );
		
		synchronized ( account )
		{
			Integer curBalance = account.getBalance();
			
			if( curBalance >= amount )
			{
				account.setBalance( curBalance - amount );
				this.accountMap.put( accountNo, account );
				
				return true;
			}
			else
			{
				return false;
			}
		}
	}
	
	
}
