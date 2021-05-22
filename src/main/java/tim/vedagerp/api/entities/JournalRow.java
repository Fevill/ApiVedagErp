package tim.vedagerp.api.entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "journal")
public class JournalRow {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private Date dateOperation;
	private	String	label;
	@ManyToOne
    @JoinColumn(name="debit_id")
	private Account debit;
	
	@ManyToOne
    @JoinColumn(name="credit_id")
	private Account credit;
	
	private float amount;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDateOperation() {
		return dateOperation;
	}
	public void setDateOperation(Date dateOperation) {
		this.dateOperation = dateOperation;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Account getCredit() {
		return credit;
	}
	public void setCredit(Account credit) {
		this.credit = credit;
	}
	public Account getDebit() {
		return debit;
	}
	public void setDebit(Account debit) {
		this.debit = debit;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return "JournalRow [id=" + id + ", dateOperation=" + dateOperation + ", label=" + label + ", credit=" + credit
				+ ", debit=" + debit + ", amount=" + amount + "]";
	}
	
	
	
}
