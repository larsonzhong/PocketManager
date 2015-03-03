package larson.pm.bean;

/**
 * 
 * @author Larson
 * 
 */
public class Fond {
	private int id;
	private String type;
	private String event;
	private String count;
	private String time;
	private String describe;
	private int ifIn;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public int getIfIn() {
		return ifIn;
	}

	public void setIfIn(int ifIn) {
		this.ifIn = ifIn;
	}

	public Fond(String type, String event, String count, String time,
			String describe, int ifIn) {
		super();
		this.type = type;
		this.event = event;
		this.count = count;
		this.time = time;
		this.describe = describe;
		this.ifIn = ifIn;
	}

	public Fond() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Fond(int id, String type, String event, String count, String time,
			String describe, int ifIn) {
		super();
		this.id = id;
		this.type = type;
		this.event = event;
		this.count = count;
		this.time = time;
		this.describe = describe;
		this.ifIn = ifIn;
	}

}
