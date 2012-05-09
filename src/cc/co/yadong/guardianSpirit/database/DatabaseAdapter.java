package cc.co.yadong.guardianSpirit.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cc.co.yadong.guardianSpirit.bean.Contact;
import cc.co.yadong.guardianSpirit.bean.Data;
import cc.co.yadong.guardianSpirit.bean.Message;
import cc.co.yadong.guardianSpirit.util.Xlog;

public class DatabaseAdapter {
	private DatabaseHelper databaseHelper;
	private SQLiteDatabase database;
	private Context mContext;

	public DatabaseAdapter(Context context) {
		mContext = context;
		databaseHelper = new DatabaseHelper(mContext);
		database = databaseHelper.getWritableDatabase();
	}
	
	public void close(){
		if(null != database)
			database.close();
		if(null != databaseHelper)
			databaseHelper.close();
	}
	public void saveData(String type, String value) {
		if (type.equals(Data.COMMAND_STRING))
			value = value + ":";
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.DATA_CLOUME_TYPE, type);
		values.put(DatabaseHelper.DATA_CLOUME_STORE, value);
		if (null != getData(type)) {
			database.update(DatabaseHelper.DATA_TABLE_NAME, values,
					DatabaseHelper.DATA_CLOUME_TYPE + "=?",
					new String[] { type });
		} else {
			database.insert(DatabaseHelper.DATA_TABLE_NAME, null, values);
		}

	}

	public String getData(String type) {
		Cursor cursor = database.query(DatabaseHelper.DATA_TABLE_NAME,
				new String[] { DatabaseHelper.DATA_CLOUME_STORE },
				DatabaseHelper.DATA_CLOUME_TYPE + " = ?",
				new String[] { type }, null, null, null);
		String value = null;
		if (null != cursor && cursor.moveToFirst()) {
			do {
				value = getStringValue(cursor, DatabaseHelper.DATA_CLOUME_STORE);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return value;
	}

	public void addContact(Contact contact) {
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.WHITE_AND_BLACK_CLOUME_NAME,
				contact.getName());
		values.put(DatabaseHelper.WHITE_AND_BLACK_CLOUME_NUMBER,
				contact.getNumber());
		values.put(DatabaseHelper.WHITE_AND_BLACK_CLOUME_TYPE,
				contact.getType());
		database.insert(DatabaseHelper.WHITE_AND_BLACK_TABLE_NAME, null, values);
	}

	public void deleteContact(int id) {
		String sql = "delete from " + DatabaseHelper.WHITE_AND_BLACK_TABLE_NAME
				+ " where _id=" + id;
		database.execSQL(sql);
	}

	public List<Message> getListOfMessage() {
		List<Message> messages = null;
		Cursor cursor = database.query(DatabaseHelper.MESSAGE_TABLE_NAME,
				new String[] { DatabaseHelper.MESSAGE_CLOUME_CONTENT,
						DatabaseHelper.MESSAGE_CLOUME_FROM,
						DatabaseHelper.MESSAGE_CLOUME_TIME,
						DatabaseHelper.MESSAGE_CLOUME_TYPE }, null, null, null,
				null, DatabaseHelper.MESSAGE_CLOUME_TIME + " desc");
		if (null == cursor || !cursor.moveToFirst())
			return null;
		else {
			messages = new ArrayList<Message>();
			while(true){
				Message message = new Message();
				message.setMeesage_content(getStringValue(cursor, DatabaseHelper.MESSAGE_CLOUME_CONTENT));
				message.setMeesage_time(getStringValue(cursor, DatabaseHelper.MESSAGE_CLOUME_TIME));
				message.setMessage_from(getStringValue(cursor, DatabaseHelper.MESSAGE_CLOUME_FROM));
				message.setMessage_type(getIntVaue(cursor, DatabaseHelper.MESSAGE_CLOUME_TYPE));
				messages.add(message);
				if(!cursor.moveToNext())
					break;
			}
		}
		cursor.close();
		return messages;
	}

	public String getStringValue(Cursor cursor, String cloumeName) {
		return cursor.getString(cursor.getColumnIndex(cloumeName));
	}
	public int getIntVaue(Cursor cursor, String cloumeName) {
		return cursor.getInt(cursor.getColumnIndex(cloumeName));
	}
	
	public void insertMessage(Message message){
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.MESSAGE_CLOUME_CONTENT, message.getMeesage_content());
		values.put(DatabaseHelper.MESSAGE_CLOUME_FROM, message.getMessage_from());
		values.put(DatabaseHelper.MESSAGE_CLOUME_TIME, message.getMeesage_time());
		values.put(DatabaseHelper.MESSAGE_CLOUME_TYPE, message.getMessage_type());
		database.insert(DatabaseHelper.MESSAGE_TABLE_NAME, null, values);
	}
	
	public void deleteMessage(int id){
		Xlog.defualV("delete message id = "+id);
		StringBuilder sql = new StringBuilder("delete from ");
		sql.append(DatabaseHelper.MESSAGE_TABLE_NAME);
		if (id != -1) {
			sql.append(" where _id = ");
			sql.append(id+"");
		}
		database.execSQL(sql.toString());
	}
	
}
