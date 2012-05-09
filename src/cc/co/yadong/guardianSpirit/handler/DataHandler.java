package cc.co.yadong.guardianSpirit.handler;

import android.content.Context;
import cc.co.yadong.guardianSpirit.bean.Data;
import cc.co.yadong.guardianSpirit.database.DatabaseAdapter;
import cc.co.yadong.guardianSpirit.database.DatabaseHelper;
import cc.co.yadong.guardianSpirit.util.MD5;
import cc.co.yadong.guardianSpirit.util.Xlog;

public class DataHandler{
	private DatabaseAdapter databaseAdapter;
	public DataHandler(Context context){
		this.databaseAdapter = new DatabaseAdapter(context);
	}
	public boolean checkPassword(String input){
		String md5password = MD5.getMD5Str(input);
		if(md5password != null && md5password.equals(databaseAdapter.getData(Data.SAVE_PASSWORD)))
			return true;
		return false;
	}
	public boolean isPasswordSaved(){
		return databaseAdapter.getData(Data.SAVE_PASSWORD)!=null;
	}
	public void setPassword(String password){
		String md5password = MD5.getMD5Str(password);
		databaseAdapter.saveData(Data.SAVE_PASSWORD, md5password);
	}
	
	public int getErrorTime(){
		String time = databaseAdapter.getData(Data.PASSWORD_ERROR_COUNT);
		int output = 0;
		try {
			output = Integer.parseInt(time);
		} catch (NumberFormatException e) {
			Xlog.defualV("Number format exception for pase password error time");
			output = 4;
		}
		return output;
	}
	public void addErrorTime(int num){
		int errorTime = num+1;
		databaseAdapter.saveData(Data.PASSWORD_ERROR_COUNT, errorTime+"");
	}
	public boolean isSaveMessageSelect(){
		String s = databaseAdapter.getData(Data.SAVE_MESSAGE);
		if(DatabaseHelper.BOOLEAN_TRUE.equals(s))
			return true;
		return false;
	}
	
	public boolean isNotifySelect(){
		String s = databaseAdapter.getData(Data.NOTIFY_WHEN_HAVA_MESSAGE);
		if(DatabaseHelper.BOOLEAN_TRUE.equals(s))
			return true;
		return false;
	}
	
	public String getData(String data_cloume){
		return databaseAdapter.getData(data_cloume);
	}
	
	public void saveData(String data_cloume,String value){
		databaseAdapter.saveData(data_cloume, value);
	}
	public void unlockPassword(){
		databaseAdapter.saveData(Data.PASSWORD_ERROR_COUNT, 0+"");
	}
	//close database 
	public void close(){
		databaseAdapter.close();
	}
	
}
