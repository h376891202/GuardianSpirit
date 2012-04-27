package cc.co.yadong.guardianSpirit.broadcastReceiver;

import cc.co.yadong.guardianSpirit.bean.Data;
import cc.co.yadong.guardianSpirit.database.DatabaseAdapter;
import android.content.Context;
import android.util.Log;

public class SmsContextResove {
	
	private static final String TAG = "SmsContextResove";
	private String mSmsContext;
	private DatabaseAdapter adapter;
	protected SmsContextResove(Context context,String smsContext){
		this.mSmsContext = smsContext;
		adapter = new DatabaseAdapter(context);
	}
	/**
	 * Judge the context is a command or not
	 * @return
	 */
	protected boolean isCommand(){
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
	/**
	 * search from database what the command String is
	 * @return
	 */
	protected String getCommandString(){
		String commandString = adapter.getData(Data.COMMAND_STRING);
		Log.v(TAG,commandString);
		return commandString;
	}
	/**
	 * get command from context
	 * @return
	 */
	protected String getCommand(){
		String command =  mSmsContext.substring(mSmsContext.lastIndexOf(":")+1).trim();
		//TODO
		return adapter.getData(command);
	}
	
	public void close(){
		adapter.close();
	}
	
	
}
