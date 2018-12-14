package kr.lesile.processes.transaction.log.common.telegram;

import kr.lesile.processes.transaction.log.common.enums.LogType;

/**
 * 출금 로그
 */
@SuppressWarnings("unused")
public class WithdrawLog extends LogTelegram
{
	/**
	 * 로그 전문 설명
	 * 
	 * 로그타입		logType	
	 * 고객번호 		customerNo
	 * 출금 계좌번호	withdrawAccountNo
	 * 출금 금액		withdrawAmount
	 * 출금 시각		dateOfWithdraw
	 */
	
	private String logType = null;
	private String customerNo = null;
	private String withdrawAccountNo = null;
	private Integer withdrawAmount = -1;
	private String dateOfWithdraw = null;
	
	
	
	
	
	public WithdrawLog ( String customerNo, String withdrawAccountNo, Integer withdrawAmount, String dateOfWithdraw ) 
	{
		this.logType = LogType.WITHDRAW_LOG;
		this.customerNo = customerNo;
		this.withdrawAccountNo = withdrawAccountNo;
		this.withdrawAmount = withdrawAmount;
		this.dateOfWithdraw = dateOfWithdraw;
	}
	
	
	
	
	
	public String getCustomerNo()
	{
		return this.customerNo;
	}
	
	
	
	public String getWithdrawAccountNo()
	{
		return this.withdrawAccountNo;
	}
	
	
	
	public Integer getWithdrawAmount()
	{
		return this.withdrawAmount;
	}
	
	
	
	public String getDateOfWithdraw()
	{
		return this.dateOfWithdraw;
	}
	
	
}
