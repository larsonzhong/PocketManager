package larson.pm.bean;

public class Autoprofiles {
	private String time;
	private String profiles;
	private int isStart;
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Autoprofiles() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Autoprofiles(int id,String time, String profiles, int isStart) {
		super();
		this.time = time;
		this.profiles = profiles;
		this.isStart = isStart;
		this.id = id;
	}

	public Autoprofiles(String time, String profiles, int isStart) {
		super();
		this.time = time;
		this.profiles = profiles;
		this.isStart = isStart;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getProfiles() {
		return profiles;
	}

	public void setProfiles(String profiles) {
		this.profiles = profiles;
	}

	public int getIsStart() {
		return isStart;
	}

	public void setIsStart(int isStart) {
		this.isStart = isStart;
	}
}
