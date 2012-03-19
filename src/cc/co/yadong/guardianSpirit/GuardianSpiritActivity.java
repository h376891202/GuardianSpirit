package cc.co.yadong.guardianSpirit;

import cc.co.yadong.guardianSpirit.handler.DataHandler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GuardianSpiritActivity extends Activity implements OnClickListener{
	private EditText editText;
	private Button okButton,cancelButton;
	private DataHandler dataHandler;
	private static final int INPUT_PASSWORD_DIALOG = 1;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	dataHandler = new DataHandler(this);
    	if(!dataHandler.isPasswordSaved())
    		showDialog(INPUT_PASSWORD_DIALOG);
    	this.editText = (EditText) findViewById(R.id.password_input);
    	okButton = (Button) findViewById(R.id.ok_button);
    	cancelButton = (Button) findViewById(R.id.cancel_button);
    	okButton.setOnClickListener(this);
    	cancelButton.setOnClickListener(this);
    
        
    }
	public void onClick(View v) {
		if (v.equals(okButton)) {
			String password = editText.getText().toString();
			if (!dataHandler.checkPassword(password)) {
				Toast.makeText(this, "passwordError", Toast.LENGTH_SHORT).show();
			}
		}else if(v.equals(cancelButton)){
			GuardianSpiritActivity.this.finish();
		}
		
	}
	@Override
	protected Dialog onCreateDialog(int id) {
		if(id == INPUT_PASSWORD_DIALOG){
			final EditText editText = new EditText(this);
			editText.setTransformationMethod(new PasswordTransformationMethod());
			return new AlertDialog.Builder(this)
					.setTitle("set your passwor for enter into this app!")
					.setView(editText)
					.setPositiveButton("ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									String password = editText.getText().toString();
									dataHandler.setPassword(password);
								}
							}).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									GuardianSpiritActivity.this.finish();
								}
							}).create();
		}
		return super.onCreateDialog(id);
	}
  
}