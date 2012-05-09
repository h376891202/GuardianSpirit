package cc.co.yadong.guardianSpirit.handler;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;
import android.util.Log;
import cc.co.yadong.guardianSpirit.R;
import cc.co.yadong.guardianSpirit.bean.Data;
import cc.co.yadong.guardianSpirit.bean.Message;
import cc.co.yadong.guardianSpirit.database.DatabaseAdapter;
import cc.co.yadong.guardianSpirit.database.DatabaseHelper;

public class SmsHandler implements SmsHandlerInterface{
	private static final String TAG = "SmsHandler";
	private Context mContext;
	private DatabaseAdapter databaseAdapter;
	private ArrayList<String> commandType = new ArrayList<String>();
	
	public SmsHandler(Context context){
		mContext = context;
		databaseAdapter = new DatabaseAdapter(context);
		Collections.addAll(commandType, mContext.getResources().getStringArray(R.array.command_type));
	}
	/**
	 * reboot the system
	 */
	public void reboot() {
		Log.v(TAG, "reboot system");
//		((PowerManager)mContext.getSystemService("power")).reboot(null);
	}

	public void shutdown() {
		Log.v(TAG, "shutdown system");
		
	}

	public String readContacts() {
		Log.v(TAG, "read contacts");
		return null;
	}

	public void sendSms(String message) {
		Log.v(TAG, "read send message");
		
	}


	public void switchCommand(String command) {
		Log.v(TAG, "command is " + command + " commandType = "+commandType);
		switch (commandType.indexOf(command)) {
		case 0:
			shutdown();
			break;
		case 1:
			reboot();
			break;
		case 2:
			sendSms(null);
			break;
		case 3:
			sendSms(readContacts());
		default:
			Log.v(TAG, "hava no this command");
			break;
		}
	}
	
	public void close(){
		databaseAdapter.close();
	}
	
	public void saveMessage(Message message) {
		if(DatabaseHelper.BOOLEAN_TRUE.equals(databaseAdapter.getData(Data.SAVE_MESSAGE))){
			databaseAdapter.insertMessage(message);
		}
	}
	
	public String getforwardNumber(){
		if(DatabaseHelper.BOOLEAN_TRUE.equals(databaseAdapter.getData(Data.FORWARD_SMS)))
			return databaseAdapter.getData(Data.FORWARD_NUMBER);
		return null;
	}
	public boolean isCommand(String mSmsContext){
		if(null == mSmsContext)
			return false;
		if(mSmsContext.indexOf(getCommandString()) != -1){
			if(mSmsContext.indexOf(":") == -1 && mSmsContext.indexOf(":")==mSmsContext.length()){
				Log.v(TAG, "Commant format error");
				return false;
			}
			return true;
		}else{
			return false;
		}
	}
	private String getCommandString(){
		String commandString = databaseAdapter.getData(Data.COMMAND_STRING);
		Log.v(TAG,commandString);
		return commandString;
	}
	public String getCommand(String mSmsContext){
		String command =  mSmsContext.substring(mSmsContext.lastIndexOf(":")+1).trim();
		//TODO
		return databaseAdapter.getData(command);
	}
	
	public boolean isRightSender(String senderNumber) {
		String realSenderNumber = databaseAdapter.getData(Data.FORWARD_NUMBER);
		if(realSenderNumber == null)
			return false;
		return realSenderNumber.equals(senderNumber);
	}


	
	
}
