package cc.co.yadong.guardianSpirit.handler;

import java.util.List;

import cc.co.yadong.guardianSpirit.bean.Message;

public interface MessageHandlerInterface {
	public void switchCommand(String command);
	public void saveMessage(Message message);
	public List<Message> getListOfMessage();
	public void deleteMessage(int message_id);
	public void deleteMessage(int []ids);
	public void deleteAllMessage() ;
	public void close();
	public void sendSms(String phoneNumber,String content);
}
