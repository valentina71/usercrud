package it.zanichelli.garotta.usercrud.reponse;

public enum MessageResponse {

	OK("OK", "operazione avvenuta con successo"), KO_GET_LISTA_ACCOUNT("KO",
			"errori nel recupero della lista degli account"), KO_GET_LISTA_USERNAME("KO",
					"errori nel recupero della lista degli username"),KO_GET_ACCOUNT("KO",
					"errori nel recupero dell'account specificato"), KO_DELETE("KO",
							"errore nella cancellazione dell'account specificato"), KO_INSERT("KO",
									"errore nell'inserimento dell'account specificato"), KO_UPDATE("KO",
											"errore nell'update dell'account specificato");

	private String code;
	private String message;

	private MessageResponse(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return this.code;
	}

	public String getMessage() {
		return this.message;
	}

}
