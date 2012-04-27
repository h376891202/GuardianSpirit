package cc.co.yadong.guardianSpirit.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cc.co.yadong.guardianSpirit.R;
import cc.co.yadong.guardianSpirit.handler.DataHandler;

public class IdentityCheckingActivity extends Activity implements
		OnClickListener, TextWatcher {
	private EditText mPassword;
	private Button mButton;
	private DataHandler dataHandler;
	private TextView mTextView;
	private static final int ALLOW_MAX_ERROR_TIME = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.indetity_checking);
		mPassword = (EditText) findViewById(R.id.password_et);
		mPassword.addTextChangedListener(this);
		mButton = (Button) findViewById(R.id.ok_btn);
		mTextView = (TextView) findViewById(R.id.message);
		mButton.setOnClickListener(this);
		mButton.setEnabled(false);
		dataHandler = new DataHandler(this);
	}

	public void onClick(View v) {
		int errorTime = dataHandler.getErrorTime();
		if (errorTime >= ALLOW_MAX_ERROR_TIME) {
			mTextView.setVisibility(View.VISIBLE);
			if (errorTime == ALLOW_MAX_ERROR_TIME) {
				//TODO send message to phone number 
				
			}
			Toast.makeText(this, "application has locked,pls unlock it.",
					Toast.LENGTH_SHORT).show();
		} else {
			String password = null;
			if (mPassword != null) {
				password = mPassword.getText().toString();
			}
			if (password == null)
				return;
			else if (dataHandler.checkPassword(password)) {
				Intent intent = new Intent();
				intent.setClass(IdentityCheckingActivity.this,
						MainActivity.class);
				dataHandler.unlockPassword();
				startActivity(intent);
				this.finish();
			} else {
				dataHandler.addErrorTime(errorTime);
				Toast.makeText(this, "password error", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	public void afterTextChanged(Editable arg0) {
	}

	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (s.length() < 1)
			mButton.setEnabled(false);
		else
			mButton.setEnabled(true);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dataHandler.close();
	}

}
