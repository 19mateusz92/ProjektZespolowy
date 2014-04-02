package UserRegistration;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.ArrayUtils;

@XmlRootElement
public class UserDTO {
	
	private String login;
	private String password;
	private UserGameDTO userGame;
/*	private String email;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}*/
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserGameDTO getUserGame() {
		return userGame;
	}
	public void setUserGame(UserGameDTO userGame) {
		this.userGame = userGame;
	}
	
	@Override
	public String toString() {
		return "UserDTO [login=" + login + ", password=" + password
				+ ", userGame=" + userGame + "]";
	}
	
	public String[] toArray() {
		String [] arrayOfUser = new String [] {login};
		return (String[]) ArrayUtils.addAll(arrayOfUser, userGame.toArray());
	}
	
	public String[] toArrayOnlyUser() {
		return new String [] {login};
	}
}
