package kr.lesile.processes.transaction.log.common.telegram;

import kr.lesile.processes.transaction.log.common.enums.LogType;

/**
 * 계좌개설 로그
 */
@SuppressWarnings("unused")
public class AccountOpenLog extends LogTelegram
{
	/**
	 * 로그 전문 설명
	 *
	 * 로그타입		logType
	 * 고객번호 		customerNo
	 * 계좌번호		accountNo
	 * 개설 시각		dateOfAccountOpen
	 */
	
	private String logType = null;
	private String customerNo = null;
	private String accountNo = null;
	private String dateOfAccountOpen = null;
	
	
	
	
	
	public AccountOpenLog ( String customerNo, String accountNo, String dateOfAccountOpen ) 
	{
		this.logType = LogType.ACCOUNT_OPEN_LOG;
		this.customerNo = customerNo;
		this.accountNo = accountNo;
		this.dateOfAccountOpen = dateOfAccountOpen;
	}
	
	
	
	
	
	public String getCustomerNo()
	{
		return this.customerNo;
	}
	
	
	
	public String getAccountNo()
	{
		return this.accountNo;
	}
	
	
	
	public String getDateOfAccountOpen()
	{
		return this.dateOfAccountOpen;
	}
	
	
}
