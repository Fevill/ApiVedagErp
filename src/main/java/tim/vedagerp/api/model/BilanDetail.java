package tim.vedagerp.api.model;

import tim.vedagerp.api.entities.Account;

public class BilanDetail {
	
	private Account account;
	private float solde;
    
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
    public float getSolde() {
        return solde;
    }
    public void setSolde(float solde) {
        this.solde = solde;
    }

}
