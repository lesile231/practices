package kr.lesile.processes.transaction.log.common.telegram;

import kr.lesile.processes.transaction.log.common.enums.LogType;

/**
 * 입금 로그
 */
@SuppressWarnings("unused")
public class DepositLog extends LogTelegram
{
	/**
	 * 로그 전문 설명
	 * 
	 * 로그타입		logType
	 * 고객번호 		customerNo
	 * 입금 계좌번호	depositAccountNo
	 * 입금 금액		depositAmount
	 * 입금 시각		dateOfDeposit
	 */
	
	private String logType = null;
	private String customerNo = null;
	private String depositAccountNo = null;
	private Integer depositAmount = 0;
	private String dateOfDeposit = null;
	
	
	
	
	
	public DepositLog ( String customerNo, String depositAccountNo, Integer depositAmount, String dateOfDeposit ) 
	{
		this.logType = LogType.DEPOSIT_LOG;
		this.customerNo = customerNo;
		this.depositAccountNo = depositAccountNo;
		this.depositAmount = depositAmount;
		this.dateOfDeposit = dateOfDeposit;
	}
	
	
	
	
	
	public String getCustomerNo()
	{
		return this.customerNo;
	}
	
	
	
	public String getDepositAccountNo()
	{
		return this.depositAccountNo;
	}
	
	
	
	public Integer getDepositAccountAmount()
	{
		return this.depositAmount;
	}
	
	
	
	public String getDateOfDeposit()
	{
		return this.dateOfDeposit;
	}
	
	
}
