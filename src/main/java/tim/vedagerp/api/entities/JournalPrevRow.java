package tim.vedagerp.api.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "journalprev")
public class JournalPrevRow {

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
	
	@ManyToOne
    @JoinColumn(name="namespace_id")
	private NameSpace namespace;
	
	private float amount;

	public JournalPrevRow(){}

	public JournalPrevRow(Date dateOperation, String label, Account debit,
	 Account credit, float solde, NameSpace ns) {
		
		this.dateOperation = dateOperation;
		this.amount = solde;
		this.credit = credit;
		this.debit = debit;
		this.namespace = ns;
		this.label = label;
	}
	
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
	
	public NameSpace getNamespace() {
		return namespace;
	}
	public void setNamespace(NameSpace namespace) {
		this.namespace = namespace;
	}
	@Override
	public String toString() {
		return "JournalRow [id=" + id + ", dateOperation=" + dateOperation + ", label=" + label + ", credit=" + credit
				+ ", debit=" + debit + ", amount=" + amount + "]";
	}
	
	
	
}
