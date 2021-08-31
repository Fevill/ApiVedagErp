package tim.vedagerp.api.model;

import tim.vedagerp.api.entities.Account;

public class BudgetRow {
	
	private Account account;
	private float solde;
    private float soldeprev;
    private float soldediff;
    private String month;
    
    public Account getAccount() {
        return account;
    }
    public float getSoldediff() {
        return soldediff;
    }
    public void setSoldediff(float soldediff) {
        this.soldediff = soldediff;
    }
    public float getSoldeprev() {
        return soldeprev;
    }
    public void setSoldeprev(float soldeprev) {
        this.soldeprev = soldeprev;
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
    public void calculSoldeDiff() {
        this.soldediff=this.solde-this.soldeprev;
    }

}
