package cc.co.yadong.guardianSpirit;

import cc.co.yadong.guardianSpirit.bean.Contact;
import cc.co.yadong.guardianSpirit.database.DatabaseAdapter;
import cc.co.yadong.guardianSpirit.database.DatabaseHelper;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GuardianSpiritActivity extends Activity {
	 Button button1,button2,button3,button4;
	 DatabaseAdapter adapter;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	adapter = new DatabaseAdapter(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        ButtonListener listener = new ButtonListener();
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        
    }
    class ButtonListener implements OnClickListener{
		public void onClick(View v) {
			if(v.equals(button1)){
				adapter.saveData(DatabaseAdapter.SAVE_PASSWORD, "12345");
			}else if(v.equals(button2)){
				String data = adapter.getData(DatabaseAdapter.SAVE_PASSWORD);
				System.out.println(data);
			}else if(v.equals(button3)){
				Contact contact = new Contact();
				contact.setName("yadong");
				contact.setNumber("15002869434");
				contact.setType(Contact.BLACK_LIST);
				adapter.addContact(contact);
			}else if(v.equals(button4)){
				
			}
			
		}
    }
}