/**
 * 
 */
package cc.co.yadong.guardianSpirit.activity;

import cc.co.yadong.guardianSpirit.R;
import cc.co.yadong.guardianSpirit.handler.DataHandler;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author admin
 *
 */
public class Welcom extends Activity implements OnClickListener{
	private DataHandler dataHandler = null;
	private Button mCancelButton;
	private Button mNextButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		dataHandler = new DataHandler(this);
		if(dataHandler.isPasswordSaved()){
			//TODO
			this.finish();
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcom);
		mCancelButton = (Button) findViewById(R.id.cancel_button);
		mNextButton = (Button) findViewById(R.id.ok_button);
		mCancelButton.setOnClickListener(this);
		mNextButton.setOnClickListener(this);
	}
	public void onClick(View v) {
		if(v.equals(mCancelButton)){
			
		}else{
			Intent intent = new Intent(Welcom.this,SecondSetpActivity.class);
			startActivity(intent);
		}
		
	}
	

}
