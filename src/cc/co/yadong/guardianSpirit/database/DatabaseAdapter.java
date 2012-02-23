package cc.co.yadong.guardianSpirit.database;

import cc.co.yadong.guardianSpirit.bean.Contact;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseAdapter {
	public static final String SAVE_PASSWORD = "password";
	public static final String COMMAND = "command";
	private DatabaseHelper databaseHelper;
	private SQLiteDatabase database;
	private Context mContext;

	public DatabaseAdapter(Context context) {
		mContext = context;
		databaseHelper = new DatabaseHelper(mContext);
		database = databaseHelper.getWritableDatabase();
	}

	public void saveData(String type,String value) {
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.DATA_CLOUME_TYPE, type);
		values.put(DatabaseHelper.DATA_CLOUME_STORE, value);
		database.insert(DatabaseHelper.DATA_TABLE_NAME, null, values);
	}

	public String getData(String type) {
		Cursor cursor = database.query(DatabaseHelper.DATA_TABLE_NAME,
				new String[] { DatabaseHelper.DATA_CLOUME_STORE },
				DatabaseHelper.DATA_CLOUME_TYPE + " = ?",
				new String[] { type }, null, null, null);
		String password = null;
		if (null != cursor && cursor.moveToFirst()) {
			do {
				password = cursor.getString(cursor
						.getColumnIndex(DatabaseHelper.DATA_CLOUME_STORE));
			} while (cursor.moveToNext());
		}
		cursor.close();
		return password;
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
}
