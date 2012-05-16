/**
 * 
 */
package cc.co.yadong.guardianSpirit.activity;

import cc.co.yadong.guardianSpirit.R;
import cc.co.yadong.guardianSpirit.bean.Data;
import cc.co.yadong.guardianSpirit.handler.DataHandler;
import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author cassidy
 *
 */
public class AlterPsswordOrNumber extends Activity implements OnClickListener{
	public static final int ALTER_PASSWORD = 1;
	public static final int ALTER_PRESET_NUMBER = 2;
	public static final String ALTER_TYPE = "alter_type";
	private TextView alterTypePrompt,surePasswordTextView;
	private EditText sureNewPassword,password,newVlue;
	private Button okButton,cancelButton;
	private DataHandler dataHandler;
	private int mAlterType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dataHandler = new DataHandler(this);
		setContentView(R.layout.alter_password_or_number);
		mAlterType = getIntent().getExtras().getInt(ALTER_TYPE);
		alterTypePrompt = (TextView) findViewById(R.id.textView2);
		sureNewPassword = (EditText) findViewById(R.id.sure_new_password);
		sureNewPassword.setVisibility(View.GONE);
		surePasswordTextView = (TextView) findViewById(R.id.sure_password_tv);
		surePasswordTextView.setVisibility(View.GONE);
		password = (EditText) findViewById(R.id.editText1);
		newVlue = (EditText) findViewById(R.id.new_value);
		okButton = (Button) findViewById(R.id.ok_button);
		cancelButton = (Button) findViewById(R.id.cancel_button);
		okButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);
		initView();

	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(dataHandler != null)
			dataHandler.close();
	}


	private void initView(){
		if(mAlterType == ALTER_PASSWORD){
			newVlue.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
			alterTypePrompt.setText(getResources().getString(R.string.input_new_password_prompt));
			sureNewPassword.setVisibility(View.VISIBLE);
			surePasswordTextView.setVisibility(View.VISIBLE);
		}else{
			newVlue.setInputType(InputType.TYPE_NUMBER_VARIATION_NORMAL);
			alterTypePrompt.setText(getResources().getString(R.string.input_new_number_prompt));
			sureNewPassword.setVisibility(View.GONE);
			surePasswordTextView.setVisibility(View.GONE);
		}
	}

	public void onClick(View v) {
		if(v.equals(okButton)){
			String passwordStr = password.getText().toString();
			String newValue = newVlue.getText().toString();
			if(!dataHandler.checkPassword(passwordStr))
				Toast.makeText(this, "password is wrong pls try it agin", Toast.LENGTH_SHORT).show();
			else{
				if(mAlterType == ALTER_PASSWORD){
					String sureValue = sureNewPassword.getText().toString();
					if(newValue== null || !newValue.equals(sureValue))
						Toast.makeText(this, "pls input right password and be sure your password is right", Toast.LENGTH_SHORT).show();
					else
						dataHandler.setPassword(newValue);
				}else{
					if(newValue != null){
						dataHandler.saveData(Data.FORWARD_NUMBER, newValue);
					}
				}
			}
			
		}else if(v.equals(cancelButton)){
			this.finish();
		}
		
	}
}
