package hr.webapp.models;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@NamedQueries({
	@NamedQuery(name="Delivery.getAll",query="select a from Delivery a")
})
@Entity
@Table(name = "delivery")
//@Cacheable(true)
public class Delivery {
	private Long id;
	private String adress;
	private Date createdAt;
	private String status;

	@Id
    @GeneratedValue
	public Long getId() {
		return id;
	}

	@Column(length = 100, nullable = false)
	public String getAdress() {
		return adress;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getCreatedAt() {
		return createdAt;
	}

	@Column(length = 50, nullable = false)
	public String getStatus() {
		return status;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Delivery other = (Delivery) obj;
		return Objects.equals(id, other.id);
	}

}
