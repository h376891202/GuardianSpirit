package cc.co.yadong.guardianSpirit.activity.tab;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import cc.co.yadong.guardianSpirit.R;
import cc.co.yadong.guardianSpirit.handler.DataHandler;

public class SettingTab extends PreferenceActivity {
	private DataHandler dataHandler;
	private Preference saveMessage;
	private Preference remindeMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.setting_tab);
		dataHandler = new DataHandler(this);
		saveMessage = findPreference("save_ordering_message");
		remindeMessage = findPreference("reminder_message");
		initValue();
	}

	private void initValue() {
		boolean isSaveMessage = dataHandler.isSaveMessageSelect();
		if (saveMessage != null)
			saveMessage.setPersistent(isSaveMessage);
		boolean isRemindeMessage = dataHandler.isNotifySelect();
		if (remindeMessage != null)
			remindeMessage.setPersistent(isRemindeMessage);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		dataHandler.close();
	}
}
