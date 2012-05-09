package cc.co.yadong.guardianSpirit.broadcastReceiver;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import cc.co.yadong.guardianSpirit.bean.Message;
import cc.co.yadong.guardianSpirit.handler.MessageHandler;
import cc.co.yadong.guardianSpirit.handler.SmsHandler;
import cc.co.yadong.guardianSpirit.handler.SmsHandlerInterface;
import cc.co.yadong.guardianSpirit.util.Xlog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;

public class Receiver extends BroadcastReceiver {
	private SmsHandlerInterface smsHandler;
	private MessageHandler handler;

	@Override
	public void onReceive(Context context, Intent intent) {
		Xlog.defualV("yadong -- >on receive sms");
		String action = intent.getAction();
		SmsMessage[] msg = null;
		// get sms received broadcase
		if ("android.provider.Telephony.SMS_RECEIVED".equals(action)) {
			Bundle bundle = intent.getExtras();
			String msgTxt = null;
			String commingNumber = null;
			Message messageSave = new Message();
			if (null != bundle) {
				smsHandler = new SmsHandler(context);
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
				if (checkIsCommandMessage(msgTxt,commingNumber)) {
					handler = new MessageHandler(context);
					String cmd = smsHandler.getCommand(msgTxt);
					smsHandler.switchCommand(cmd);
					smsHandler.saveMessage(messageSave);
					//reflash the message list
					Intent intent2 = new Intent("co.cc.yadong.new");
					context.sendBroadcast(intent2);
					handler.close();
				}
				smsHandler.close();

			}

		} else if ("".equals(action)) {

		}
	}
	
	private boolean checkIsCommandMessage(String msgTxt,String inCommingNumber){
		boolean isCommand = smsHandler.isCommand(msgTxt);
		boolean isFromRightUser = smsHandler.isRightSender(inCommingNumber);
		Xlog.defualV("isCommand = "+isCommand + " isFromRightUser = "+isFromRightUser);
		return isCommand && isFromRightUser;
	}
}
