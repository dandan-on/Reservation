package kr.or.connect.reservation.service.security;

public class UserEntity {
	private String userLoginId;
	private String password;
	
	public UserEntity(String userLoginId, String password) {
		this.userLoginId = userLoginId;
		this.password = password;
	}

	public String getUserLoginId() {
		return userLoginId;
	}

	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
		
	
}
