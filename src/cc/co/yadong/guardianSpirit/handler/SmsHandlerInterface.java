package cc.co.yadong.guardianSpirit.handler;

import cc.co.yadong.guardianSpirit.bean.Message;

public interface SmsHandlerInterface {
	public void reboot();
	public void shutdown();
	public String readContacts();
	public void sendSms(String s);
	public void switchCommand(String command);
	public void saveMessage(Message message);
}
