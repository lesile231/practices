package kr.lesile.processes.transaction.log.profiler.restful;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import kr.lesile.processes.transaction.log.common.customer.Account;
import kr.lesile.processes.transaction.log.common.customer.Customer;
import kr.lesile.processes.transaction.log.profiler.manager.ProfilerManager;
import spark.Spark;
import spark.servlet.SparkApplication;

public class SearchService implements SparkApplication
{
	@Override
	public void init() 
	{
		Spark.get("/customer/:customerNo", (req, res)->
		{
			Customer customer = ProfilerManager.customerMap.get( req.params(":customerNo") );
			
			if( customer == null )
	    		{
	    			return "<html><body><h1>Customer not found</h1></body></html>";
	    		}

    			HashMap<String, Account> accountMap = customer.getAccountMap();
			String accountInfo = "";
    			
    			if( accountMap != null )
    			{
    				Set<String> keySet = accountMap.keySet();
    				Iterator<String> itr = keySet.iterator();
    				
    				while( itr.hasNext() )
    				{
    					String accountNo = itr.next();
    					Account account = accountMap.get( accountNo );
    					
    					accountInfo += "[ Account Number : "+ accountNo + " / Current Balance : " + account.getBalance()
    							       + " / date of account openning : " + account.getDateOfAccountOpen() + " ] ";
    				}
    			}
			
			return "This Customer name is " + customer.getCustomerName() + "<br/>"
				+  " This Customer account info : " + accountInfo + "<br/>"
				+  " This Customer date of join : " + customer.getDateOfJoin() + "<br/>";
			
		});
        
		
		
		Spark.get("/customer/:customerNo/account/:acountNo", (req,res)->
        {
        		Customer customer = ProfilerManager.customerMap.get( req.params(":customerNo") );
        		
        		if( customer == null )
        		{
        			return "<html><body><h1>Customer not found</h1></body></html>";
        		}
        		
        		HashMap<String, Account> accountMap = customer.getAccountMap();
        		Account account = accountMap.get( req.params(":acountNo") );
        		
        		if( account == null )
        		{
        			return "<html><body><h1>Customer's account not found</h1></body></html>";
        		}
        		
            return "Customer's account["+ req.params(":acountNo") +"] balance is "+ account.getBalance() + " / " 
                   + " date of account openning : " + account.getDateOfAccountOpen();
        });
	}
	
	
}
