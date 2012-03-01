package cc.co.yadong.guardianSpirit.broadcastReceiver;

import cc.co.yadong.guardianSpirit.handler.SmsHandler;
import cc.co.yadong.guardianSpirit.handler.SmsHandlerInterface;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;

public class Receiver extends BroadcastReceiver {
	private SmsContextResove mSmsContextResove;
	private SmsHandlerInterface smsHandler;
	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("yadong -- >on receive");
		String action = intent.getAction();
		SmsMessage[] msg = null;
		// get sms received broadcase
		if ("android.provider.Telephony.SMS_RECEIVED".equals(action)) {
			Bundle bundle = intent.getExtras();
			String msgTxt = null;
			String commingNumber = null;
			if (null != bundle) {
				Object[] pdus = (Object[]) intent.getExtras().get("pdus");
				for (Object p : pdus) {
					byte[] pdu = (byte[]) p;
					SmsMessage message = SmsMessage.createFromPdu(pdu);
					if (null != msgTxt) {
						msgTxt += message.getMessageBody();
					}else{
						msgTxt = message.getMessageBody();
					}
					commingNumber = message.getOriginatingAddress();
				}
				mSmsContextResove = new SmsContextResove(context,msgTxt);
				if(mSmsContextResove.isCommand()){
					String cmd = mSmsContextResove.getCommand();
					smsHandler = new SmsHandler(context);
					smsHandler.switchCommand(cmd);
				}
				
			}
			
		} else if ("".equals(action)) {

		}
	}

}
