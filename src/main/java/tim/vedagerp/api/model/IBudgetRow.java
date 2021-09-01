package tim.vedagerp.api.model;

import tim.vedagerp.api.entities.Account;

public interface IBudgetRow {
	
	float getSolde();
    float getSoldeprev();
    int getMonth();
    Long getId();
    String getLabel();
    public default float getSoldediff(){
        return getSolde()-getSoldeprev();
   };
   
}
