package cc.co.yadong.guardianSpirit.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{
	private static final String TAG = "DatabaseHelper";
	private static final int DATABASE_VERSION = 1;
	protected static final String DATABASE_NAME = "guardianSpirit";
	protected static final String WHITE_AND_BLACK_TABLE_NAME  = "white_black";
	protected static final String WHITE_AND_BLACK_CLOUME_NAME  = "name";
	protected static final String WHITE_AND_BLACK_CLOUME_NUMBER = "number";
	protected static final String WHITE_AND_BLACK_CLOUME_TYPE = "type";
	protected static final String DATA_TABLE_NAME = "data";
	protected static final String DATA_CLOUME_TYPE = "data_type";
	protected static final String DATA_CLOUME_STORE = "storage";
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table " + WHITE_AND_BLACK_TABLE_NAME + "(_id Integer PRIMARY KEY AUTOINCREMENT, "+WHITE_AND_BLACK_CLOUME_NAME + " String NOT NULL,"+
				WHITE_AND_BLACK_CLOUME_NUMBER+" String NOT NULL,"+WHITE_AND_BLACK_CLOUME_TYPE+" Integer NOT NULL)";
		String sql1 = "create table " + DATA_TABLE_NAME + "(_id Integer PRIMARY KEY AUTOINCREMENT ,"+DATA_CLOUME_TYPE+" String NOT NULL,"+DATA_CLOUME_STORE + 
				" String NOT NULL)";
		Log.v(TAG, "sql = "+sql+"\n sql1 = "+sql1);
		db.execSQL(sql);
		db.execSQL(sql1);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "drop table "+WHITE_AND_BLACK_TABLE_NAME+" IF EXIST";
		String sql1 = "drop table "+DATA_TABLE_NAME+" IF EXIST";
		Log.v(TAG, "sql = "+sql+"\n sql1 = "+sql1);
		db.execSQL(sql);
		db.execSQL(sql1);
		onCreate(db);
	}

}
