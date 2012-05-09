package cc.co.yadong.guardianSpirit.handler;

import cc.co.yadong.guardianSpirit.bean.Message;

public interface SmsHandlerInterface {
	public void reboot();
	public void shutdown();
	public String readContacts();
	public void sendSms(String s);
	public void switchCommand(String command);
	public void saveMessage(Message message);
	public boolean isCommand(String commad);
	public String getCommand(String mSmsContext);
	public boolean isRightSender(String senderNumber);
	public void close();
	
}
