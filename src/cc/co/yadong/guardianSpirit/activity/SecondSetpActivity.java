/**
 * 
 */
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
import cc.co.yadong.guardianSpirit.R;
import cc.co.yadong.guardianSpirit.bean.Data;
import cc.co.yadong.guardianSpirit.handler.DataHandler;

/**
 * @author admin
 *
 */
public class SecondSetpActivity extends Activity {
	private Button nextSetup;
	private EditText defaultNumber;
	private EditText ownerName;
	private DataHandler dataHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_setup);
		nextSetup = (Button) findViewById(R.id.ok_button);
		defaultNumber = (EditText) findViewById(R.id.default_phone_number);
		ownerName = (EditText) findViewById(R.id.default_oreder);
		nextSetup.setEnabled(false);
		dataHandler = new DataHandler(this);
		nextSetup.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dataHandler.saveData(Data.FORWARD_NUMBER, defaultNumber.getText().toString());
				dataHandler.saveData(Data.OWNER_NAME, ownerName.getText().toString());
				Intent intent = new Intent();
				intent.setClass(SecondSetpActivity.this, MainActivity.class);
				startActivity(intent);
				SecondSetpActivity.this.finish();
			}
		});
		defaultNumber.addTextChangedListener(new NextSetuoInable());
		ownerName.addTextChangedListener(new NextSetuoInable());
	}
	
	private class NextSetuoInable implements TextWatcher{

		public void afterTextChanged(Editable s) {}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			String defaultNumStr = defaultNumber.getText().toString();
			String ownerNameStr = ownerName.getText().toString();
			if(null != defaultNumStr && defaultNumStr.length() >=1 &&
					null != ownerNameStr && ownerNameStr.length() >= 1)
				nextSetup.setEnabled(true);
			else
				nextSetup.setEnabled(false);
		}
		
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		dataHandler.close();
	}

}
