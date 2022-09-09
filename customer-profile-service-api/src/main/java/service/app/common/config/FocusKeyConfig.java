package service.app.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import lombok.Data;

@Data
@Configuration
public class FocusKeyConfig {
	
	@Value("${focus-key.db.error}")
	private String databaseError;

	@Value("${focus-key.exception.error}")
	private String exceptionError;
}
