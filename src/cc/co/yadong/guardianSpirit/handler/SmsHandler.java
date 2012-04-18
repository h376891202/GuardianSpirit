package cc.co.yadong.guardianSpirit.handler;

import java.util.ArrayList;
import java.util.Collections;

import cc.co.yadong.guardianSpirit.R;
import android.content.Context;
import android.os.PowerManager;
import android.util.Log;

public class SmsHandler implements SmsHandlerInterface{
	private static final String TAG = "SmsHandler";
	private Context mContext;
	private ArrayList<String> commandType = new ArrayList<String>();
	
	public SmsHandler(Context context){
		mContext = context;
		Collections.addAll(commandType, mContext.getResources().getStringArray(R.array.command_type));
	}
	/**
	 * reboot the system
	 */
	public void reboot() {
		Log.v(TAG, "reboot system");
		((PowerManager)mContext.getSystemService("power")).reboot(null);
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
	
	
}
