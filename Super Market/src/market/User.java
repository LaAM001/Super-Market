package market;

public class User {
	private int ID;
	private String name, email, password, permission, phoneNum;

	//בנאי שיוצר משתמש לפי הנתונים שקיבל
	public User(int ID, String name, String email, String phoneNum, String password, String permission) {
		this.setID(ID);
		this.setName(name);
		this.setEmail(email);
		this.setPhoneNum(phoneNum);
		this.setPassword(password);
		this.setPermission(permission);
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		this.ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	@Override
	public String toString() {
		return "User#" + ID + "#" + name + "#" + email+ "#" + phoneNum + "#" + password + "#" + permission;
	}
	
	
}
