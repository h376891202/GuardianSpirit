package cc.co.yadong.guardianSpirit.broadcastReceiver;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import cc.co.yadong.guardianSpirit.bean.Message;
import cc.co.yadong.guardianSpirit.handler.MessageHandler;
import cc.co.yadong.guardianSpirit.handler.SmsHandler;
import cc.co.yadong.guardianSpirit.handler.SmsHandlerInterface;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;

public class Receiver extends BroadcastReceiver {
	private SmsContextResove mSmsContextResove;
	private SmsHandlerInterface smsHandler;
	private MessageHandler handler;

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
			Message messageSave = new Message();
			if (null != bundle) {
				Object[] pdus = (Object[]) intent.getExtras().get("pdus");
				for (Object p : pdus) {
					byte[] pdu = (byte[]) p;
					SmsMessage message = SmsMessage.createFromPdu(pdu);
					if (null != msgTxt) {
						msgTxt += message.getMessageBody();
					} else {
						msgTxt = message.getMessageBody();
					}
					commingNumber = message.getOriginatingAddress();
					Calendar calendar = Calendar.getInstance();
					calendar.setTimeInMillis(message.getTimestampMillis());
					messageSave.setMeesage_time(new SimpleDateFormat(
							Message.MESSAGE_TIME_FORMAT).format(calendar
							.getTime()));
					messageSave.setMeesage_content(msgTxt);
					messageSave.setMessage_from(commingNumber);
					messageSave.setMessage_id(1);
				}
				mSmsContextResove = new SmsContextResove(context, msgTxt);
				if (mSmsContextResove.isCommand()) {
					handler = new MessageHandler(context);
					String cmd = mSmsContextResove.getCommand();
					smsHandler = new SmsHandler(context);
					smsHandler.switchCommand(cmd);
					handler.insertMessage(messageSave);
					Intent intent2 = new Intent();
					intent2.setAction("co.cc.yadong.new");
					context.sendBroadcast(intent2);
					handler.close();
					mSmsContextResove.close();
				}

			}

		} else if ("".equals(action)) {

		}
	}
}
