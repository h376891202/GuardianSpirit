package cc.co.yadong.guardianSpirit.handler;

import java.util.List;

import android.content.Context;
import cc.co.yadong.guardianSpirit.bean.Message;
import cc.co.yadong.guardianSpirit.database.DatabaseAdapter;

public class MessageHandler {
	private DatabaseAdapter adapter;
	private Context mContext;
	
	public MessageHandler(Context context) {
		mContext = context;
		adapter = new DatabaseAdapter(mContext);
	}
		
	public List<Message> getListOfMessage(){
		return adapter.getListOfMessage();
	}
	
	public void insertMessage(Message message){
		adapter.insertMessage(message);
	}
	
	public void deleteMessage(int message_id){
		adapter.deleteMessage(message_id);
	}

	public void deleteMessage(int []ids){
		for(Integer message_id : ids){
			deleteMessage(message_id);
		}
	}
	public void deleteAllMessage() {
		adapter.deleteMessage(-1);
	}
	
	public void close(){
		adapter.close();
	}
	 
	
	
	
}
