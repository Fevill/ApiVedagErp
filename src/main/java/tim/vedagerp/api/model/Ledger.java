package tim.vedagerp.api.model;

import java.util.List;

import tim.vedagerp.api.entities.JournalRow;

public class Ledger {
	
	private List<JournalRow> debit;
	private List<JournalRow> credit;
	private float solde;
	
	public List<JournalRow> getDebit() {
		return debit;
	}
	public float getSolde() {
		return solde;
	}
	public void setSolde(float solde) {
		this.solde = solde;
	}
	public void setDebit(List<JournalRow> debit) {
		this.debit = debit;
	}
	public List<JournalRow> getCredit() {
		return credit;
	}
	public void setCredit(List<JournalRow> credit) {
		this.credit = credit;
	}
	
	

}
