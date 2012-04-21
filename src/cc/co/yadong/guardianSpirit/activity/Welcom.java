/**
 * 
 */
package cc.co.yadong.guardianSpirit.activity;

import cc.co.yadong.guardianSpirit.R;
import cc.co.yadong.guardianSpirit.handler.DataHandler;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * @author admin
 *
 */
public class Welcom extends Activity implements OnClickListener{
	private DataHandler dataHandler = null;
	private Button mCancelButton;
	private Button mNextButton;
	private EditText mPassword;
	private EditText mRepassword;
	private CheckBox mShowPassword;
	 
	protected void onCreate(Bundle savedInstanceState) {
		dataHandler = new DataHandler(this);
		if(dataHandler.isPasswordSaved()){
			Intent intent = new Intent();
			intent.setClass(Welcom.this, IdentityCheckingActivity.class);
			startActivity(intent);
			this.finish();
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcom);
		mCancelButton = (Button) findViewById(R.id.cancel_button);
		mNextButton = (Button) findViewById(R.id.ok_button);
		mNextButton.setEnabled(false);
		mCancelButton.setOnClickListener(this);
		mNextButton.setOnClickListener(this);
		mPassword = (EditText) findViewById(R.id.password_input);
		mRepassword = (EditText) findViewById(R.id.verify_password_input);
		mRepassword.addTextChangedListener(new TextChangeListener());
		mPassword.addTextChangedListener(new TextChangeListener());
		mShowPassword = (CheckBox) findViewById(R.id.show_password);
		mShowPassword.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			 
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					mPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
					mRepassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
				}else{
					mPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
					mRepassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				}
			}
		});
		
	}
	private class TextChangeListener implements TextWatcher{
		 
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if(mRepassword.getText().toString().equals(mPassword.getText().toString()))
				mNextButton.setEnabled(true);
			else
				mNextButton.setEnabled(false);
		}
		
		 
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {}
		
		 
		public void afterTextChanged(Editable s) {}
	}
	public void onClick(View v) {
		if(v.equals(mCancelButton)){
			
		}else{
			String password = mPassword.getText().toString();
			dataHandler.setPassword(password);
			Intent intent = new Intent(Welcom.this,SecondSetpActivity.class);
			startActivity(intent);
		}
		
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		dataHandler.close();
	}

}
