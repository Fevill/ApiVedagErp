package tim.vedagerp.api.model;
import java.util.List;

import tim.vedagerp.api.entities.Account;

public class BudgetPart {
	
	private float solde;
    private float soldeprev;
   
    
    private List<IBudgetRow> row;
    
    public float getSolde() {
        return solde;
    }

    public List<IBudgetRow> getRow() {
        return row;
    }
    public void setRow(List<IBudgetRow> row) {
        this.row = row;
        this.solde = this.calculSolde();
        this.soldeprev = this.calculSoldePrev();
    }
    public float getSoldeprev() {
        return soldeprev;
    }
    public void setSoldeprev(float soldeprev) {
        this.soldeprev = soldeprev;
    }
    public void setSolde(float solde) {
        this.solde = solde;
    }


    private float calculSolde() {
        float sum = 0 ;
        for (IBudgetRow iBudgetRow : row) {
            sum = sum + iBudgetRow.getSolde();
        }
        return sum;
    }

    private float calculSoldePrev() {
        float sum = 0 ;
        for (IBudgetRow iBudgetRow : row) {
            sum = sum + iBudgetRow.getSoldeprev();
        }
        return sum;
    }
}
