package tim.vedagerp.api.model;

import tim.vedagerp.api.entities.Account;

public class ResultatRow {
	
	private Account account;
	private float solde;
    private String month;
    
    public Account getAccount() {
        return account;
    }
    public String getMonth() {
        return month;
    }
    public void setMonth(String month) {
        this.month = month;
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
