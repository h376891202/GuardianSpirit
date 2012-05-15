package cc.co.yadong.guardianSpirit.activity.tab;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.view.KeyEvent;
import cc.co.yadong.guardianSpirit.R;
import cc.co.yadong.guardianSpirit.handler.DataHandler;
import cc.co.yadong.guardianSpirit.util.Xlog;

public class SettingTab extends PreferenceActivity implements OnClickListener{
	private DataHandler dataHandler;
	private CommandSettingPreference commandSettingPreference; 
	private static final int DLG_SURE_EXIT = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.setting_tab);
		dataHandler = new DataHandler(this);
		commandSettingPreference = (CommandSettingPreference) findPreference("message_command_string");
		SettingPreference.preferences.add(commandSettingPreference);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Xlog.defualV("onKeyDown keyCode = "+keyCode);
		if(keyCode == KeyEvent.KEYCODE_BACK){
			showDialog(DLG_SURE_EXIT);
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//close all setting preference's database
		SettingPreference.close();
		dataHandler.close();
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		Xlog.defualV("onCreate Dialog id = "+id);
		if(id == DLG_SURE_EXIT){
			return new AlertDialog.Builder(this).setTitle(R.string.warning)
					.setMessage(R.string.sure_exit)
					.setPositiveButton(R.string.ok, this)
					.setNegativeButton(R.string.cancel, this).create();
		}
		return super.onCreateDialog(id);
	}
	public void onClick(DialogInterface dialog, int which) {
		if(which == DialogInterface.BUTTON_POSITIVE)
			System.exit(0);
		else
			dismissDialog(DLG_SURE_EXIT);
	}
}
