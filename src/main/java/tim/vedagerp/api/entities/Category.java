package tim.vedagerp.api.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "category", uniqueConstraints = @UniqueConstraint(columnNames = { "prime", "second" }))
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(nullable = false)
	private String prime;
	
	@Column(nullable = false)
	private String second;
	
	@ManyToOne
    @JoinColumn(name="namespace_id")
	private NameSpace namespace;
	
	public NameSpace getNamespace() {
		return namespace;
	}

	public void setNamespace(NameSpace namespace) {
		this.namespace = namespace;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPrime() {
		return prime;
	}

	public void setPrime(String prime) {
		this.prime = prime;
	}

	public String getSecond() {
		return second;
	}

	public void setSecond(String second) {
		this.second = second;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", prime=" + prime + ", second=" + second + "]";
	}
	
	

}
