package cc.co.yadong.guardianSpirit.activity.tab;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.util.AttributeSet;
import cc.co.yadong.guardianSpirit.bean.Data;
import cc.co.yadong.guardianSpirit.database.DatabaseHelper;
import cc.co.yadong.guardianSpirit.handler.DataHandler;
import cc.co.yadong.guardianSpirit.util.Xlog;

public class SettingPreference extends CheckBoxPreference implements OnPreferenceChangeListener, OnPreferenceClickListener,CloseDatabaseInterface{
	private String key;
	private DataHandler dataHandler;
	public static List<Preference> preferences = new ArrayList<Preference>();
	
	public SettingPreference(Context context) {
		super(context);
		init(context);
	}
	public SettingPreference(Context context, AttributeSet attrs){
		super(context, attrs);
		init(context);
	}
	public SettingPreference(Context context, AttributeSet attrs, int defStyle){
		super(context,attrs,defStyle);
		init(context);
	}
	
	private void init(Context context){
		this.setOnPreferenceChangeListener(this);
		this.setOnPreferenceClickListener(this);
		key = getKey();
		dataHandler = new DataHandler(context);
		setChecked(getStates());
		preferences.add(this);
	}
	public boolean getStates(){
		String value = dataHandler.getData(key);
		return DatabaseHelper.BOOLEAN_TRUE.equals(value);
	}
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		Xlog.defualV("onPreferenceChange key ="+key +" new Value = "+newValue);
		this.setChecked((Boolean)newValue);
		if(this.isChecked())
			dataHandler.saveData(key,DatabaseHelper.BOOLEAN_TRUE);
		else
			dataHandler.saveData(key, DatabaseHelper.BOOLEAN_FLASE);
		if(Data.OPEN_SERVICE.equals(key)){
				for(Preference settingPreference : preferences){
					if(!Data.OPEN_SERVICE.equals(settingPreference.getKey())){
						if((Boolean)newValue)
							settingPreference.setEnabled(true);
						else
							settingPreference.setEnabled(false);
				}
					
			}
		}
		return false;
	}

	public boolean onPreferenceClick(Preference preference) {
		Xlog.defualV("onPreferenceClick key = "+ key);
		return true;
	}
	
	public void closeSelf(){
		dataHandler.close();
	}
	
	public static void close(){
		for(Preference preference : preferences){
			((CloseDatabaseInterface)preference).closeSelf();
		}
	}
	
}
