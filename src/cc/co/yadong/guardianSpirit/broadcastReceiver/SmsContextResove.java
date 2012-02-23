package cc.co.yadong.guardianSpirit.broadcastReceiver;

import android.util.Log;

public class SmsContextResove {
	
	private static final String TAG = "SmsContextResove";
	private String mSmsContext;
	protected SmsContextResove(String smsContext){
		this.mSmsContext = smsContext;
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
		//TODO : this need search from database
		return "command:";
	}
	/**
	 * get command from context
	 * @return
	 */
	protected String getCommand(){
		return mSmsContext.substring(mSmsContext.lastIndexOf(":")+1).trim();
	}
	
	
}
