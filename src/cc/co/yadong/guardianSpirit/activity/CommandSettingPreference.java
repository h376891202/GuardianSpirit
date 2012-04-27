package cc.co.yadong.guardianSpirit.activity;

import cc.co.yadong.guardianSpirit.bean.Data;
import cc.co.yadong.guardianSpirit.handler.DataHandler;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.EditTextPreference;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;

public class CommandSettingPreference extends EditTextPreference implements TextWatcher{
	private final static String DEFAULT_COMMAND_STRING = "command:";
	private String summary;
	private DataHandler dataHandler;

	public CommandSettingPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		dataHandler = new DataHandler(context);
		setSummaryToName();
	}

	private void setSummaryToName() {
		summary = dataHandler.getData(Data.COMMAND_STRING);
		setSummary(summary);
	}

	public void resume() {
		EditText editText = getEditText();
		editText.addTextChangedListener(this);
	}
	@Override
	public void onClick(DialogInterface dialog, int which) {
		super.onClick(dialog, which);
		if(which == DialogInterface.BUTTON_POSITIVE){
			String s =  getEditText().getText().toString() ;
			if(null != s && !"".equals(s)){
				dataHandler.saveData(Data.COMMAND_STRING, s);
				setSummary(s);
			}
		}
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