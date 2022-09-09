package service.app.common.constant;
public class Message {
	
	public enum SUCCESS {
        SUCCESS000("000", "Success");

        private final String code;
        private final String message;

        SUCCESS(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
	
	public enum ERROR {
        ERROR100_REQUEIRED_FIELD("100", "Required field {0}"),
        ERROR101_ERROR_VALID_REQ("101", "Error validate request {0}"),
        ERROR102_ERROR_INVALID_USERNAME("102", "Error invalid name."),
		ERROR103_ERROR_DB_SAVE_EXCEPTION("103", "{0}");
        private final String code;
        private final String message;

        ERROR(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

	}
}
