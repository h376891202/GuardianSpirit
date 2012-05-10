package cc.co.yadong.guardianSpirit.activity.tab;

import android.content.Context;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;

public class SettingPreference extends CheckBoxPreference implements OnPreferenceChangeListener,OnPreferenceClickListener{
	private static final String SAVE_ORDERING_MESSAGE =  "save_ordering_message";
	private static final String REMINDER_MESSAGE = "reminder_message";
	private static final String MESSAGE_COMMAND_STRING = "message_command_string";
	private static final String OPEN_SHUTDOWN_REBOOT_FUNCTION = "open_shutdown_reboot_function";
	private static final String FORWARD_SMS = "forward_sms";
	private static final String READ_SEND_CONTACTS = "read_send_contacts";
	private static final String DELETE_PICTUR = "delete_picture";
	private String key;
	
	public SettingPreference(Context context) {
		super(context);
		this.setOnPreferenceChangeListener(this);
		this.setOnPreferenceClickListener(this);
		key = getKey();
	}

	public boolean onPreferenceChange(Preference preference, Object newValue) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean onPreferenceClick(Preference preference) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
