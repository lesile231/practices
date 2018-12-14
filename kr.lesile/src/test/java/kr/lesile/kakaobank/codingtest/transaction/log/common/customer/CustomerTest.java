package kr.lesile.kakaobank.codingtest.transaction.log.common.customer;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import kr.lesile.processes.transaction.log.common.customer.Account;
import kr.lesile.processes.transaction.log.common.customer.Customer;

public class CustomerTest 
{
	Customer customer;
	String customerNo;
	String customerName;
	String dateOfJoin;
	
	String accountNo;
	String dateOfAccountOpen;
	
	
	
	@Before
	public void setup()
	{
		this.customerNo = "CUSTOMER.1";
		this.customerName = "LEE.1";
		this.dateOfJoin = "20181203";
		this.customer = new Customer( this.customerNo, this.customerName, this.dateOfJoin );

		this.accountNo = "ACCOUNT.1";
		this.dateOfAccountOpen = "20181204";
		
		Account newAccount = new Account( this.accountNo, 0, this.dateOfAccountOpen );
		this.customer.getAccountMap().put( this.accountNo, newAccount);
	}
	
	
	
	
	
	@Test
	public void testCustomer() 
	{
		Customer testCustomer = new Customer( this.customerNo, this.customerName, this.dateOfJoin );
		assertNotNull( testCustomer );
	}

	
	
	@Test
	public void testGetCustomerNo() 
	{
		assertThat("CUSTOMER.1", is( this.customer.getCustomerNo() ));
	}

	
	
	@Test
	public void testGetCustomerName() 
	{
		assertThat("LEE.1", is( this.customer.getCustomerName() ));
	}

	
	
	@Test
	public void testGetDateOfJoin() 
	{
		assertThat("20181203", is( this.customer.getDateOfJoin() ));
	}

	
	
	@Test
	public void testGetAccountMap() 
	{
		assertNotNull( this.customer.getAccountMap() );
		assertSame( this.customer.getAccountMap(), this.customer.getAccountMap() );
	}

	
	
	@Test
	public void testOpenAccount() 
	{
		Account newAccount = new Account( this.accountNo, 0, dateOfAccountOpen );
		
		assertNotNull( newAccount );
		assertNotNull( this.customer.getAccountMap() );
		
		this.customer.getAccountMap().put( this.accountNo, newAccount );
		
		assertNotNull( this.customer.getAccountMap().get(this.accountNo) );
	}

	
	
	@Test
	public void testDeposit() 
	{
		Integer amount = 1000;
		Account account = this.customer.getAccountMap().get( this.accountNo );
		
		assertNotNull( account );
		
		Integer curBalance = account.getBalance();
		
		assertNotNull( curBalance );
		assertThat(0, is( curBalance ));
		
		account.setBalance( curBalance+amount );
		this.customer.getAccountMap().put( this.accountNo, account );

		assertThat(1000, is( this.customer.getAccountMap().get(this.accountNo).getBalance() ));
	}
	
	
	
	@Test
	public void testWithdraw() 
	{
		Integer amount = 500;
		Account account = this.customer.getAccountMap().get( this.accountNo );
		
		assertNotNull( account );
		
		Integer curBalance = account.getBalance();

		assertNotNull( curBalance );
		
		if( curBalance >= amount )
		{
			account.setBalance( curBalance - amount );
			this.customer.getAccountMap().put( this.accountNo, account );
		}
		
		assertThat(0, is( this.customer.getAccountMap().get(this.accountNo).getBalance() ));
	}
	
	
}