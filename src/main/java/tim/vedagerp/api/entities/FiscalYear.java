package tim.vedagerp.api.entities;

import java.sql.Date;

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
	private String name;

	@Column(nullable = false)
	private Date startDate;

	@Column(nullable = false)
	private Date endDate;

	@ManyToOne
	@JoinColumn(name = "namespace_id")
	private NameSpace namespace;

	public Long getId() {
		return id;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public NameSpace getNamespace() {
		return namespace;
	}

	public void setNamespace(NameSpace namespace) {
		this.namespace = namespace;
	}

	@Override
	public String toString() {
		return "FiscalYear [endDate=" + endDate + ", id=" + id + ", name=" + name + ", namespace=" + namespace
				+ ", startDate=" + startDate + "]";
	}

}
