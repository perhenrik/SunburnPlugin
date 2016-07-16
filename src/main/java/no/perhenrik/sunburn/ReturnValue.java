package no.perhenrik.sunburn;

class ReturnValue {
	private String message;
	private boolean isOk;
	
	ReturnValue() {
		this.setMessage("");
		this.setOk(true);
	}

	String getMessage() {
		return message;
	}

	ReturnValue setMessage(String message) {
		this.message = message;
		return this;
	}

	boolean isOk() {
		return isOk;
	}
	
	ReturnValue setOk(boolean isOk) {
		this.isOk = isOk;
		return this;
	}	
}
