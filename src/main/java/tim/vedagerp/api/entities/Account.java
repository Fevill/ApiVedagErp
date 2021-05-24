package tim.vedagerp.api.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "accounts")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String label;
	private	String	number;
	
	@ManyToOne()
    @JoinColumn(name="category_id")
	private	Category category;
	
	@OneToMany(mappedBy = "debit")
    private List<JournalRow> journalDebit;
	
	@OneToMany(mappedBy = "credit")
    private List<JournalRow> journalCredit;
	
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
	@Override
	public String toString() {
		return "Account [id=" + id + ", label=" + label + ", number=" + number + ", category=" + category
				+ ", journalDebit=" + journalDebit + ", journalCredit=" + journalCredit + "]";
	}
	
	
}
