package cc.co.yadong.guardianSpirit.handler;

import java.util.List;

import android.content.Context;
import cc.co.yadong.guardianSpirit.bean.Message;
import cc.co.yadong.guardianSpirit.database.DatabaseAdapter;

public class MessageHandler implements MessageHandlerInterface{
	private DatabaseAdapter databaseAdapter;
	
	public MessageHandler(Context context){
		databaseAdapter = new DatabaseAdapter(context);
	}

	public void switchCommand(String command) {
		//TODO SWITCH COMMAND use OrderHander
		
	}
	
	public void close(){
		databaseAdapter.close();
	}
	
	public void saveMessage(Message message) {
		databaseAdapter.insertMessage(message);
	}

	public List<Message> getListOfMessage() {
		return databaseAdapter.getListOfMessage();
	}

	public void deleteMessage(int message_id) {
		databaseAdapter.deleteMessage(message_id);
		
	}

	public void deleteMessage(int[] ids) {
		for(Integer message_id : ids){
			if(message_id == -1)
				continue;
			deleteMessage(message_id);
		}
	}

	public void deleteAllMessage() {
		databaseAdapter.deleteMessage(-1);
	}
	



	
	
}
