package tim.vedagerp.api.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
name = "accounts",
uniqueConstraints=
@UniqueConstraint(columnNames={
	"label", "number","namespace_id"
}))
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(unique=true)
	private String label;

	@Column()
	private String labelBilan;
	
	@Column(unique=true)
	private	String	number;
	
	@ManyToOne()
    @JoinColumn(name="category_id")
	private	Category category;
	
	@OneToMany(mappedBy = "debit")
    private List<JournalRow> journalDebit;
	
	@OneToMany(mappedBy = "credit")
    private List<JournalRow> journalCredit;
	
	@ManyToOne
    @JoinColumn(name="namespace_id")
	private NameSpace namespace;
	
	@ManyToOne()
    @JoinColumn(name="account_id")
	private	Account account;
	
	public Account getAccount() {
		return account;
	}
	public String getLabelBilan() {
		return labelBilan;
	}
	public void setLabelBilan(String labelBilan) {
		this.labelBilan = labelBilan;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
		
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	
	public NameSpace getNamespace() {
		return namespace;
	}
	public void setNamespace(NameSpace namespace) {
		this.namespace = namespace;
	}
	@Override
	public String toString() {
		return "Account [category=" + category + ", id=" + id + ", journalCredit=" + journalCredit + ", journalDebit="
				+ journalDebit + ", label=" + label + ", labelBilan=" + labelBilan + ", namespace=" + namespace
				+ ", number=" + number + "]";
	}

	
	
	
}
