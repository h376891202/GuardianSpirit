package cc.co.yadong.guardianSpirit.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import cc.co.yadong.guardianSpirit.R;
import cc.co.yadong.guardianSpirit.bean.Data;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String TAG = "DatabaseHelper";
	private static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "guardianSpirit.db";
	public static final String WHITE_AND_BLACK_TABLE_NAME = "white_black";
	public static final String WHITE_AND_BLACK_CLOUME_NAME = "name";
	public static final String WHITE_AND_BLACK_CLOUME_NUMBER = "number";
	public static final String WHITE_AND_BLACK_CLOUME_TYPE = "type";
	public static final String DATA_TABLE_NAME = "data";
	public static final String DATA_CLOUME_TYPE = "data_type";
	public static final String DATA_CLOUME_STORE = "storage";
	public static final String MESSAGE_TABLE_NAME = "message";
	public static final String MESSAGE_CLOUME_FROM = "message_from";
	public static final String MESSAGE_CLOUME_TIME = "time";
	public static final String MESSAGE_CLOUME_CONTENT = "content";
	public static final String MESSAGE_CLOUME_TYPE = "type";
	public static final String BOOLEAN_TRUE  = "1";
	public static final String BOOLEAN_FLASE = "0";
	private Context mContext;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table " + WHITE_AND_BLACK_TABLE_NAME + "(_id Integer PRIMARY KEY AUTOINCREMENT, "+WHITE_AND_BLACK_CLOUME_NAME + " String NOT NULL,"+
				WHITE_AND_BLACK_CLOUME_NUMBER+" String NOT NULL,"+WHITE_AND_BLACK_CLOUME_TYPE+" Integer NOT NULL)";
		String sql1 = "create table " + DATA_TABLE_NAME + "(_id Integer PRIMARY KEY AUTOINCREMENT ,"+DATA_CLOUME_TYPE+" String NOT NULL,"+DATA_CLOUME_STORE + 
				" String NOT NULL)";
		String sql2 = "create table " + MESSAGE_TABLE_NAME + "(_ID Integer PRIMARY KEY AUTOINCREMENT ,"+MESSAGE_CLOUME_CONTENT+" String NOT NULL, "+MESSAGE_CLOUME_FROM+ " String NOT NULL ,"+
		MESSAGE_CLOUME_TIME+" String NOT NULL , "+MESSAGE_CLOUME_TYPE+" Integer NOT NULL)";
		Log.v(TAG, "sql = "+sql+"\n sql1 = "+sql1);
		db.execSQL(sql);
		db.execSQL(sql1);
		db.execSQL(sql2);
		loadDefualtValue(db);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "drop table " + WHITE_AND_BLACK_TABLE_NAME + " IF EXIST";
		String sql1 = "drop table " + DATA_TABLE_NAME + " IF EXIST";
		String sql2 = "drop table " + MESSAGE_TABLE_NAME + " IF EXIST";
		Log.v(TAG, "sql = " + sql + "\n sql1 = " + sql1);
		db.execSQL(sql);
		db.execSQL(sql1);
		db.execSQL(sql2);
		onCreate(db);
	}

	public void loadDefualtValue(SQLiteDatabase database) {
		SQLiteStatement statement = null;
		try {
			statement = database.compileStatement("insert into "
					+ DATA_TABLE_NAME + "(" + DATA_CLOUME_TYPE + ","
					+ DATA_CLOUME_STORE + ") values(?,?)");
			loadDataString(statement, Data.COMMAND_STRING,
					Data.COMMAND_STRING);
			String commends[] = mContext.getResources().getStringArray(
					R.array.command_type);
			for (String commend : commends)
				loadDataString(statement, commend, commend);
			loadDataString(statement,Data.PASSWORD_ERROR_COUNT,0+"");
			loadDataBoolean(statement, Data.SAVE_MESSAGE, true);
			loadDataBoolean(statement, Data.NOTIFY_WHEN_HAVA_MESSAGE, true);
		} finally {
			if (null != statement)
				statement.close();
		}
	}

	public void loadDataString(SQLiteStatement statement, String key, String value) {
		statement.bindString(1, key);
		statement.bindString(2, value);
		statement.execute();
	}
	
	public void loadDataBoolean(SQLiteStatement statement,String key, boolean value) {
		if(value){
			loadDataString(statement, key,BOOLEAN_TRUE);
		}else{
			loadDataString(statement, key, BOOLEAN_FLASE);
		}
	}
	

}
