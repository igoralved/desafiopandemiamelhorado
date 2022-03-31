package igorgroup.desafiopandemia.DTO;

public class TokenDTO {

	private String token, type;
	
	public TokenDTO(String token, String type) {
		this.token = token;
		this.type = type;
	}
	
	public String getToken() {
		return this.token;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
}

