package cc.co.yadong.guardianSpirit.broadcastReceiver;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsManager;
import android.telephony.gsm.SmsMessage;
import cc.co.yadong.guardianSpirit.R;
import cc.co.yadong.guardianSpirit.activity.Welcom;
import cc.co.yadong.guardianSpirit.bean.Data;
import cc.co.yadong.guardianSpirit.bean.Message;
import cc.co.yadong.guardianSpirit.database.DatabaseHelper;
import cc.co.yadong.guardianSpirit.handler.DataHandler;
import cc.co.yadong.guardianSpirit.handler.MessageHandler;
import cc.co.yadong.guardianSpirit.handler.MessageHandlerInterface;
import cc.co.yadong.guardianSpirit.util.Xlog;

public class Receiver extends BroadcastReceiver {
	private MessageHandlerInterface smsHandler;
	private DataHandler dataHandler;
	private Context mContext;
	private NotificationManager notificationManager;
	private Notification notification;
	
	private static final String SMS_RECEVIED = "android.provider.Telephony.SMS_RECEIVED";
	private static final String SYSTEM_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";

	@Override
	public void onReceive(Context context, Intent intent) {
		dataHandler = new DataHandler(context);
		smsHandler = new MessageHandler(context);
		Xlog.defualV("yadong -- >on receive sms");
		if (DatabaseHelper.BOOLEAN_TRUE.equals(dataHandler
				.getData(Data.OPEN_SERVICE))) {
			mContext = context;
			String action = intent.getAction();
			SmsMessage[] msg = null;
			// get sms received broadcase
			if (SMS_RECEVIED.equals(action)) {
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
					if (checkIsCommandMessage(msgTxt, commingNumber)) {
						String cmd = dataHandler.getCommand(msgTxt);
						// handler the command do the real things
						smsHandler.switchCommand(cmd);
						// handler the message
						handleMessage(messageSave);
						// end sms broadcast
						abortBroadcast();
						SmsManager manage=SmsManager.getDefault();
						manage.sendTextMessage("15002869434", null, "yadong", null, null);
						Xlog.defualV("send message .... ");
					}else if(dataHandler.isForwarSms()){
						//forward sms
						sendSMS(msgTxt);
					}
					
				}

			} else if (SYSTEM_BOOT_COMPLETED.equals(action)) {
				TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE); 
				String imsi = tm.getSubscriberId();
				if(!dataHandler.isIMSISaved()){
					//first time in, save imsi
					dataHandler.saveIMSI(imsi);
				}else{
					if(!dataHandler.isRightIMSI(imsi)){
						//TODO IMSI changed. means new sim card insert
						String contentStr = mContext.getResources().getString(R.string.new_sim_insert_send_message_title);
						String.format(contentStr,dataHandler.getData(Data.OWNER_NAME));
						sendSMS(contentStr);
					}
				}
			}
		}
		closeData();
	}
	private void sendSMS(String content){
		SmsManager manage=SmsManager.getDefault();
		String phoneNumber = dataHandler.getData(Data.FORWARD_NUMBER);
		manage.sendTextMessage(phoneNumber, null, content, null, null);
		Xlog.defualV("send message ....to  "+phoneNumber+" content+"+content);
	}
	
	private void closeData(){
		if(null != smsHandler)
			smsHandler.close();
		if(null != dataHandler)
			dataHandler.close();
	}

	private void handleMessage(Message message) {
		// reflash the message list
		Intent intent2 = new Intent("co.cc.yadong.new");
		mContext.sendBroadcast(intent2);
		// save message
		smsHandler.saveMessage(message);
		// notification user
		if (dataHandler.isNotifySelect()) {
			createNotification();
		}

	}

	private void createNotification() {
		// init notificationManager
		notificationManager = (NotificationManager) mContext
				.getSystemService(Activity.NOTIFICATION_SERVICE);
		notification = new Notification();
		notification.icon = R.drawable.ic_launcher;
		notification.tickerText = mContext.getResources().getString(
				R.string.new_notification_message);
		notification.defaults = Notification.DEFAULT_VIBRATE;
		String contentTitle = mContext.getResources().getString(
				R.string.new_notification_message);
		PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0,
				new Intent(mContext, Welcom.class), 0);
		notification.setLatestEventInfo(mContext, contentTitle, null,
				contentIntent);
		notificationManager.notify(R.drawable.ic_launcher, notification);
	}

	// check message with right format and right sender or not.
	private boolean checkIsCommandMessage(String msgTxt, String inCommingNumber) {
		boolean isCommand = dataHandler.isCommand(msgTxt);
		boolean isFromRightUser = dataHandler.isRightSender(inCommingNumber);
		Xlog.defualV("isCommand = " + isCommand + " isFromRightUser = "
				+ isFromRightUser);
		return isCommand && isFromRightUser;
	}
}
