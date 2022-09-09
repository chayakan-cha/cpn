package service.app.db.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
@Entity
@Table(name = "address")
public class Address implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	@Column(name="houseNo")
	private String houseNo;
	@Column(name="villageNo")
	private String villageNo;
	@Column(name="alley")
	private String alley;
	@Column(name="lane")
	private String lane;
	@Column(name="road")
	private String road;
	@Column(name="district")
	private String district; 
	@Column(name="sub_district")
	private String subDistrict;
	@Column(name="province")
	private String province;
	@Column(name="postal_code")
	private String postalCode;
	@Column(name="user_name")
	private String userName;
}
