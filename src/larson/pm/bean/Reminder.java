package larson.pm.bean;

public class Reminder {
	private int id;
	private String tagName;
	private String time;
	private int isClock;
	private int isNotify;
	private int ringType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getIsClock() {
		return isClock;
	}

	public void setIsClock(int isClock) {
		this.isClock = isClock;
	}

	public int getIsNotify() {
		return isNotify;
	}

	public void setIsNotify(int isNotify) {
		this.isNotify = isNotify;
	}

	public int getRingType() {
		return ringType;
	}

	public void setRingType(int ringType) {
		this.ringType = ringType;
	}

}