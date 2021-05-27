package tim.vedagerp.api.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "fiscalyear")
public class FiscalYear {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(nullable = false)
	private String year;
	
	@ManyToOne
    @JoinColumn(name="namespace_id")
	private NameSpace namespace;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public NameSpace getNamespace() {
		return namespace;
	}

	public void setNamespace(NameSpace namespace) {
		this.namespace = namespace;
	}
	
	
	
}
