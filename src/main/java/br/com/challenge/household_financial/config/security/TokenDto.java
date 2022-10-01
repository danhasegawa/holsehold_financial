package br.com.challenge.household_financial.config.security;

public class TokenDto {
	
	private String token;
	private String type;
	
	public TokenDto(String token, String type) {
		this.token = token;
		this.type = type;
	}

	public String getToken() {
		return token;
	}

	public String getType() {
		return type;
	}
	
	
	

}
