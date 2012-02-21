package cc.co.yadong.guardianSpirit.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;

public class Receiver extends BroadcastReceiver {

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
				System.out.println("yadong -- >msgTxt = "+msgTxt);
				System.out.println("yadong -- >commingNumber = "+commingNumber);
			}
			if("42729602".equals(commingNumber)){
				abortBroadcast();
			}
		} else if ("".equals(action)) {

		}
	}

}
