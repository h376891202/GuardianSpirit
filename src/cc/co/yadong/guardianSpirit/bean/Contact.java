package cc.co.yadong.guardianSpirit.bean;

public class Contact {
	private int id;
	private String name;
	private String number;
	private int type;
	public static final int BLACK_LIST = 1;
	public static final int WRITE_LIST = 2;
	
	@Override
	public String toString() {
		String typeStr = null;
		if(type == BLACK_LIST)
			typeStr = "black";
		else
			typeStr = "white";
		return "name: "+name+" number: "+number+" type: "+ typeStr;
	}
	
	@Override
	public boolean equals(Object o) {
		return ((Contact)o).getId() == this.id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
}
