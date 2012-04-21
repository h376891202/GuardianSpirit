package cc.co.yadong.guardianSpirit.handler;

import android.content.Context;
import android.widget.CheckBox;
import cc.co.yadong.guardianSpirit.database.DatabaseAdapter;
import cc.co.yadong.guardianSpirit.util.MD5;

public class DataHandler{
	private DatabaseAdapter databaseAdapter;
	public DataHandler(Context context){
		this.databaseAdapter = new DatabaseAdapter(context);
	}
	public boolean checkPassword(String input){
		String md5password = MD5.getMD5Str(input);
		if(md5password != null && md5password.equals(databaseAdapter.getData(DatabaseAdapter.SAVE_PASSWORD)))
			return true;
		return false;
	}
	public boolean isPasswordSaved(){
		return databaseAdapter.getData(DatabaseAdapter.SAVE_PASSWORD)!=null;
	}
	public void setPassword(String password){
		String md5password = MD5.getMD5Str(password);
		databaseAdapter.saveData(DatabaseAdapter.SAVE_PASSWORD, md5password);
	}
	
	public void close(){
		databaseAdapter.close();
	}
}
