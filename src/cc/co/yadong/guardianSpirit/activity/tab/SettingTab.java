package cc.co.yadong.guardianSpirit.activity.tab;

import android.preference.PreferenceScreen;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import cc.co.yadong.guardianSpirit.R;

public class SettingTab extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.setting_tab);
	}
}
