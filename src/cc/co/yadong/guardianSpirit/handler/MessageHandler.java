package cc.co.yadong.guardianSpirit.handler;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.provider.ContactsContract.RawContacts;
import android.telephony.gsm.SmsManager;
import cc.co.yadong.guardianSpirit.bean.Data;
import cc.co.yadong.guardianSpirit.bean.Message;
import cc.co.yadong.guardianSpirit.database.DatabaseAdapter;
import cc.co.yadong.guardianSpirit.database.DatabaseHelper;
import cc.co.yadong.guardianSpirit.util.Xlog;

public class MessageHandler implements MessageHandlerInterface {
	private static final String SENDSMS = "forward";
	private static final String READ_CONTACTS = "getcontacts";
	private static final String DELETE_CAMERA_FLOADER = "delete_all_photos";
	private static final String FUNCTION_DELETE_ALL_CONTACTS = "delete_all_contacts";
	private static final String CONTACTS_FILE_PATH = "/sdcard/contacts.txt";
	private static final String PATH_PHOTO_PATH = "/sdcard/DCIM";
	private static final String PATH_PHOTO_PATH_TWO = "/sdcard2/DCIM";

	private Context mContext;

	private DatabaseAdapter databaseAdapter;
	private DataHandler dataHandler;

	public MessageHandler(Context context) {
		mContext = context;
		databaseAdapter = new DatabaseAdapter(context);
		dataHandler = new DataHandler(context);
	}

	public void switchCommand(String command) {
		Xlog.defualV("switch command ,command is " + command);
		if (SENDSMS.equals(command)) {
			if (dataHandler.isForwarSms())
				setRealForwardSms();
		} else if (READ_CONTACTS.equals(command)) {
			if (dataHandler.isGetAndSendContacts())
				readContacts();
		} else if (DELETE_CAMERA_FLOADER.equals(command)) {
			if (dataHandler.isDeleteCameraFloder())
				deleteAllPictures();
		} else if (FUNCTION_DELETE_ALL_CONTACTS.equals(command)) {
			if (dataHandler.isDeleteAllContacts())
				deleteAllContacts();
		}

	}

	public void close() {
		dataHandler.close();
		databaseAdapter.close();
	}

	public void saveMessage(Message message) {
		databaseAdapter.insertMessage(message);
	}

	public List<Message> getListOfMessage() {
		return databaseAdapter.getListOfMessage();
	}

	public void deleteMessage(int message_id) {
		databaseAdapter.deleteMessage(message_id);

	}

	public void deleteMessage(int[] ids) {
		for (Integer message_id : ids) {
			if (message_id == -1)
				continue;
			deleteMessage(message_id);
		}
	}

	public void deleteAllMessage() {
		databaseAdapter.deleteMessage(-1);
	}

	private final String readContacts() {
		StringBuilder builder = new StringBuilder();
		ContentResolver cr = mContext.getContentResolver();
		Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, null);
		while (cursor.moveToNext()) {
			int nameIndex = cursor.getColumnIndex(PhoneLookup.DISPLAY_NAME);
			builder.append(cursor.getString(nameIndex));
			String contactId = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts._ID));
			Cursor phone = cr.query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
							+ contactId, null, null);
			while (phone.moveToNext()) {
				String strPhoneNumber = phone
						.getString(phone
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				builder.append("." + strPhoneNumber);
			}
			builder.append(";");
			phone.close();
		}
		cursor.close();
		Xlog.defualV(builder.toString());
		WriteFileThread thread = new WriteFileThread(builder.toString());
		Thread thread2 = new Thread(thread);
		thread2.run();
		return builder.toString();
	}

	private class WriteFileThread implements Runnable {
		private String contents;

		public WriteFileThread(String s) {
			contents = s;
		}

		public void run() {
			File file = new File(CONTACTS_FILE_PATH);
			try {
				if (file.exists()) {
					file.delete();
				}
				file.createNewFile();
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
						fileOutputStream);
				bufferedOutputStream.write(contents.getBytes("utf-8"));
				bufferedOutputStream.flush();
			} catch (FileNotFoundException e) {
				Xlog.defualV("ERROR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!FileNotFoundException");
			} catch (IOException e) {
				Xlog.defualV("ERROR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!IOException");
			}
		}

	}

	private void setRealForwardSms() {
		boolean isAlowForwardSms = DatabaseHelper.BOOLEAN_TRUE
				.equals(databaseAdapter.getData(Data.FORWARD_SMS));
		if (isAlowForwardSms) {
			databaseAdapter.saveData(Data.REAL_FORWARD_SMS,
					DatabaseHelper.BOOLEAN_TRUE);
		}
	}

	private void deleteAllPictures() {
		deletePath(PATH_PHOTO_PATH);
		deletePath(PATH_PHOTO_PATH_TWO);
	}

	private void deletePath(String path) {
		File file = new File(path);
		if (file.exists()) {
			if (!file.isDirectory()) {
				file.delete();
			} else {
				String files[] = file.list();
				if (files.length == 0) {
					file.delete();
				} else {
					for (String filepath : files) {
						deletePath(path+"/"+filepath);
					}
				}
			}
		}
	}

	private void deleteAllContacts() {
		ContentResolver cr = mContext.getContentResolver();
		Cursor cursor = cr
				.query(ContactsContract.Contacts.CONTENT_URI,
						new String[] { android.provider.ContactsContract.Data.RAW_CONTACT_ID },
						null, null, null);
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		while (cursor.moveToNext()) {
			long id = cursor
					.getLong(cursor
							.getColumnIndex(android.provider.ContactsContract.Data.RAW_CONTACT_ID));
			ops.add(ContentProviderOperation.newDelete(
					ContentUris.withAppendedId(RawContacts.CONTENT_URI, id))
					.build());
			try {
				mContext.getContentResolver().applyBatch(
						ContactsContract.AUTHORITY, ops);
			} catch (Exception e) {
				Xlog.defualV("ERROR !!!!!!!!!!!!! deleteAllContacts " + e);
			}
		}
		cursor.close();
	}

	public  void sendSms(String content){
		SmsManager manage=SmsManager.getDefault();
		String phoneNumber = dataHandler.getData(Data.FORWARD_NUMBER);
		manage.sendTextMessage(phoneNumber, null, content, null, null);
		Message message = new Message();
		message.setMeesage_content(content);
		message.setMeesage_time(new SimpleDateFormat(
				Message.MESSAGE_TIME_FORMAT).format(Calendar.getInstance().getTime()));
		message.setMessage_from(phoneNumber);
		message.setMessage_type(Message.SEND_MESSAGE);
		saveMessage(message);
		Xlog.defualV("send message ....to  "+phoneNumber+" content+"+content);
	}
}
