package tim.vedagerp.api.model;

import java.util.List;

import tim.vedagerp.api.entities.Account;
import tim.vedagerp.api.entities.FiscalYear;
import tim.vedagerp.api.entities.NameSpace;

public interface IResultatMonth {
	
	int getMois();
	float getSolde();
	float getSoldeprev();
	float getAffaire();

}
