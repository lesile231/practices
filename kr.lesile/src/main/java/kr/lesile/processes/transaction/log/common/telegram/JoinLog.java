package kr.lesile.processes.transaction.log.common.telegram;

import kr.lesile.processes.transaction.log.common.enums.LogType;

/**
 * 가입 로그
 */
@SuppressWarnings("unused")
public class JoinLog extends LogTelegram
{
	/**
	 * 로그 전문 설명
	 * 
	 * 로그타입	logType
	 * 고객번호 	customerNo
	 * 고객명		customerName
	 * 가입일		dateOfJoin
	 */
	
	private String logType = null;
	private String customerNo = null;
	private String customerName = null;
	private String dateOfJoin = null;
	
	
	
	
	
	public JoinLog ( String customerNo, String customerName, String dateOfJoin ) 
	{
		this.logType = LogType.JOIN_LOG;
		this.customerNo = customerNo;
		this.customerName = customerName;
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
	
	
}
