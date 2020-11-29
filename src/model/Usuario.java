package model;

public class Usuario extends BaseClass { 
	private String login;
  	private char[] senha;
  	private byte tipo;

  	public Usuario (){ }

	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public char[] getSenha() {
		return senha;
	}
	
	public void setSenha(char[] senha) {
		this.senha = senha;
	}
	
	public byte getTipo() {
		return tipo;
	}
	
	public void setTipo(byte tipo) {
		this.tipo = tipo;
	}
}
