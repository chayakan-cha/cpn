package service.app.common.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class AddressByProfile implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String houseNo;
	private String villageNo;
	private String alley;
	private String lane;
	private String road;
	private String district; 
	private String subDistrict;
	private String province;
	private String postalCode;
}
