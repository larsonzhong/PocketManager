package larson.pm.bean;

public class SmsBean {
	private int id;
	private String receiver;
	private String content;
	private String time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public SmsBean(String receiver, String content, String time) {
		super();
		this.receiver = receiver;
		this.content = content;
		this.time = time;
	}

	public SmsBean() {
	}

}
