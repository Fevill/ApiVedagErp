package tim.vedagerp.api.model;
import java.util.List;

import tim.vedagerp.api.entities.Account;

public class Budget {
	
    private float solde;
    private BudgetPart spend;
    private BudgetPart revenus;

    public BudgetPart getRevenus() {
        return revenus;
    }
    public float getSolde() {
        return solde;
    }
    public void setSolde(float solde) {
        this.solde = solde;
    }
    public BudgetPart getSpend() {
        return spend;
    }
    public void setSpend(BudgetPart spend) {
        this.spend = spend;
    }
    public void setRevenus(BudgetPart revenus) {
        this.revenus = revenus;
    }

    public void calculSolde() {
        this.solde = this.getRevenus().getSolde() - this.spend.getSolde();
    }
    
    
}
