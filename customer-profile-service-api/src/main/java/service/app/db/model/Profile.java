package service.app.db.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@Entity
@Table(name = "profile")
public class Profile implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="name")
	private String name;
	@Column(name="email")
	private String email;
	@Column(name="birthday")
	private Date birthday;
	@Column(name="primary_phone")
	private String primaryPhone;
	@Column(name="sex")
	private String sex;
}
