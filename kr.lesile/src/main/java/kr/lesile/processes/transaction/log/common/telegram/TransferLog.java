package kr.lesile.processes.transaction.log.common.telegram;

import kr.lesile.processes.transaction.log.common.enums.LogType;

/**
 * 이체 로그
 */
@SuppressWarnings("unused")
public class TransferLog extends LogTelegram
{
	/**
	 * 로그 전문 설명
	 * 
	 * 로그타입		logType
	 * 고객번호 		customerNo
	 * 송금 계좌번호	transferAccountNo
	 * 수취 은행		receivingBank
	 * 수취 계좌번호	receivingAccountNo
	 * 수취 계좌주		receivingBankAccountHolder
	 * 이체 금액		transferAmount
	 * 이체 시각		dateOfTransfer
	 */
	
	private String logType = null;
	private String customerNo = null;
	private String transferAccountNo = null;
	private String receivingBank = null;
	private String receivingAccountNo = null;
	private String receivingBankAccountHolder = null;
	private Integer transferAmount = -1;
	private String dateOfTransfer = null;
	
	
	
	
	
	public TransferLog (	String customerNo, String transferAccountNo, 
					   	String receivingBank, String receivingAccountNo,
					   	String receivingBankAccountHolder, Integer transferAmount,
					   	String dateOfTransfer ) 
	{
		this.logType = LogType.TRANSFER_LOG;
		this.customerNo = customerNo;
		this.transferAccountNo = transferAccountNo;
		this.receivingBank = receivingBank;
		this.receivingAccountNo = receivingAccountNo;
		this.receivingBankAccountHolder = receivingBankAccountHolder;
		this.transferAmount = transferAmount;
		this.dateOfTransfer = dateOfTransfer;
	}
	
	
	
	
	
	public String getCustomerNo()
	{
		return this.customerNo;
	}
	
	
	
	public String getTransferAccountNo()
	{
		return this.transferAccountNo;
	}
	
	
	
	public String getReceivingAccountNo()
	{
		return this.receivingAccountNo;
	}
	
	
	
	public String getReceivingBank()
	{
		return this.receivingBank;
	}
	
	
	
	public String getReceivingBankAccountHolder()
	{
		return this.receivingBankAccountHolder;
	}
	
	
	
	public Integer getTransferAmount()
	{
		return this.transferAmount;
	}
	
	
	
	public String getDateOfTransfer()
	{
		return this.dateOfTransfer;
	}
	
	
}
