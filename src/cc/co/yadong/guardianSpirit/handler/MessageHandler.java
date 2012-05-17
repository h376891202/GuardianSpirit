package cc.co.yadong.guardianSpirit.handler;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import cc.co.yadong.guardianSpirit.bean.Message;
import cc.co.yadong.guardianSpirit.database.DatabaseAdapter;
import cc.co.yadong.guardianSpirit.util.Xlog;

public class MessageHandler implements MessageHandlerInterface {
	private static final String REBOOT = "reboot";
	private static final String SENDSMS = "forward";
	private static final String READ_CONTACTS = "getcontacts";
	private static final String SHUTDOWN = "shutdown";
	private Context mContext;

	private DatabaseAdapter databaseAdapter;

	public MessageHandler(Context context) {
		mContext = context;
		databaseAdapter = new DatabaseAdapter(context);
	}

	public void switchCommand(String command) {
		if (REBOOT.equals(command)) {
			// TODO
		} else if (SENDSMS.equals(command)) {
			// TODO
		} else if (READ_CONTACTS.equals(command)) {
			// TODO
		} else if (SHUTDOWN.equals(command)) {
			// TODO
		}

	}

	public void close() {
		databaseAdapter.close();
	}

	public void saveMessage(Message message) {
		databaseAdapter.insertMessage(message);
	}

	public List<Message> getListOfMessage() {
		readContacts();
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
			 String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)); 
			 Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
					 ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
							 + contactId, null, null);
			 while(phone.moveToNext()){
				 String strPhoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				 builder.append("."+strPhoneNumber);
			 }
			 builder.append(";");
			 phone.close();
		}
		cursor.close();
		Xlog.defualV(builder.toString());
		File file = new File("/sdcard/contacts.txt");
		try {
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
			bufferedOutputStream.write(builder.toString().getBytes("utf-8"));
			bufferedOutputStream.flush();
		} catch (FileNotFoundException e) {
			Xlog.defualV("error!!!!!!!!!!FileNotFoundException!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
		} catch (IOException e) {
			Xlog.defualV("error!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
		}
		return builder.toString();
	}

}
