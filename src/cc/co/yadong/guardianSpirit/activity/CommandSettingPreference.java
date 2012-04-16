package cc.co.yadong.guardianSpirit.activity;

import android.content.Context;
import android.preference.EditTextPreference;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

public class CommandSettingPreference extends EditTextPreference implements TextWatcher{
	private final static String DEFAULT_COMMAND_STRING = "command:";
	private String summary;

	public CommandSettingPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		setSummaryToName();
	}

	private void setSummaryToName() {
		// TODO: summary need get from database
		summary = "command:";
		setSummary(summary);
	}

	public void resume() {
		EditText editText = getEditText();
		editText.addTextChangedListener(this);
		
	}
	@Override
	protected void onClick() {
		super.onClick();
		EditText editText = getEditText();
		if (null != editText)
			if (null != summary)
				editText.setText(summary);
			else
				editText.setText(DEFAULT_COMMAND_STRING);
	}

	public void afterTextChanged(Editable s) {
		if(null != s && s.length() > 0){
			setSummary(s);
		}
		
	}

	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {}

	public void onTextChanged(CharSequence s, int start, int before, int count) {}

}