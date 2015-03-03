package larson.pm.bean;

public class Contact {
	private String contactId;
	private String contactName;
	private String PhoneNumber;

	public Contact(String contactId, String contact, String phoneNumber) {
		super();
		this.contactId = contactId;
		this.contactName = contact;
		PhoneNumber = phoneNumber;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

}
