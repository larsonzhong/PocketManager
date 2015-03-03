package larson.pm.bean;

public class UserInfo {
	private int id;
	private String username;
	private String password;
	private String gender;
	private String nickname;
	
	

	public UserInfo() {
		super();
	}

	public UserInfo(String username, String password, String gender,
			String nickname) {
		super();
		this.username = username;
		this.password = password;
		this.gender = gender;
		this.nickname = nickname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
