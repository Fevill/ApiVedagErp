package tim.vedagerp.api.model;

import tim.vedagerp.api.entities.Account;

public interface Ibalance {

	Long getId();  
	
	String getLabel();
	
	String getNumber();

	float getCredit();

	float getDebit();
}
