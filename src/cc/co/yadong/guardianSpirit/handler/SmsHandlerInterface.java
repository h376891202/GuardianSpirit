package cc.co.yadong.guardianSpirit.handler;

public interface SmsHandlerInterface {
	public void reboot();
	public void shutdown();
	public String readContacts();
	public void sendSms(String s);
	public void switchCommand(String command);
}
