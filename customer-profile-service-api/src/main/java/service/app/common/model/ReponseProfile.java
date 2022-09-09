package service.app.common.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import service.app.db.model.Profile;

@Data
@JsonInclude(Include.NON_NULL)
public class ReponseProfile implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Profile profile;
	private String status;
	private String msg;
	private String startDtm;
	private String endDtm;
	private Long resTime;
}
