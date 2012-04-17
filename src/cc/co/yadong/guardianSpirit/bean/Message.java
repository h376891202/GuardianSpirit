package cc.co.yadong.guardianSpirit.bean;

public class Message {
	public static final String MESSAGE_TIME_FORMAT = "yyyy-MM-dd HH:mm:SS";
	private int message_id;
	private String message_from;
	private String meesage_time;
	private String meesage_content;
	private int message_type;

	public int getMessage_id() {
		return message_id;
	}

	public void setMessage_id(int message_id) {
		this.message_id = message_id;
	}

	public String getMessage_from() {
		return message_from;
	}

	public void setMessage_from(String message_from) {
		this.message_from = message_from;
	}

	public String getMeesage_time() {
		return meesage_time;
	}

	public void setMeesage_time(String meesage_time) {
		this.meesage_time = meesage_time;
	}

	public String getMeesage_content() {
		return meesage_content;
	}

	public void setMeesage_content(String meesage_content) {
		this.meesage_content = meesage_content;
	}

	public int getMessage_type() {
		return message_type;
	}

	public void setMessage_type(int message_type) {
		this.message_type = message_type;
	}
}