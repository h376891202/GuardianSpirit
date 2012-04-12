/**
 * 
 */
package cc.co.yadong.guardianSpirit.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cc.co.yadong.guardianSpirit.R;

/**
 * @author admin
 *
 */
public class SecondSetpActivity extends Activity{
	Button nextSetup;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_setup);
		nextSetup = (Button) findViewById(R.id.ok_button);
		nextSetup.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(SecondSetpActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});
	}

}
