package service.app.common.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class RequestProfile implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String email;
	private String birthday;
	private String primaryPhone;
	private String sex;
}
