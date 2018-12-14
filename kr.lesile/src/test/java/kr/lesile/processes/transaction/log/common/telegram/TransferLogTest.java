package kr.lesile.kakaobank.codingtest.transaction.log.common.telegram;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import kr.lesile.processes.transaction.log.common.telegram.TransferLog;

public class TransferLogTest 
{
	private TransferLog transferLog;
	
	private String customerNo;
	private String transferAccountNo;
	private String receivingBank;
	private String receivingAccountNo;
	private String receivingBankAccountHolder;
	private Integer transferAmount;
	private String dateOfTransfer;
	
	
	
	
	
	@Before
	public void setup()
	{
		this.customerNo = "CUSTOMER.1";
		this.transferAccountNo = "ACCOUNT.1";
		this.receivingBank = "BANK.2";
		this.receivingAccountNo = "ACCOUNT.2";
		this.receivingBankAccountHolder = "CUSTOMER.2";
		this.transferAmount = 1000;
		this.dateOfTransfer = "20181203";
		
		this.transferLog = new TransferLog( this.customerNo, this.transferAccountNo,
				this.receivingBank, this.receivingAccountNo, this.receivingBankAccountHolder,
				this.transferAmount, this.dateOfTransfer );
	}
	
	
	
	
	
	@Test
	public void testTransferLog() 
	{
		TransferLog newTransferLog = new TransferLog( this.customerNo, this.transferAccountNo,
													this.receivingBank, this.receivingAccountNo, 
													this.receivingBankAccountHolder, this.transferAmount, 
													this.dateOfTransfer );
		assertNotNull( newTransferLog );
	}

	
	
	@Test
	public void testGetCustomerNo() 
	{
		assertThat( "CUSTOMER.1", is( this.transferLog.getCustomerNo() ));
	}

	
	
	@Test
	public void testGetTransferAccountNo() 
	{
		assertThat( "ACCOUNT.1", is( this.transferLog.getTransferAccountNo() ));
	}
	
	
	
	@Test
	public void testGetReceivingBank()
	{
		assertThat( "BANK.2", is( this.transferLog.getReceivingBank() ));
	}

	@Test
	public void testGetReceivingAccountNo() 
	{
		assertThat( "ACCOUNT.2", is( this.transferLog.getReceivingAccountNo() ));
	}

	
	
	@Test
	public void testGetReceivingBankAccountHolder() 
	{
		assertThat( "CUSTOMER.2", is( this.transferLog.getReceivingBankAccountHolder() ));
	}

	
	
	@Test
	public void testGetTransferAmount() 
	{
		assertThat( 1000, is( this.transferLog.getTransferAmount() ));
	}
	
	
	
	@Test
	public void testGetDateOfTransfer()
	{
		assertThat( "20181203", is( this.transferLog.getDateOfTransfer() ));
	}

	
}
