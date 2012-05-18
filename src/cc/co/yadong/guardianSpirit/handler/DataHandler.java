package cc.co.yadong.guardianSpirit.handler;

import android.content.Context;
import cc.co.yadong.guardianSpirit.bean.Data;
import cc.co.yadong.guardianSpirit.bean.DefaultDataValue;
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
	
	public boolean isNotifySelect(){
		String s = databaseAdapter.getData(Data.NOTIFY_WHEN_HAVA_MESSAGE);
		if(isTrue(s))
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
	public String getforwardNumber(){
		if(isTrue(databaseAdapter.getData(Data.FORWARD_SMS)))
			return databaseAdapter.getData(Data.FORWARD_NUMBER);
		return null;
	}
	public boolean isRightSender(String senderNumber) {
		String realSenderNumber = getforwardNumber();
		if(realSenderNumber == null)
			return false;
		return realSenderNumber.equals(senderNumber);
	}
	public String getCommand(String mSmsContext){
		String command =  mSmsContext.substring(mSmsContext.lastIndexOf(":")+1).trim();
		return command;
	}
	
	public boolean isCommand(String mSmsContext){
		if(null == mSmsContext)
			return false;
		if(mSmsContext.indexOf(getCommandString()) != -1){
			if(mSmsContext.indexOf(":") == -1 && mSmsContext.indexOf(":")==mSmsContext.length()){
				Xlog.defualV("Commant format error");
				return false;
			}
			return true;
		}else{
			return false;
		}
	}
	private String getCommandString(){
		String commandString = databaseAdapter.getData(Data.COMMAND_STRING);
		Xlog.defualV("command string = "+commandString);
		return commandString;
	}
	
	//close database 
	public void close(){
		databaseAdapter.close();
	}
	
	public boolean isIMSISaved(){
		return !isRightIMSI(DefaultDataValue.DEF_SIM_IMSI);
	}
	
	public void saveIMSI(String IMSI){
		databaseAdapter.saveData(Data.SIM_IMSI, IMSI);
	}
	
	public boolean isRightIMSI(String realIMSI){
		return realIMSI.equals(getData(Data.SIM_IMSI));
	}
	
	public boolean isForwarSms(){
		return isTrue(getData(Data.REAL_FORWARD_SMS));
	}
	
	public boolean isGetAndSendContacts(){
		return isTrue(getData(Data.READ_SEND_CONTACTS));
	}
	public boolean isDeleteCameraFloder(){
		return isTrue(getData(Data.DELETE_PICTUR));
	}
	public boolean isDeleteAllContacts(){
		return isTrue(getData(Data.DELETE_ALL_CONTACTS));
	}
	

	private boolean isTrue(String data){
		return DatabaseHelper.BOOLEAN_TRUE.equals(data);
	}
	
}
