package tim.vedagerp.api.model;

import java.util.List;

import tim.vedagerp.api.entities.Account;
import tim.vedagerp.api.entities.FiscalYear;
import tim.vedagerp.api.entities.NameSpace;

public class ResultatNsRow {
	
	private FiscalYear fy;
	private List<IResultatMonth> resultatsSolde;
    
    public List<IResultatMonth> getResultatsSolde() {
        return resultatsSolde;
    }
    public FiscalYear getFy() {
        return fy;
    }
    public void setFy(FiscalYear fy) {
        this.fy = fy;
    }
    public void setResultatsSolde(List<IResultatMonth> resultatsSolde) {
        this.resultatsSolde = resultatsSolde;
    }

    
}
