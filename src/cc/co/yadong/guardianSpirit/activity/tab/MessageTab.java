package cc.co.yadong.guardianSpirit.activity.tab;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import cc.co.yadong.guardianSpirit.R;
import cc.co.yadong.guardianSpirit.bean.Message;
import cc.co.yadong.guardianSpirit.database.DatabaseHelper;
import cc.co.yadong.guardianSpirit.handler.MessageHandler;

public class MessageTab extends ListActivity{
	private ListView listView;
	private LayoutInflater factory;
	private MessageHandler messageHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		listView = getListView();
		factory = LayoutInflater.from(this);
		messageHandler = new MessageHandler(this);
		super.onCreate(savedInstanceState);
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, initValue(),
				R.layout.message_item, new String[] {
						DatabaseHelper.MESSAGE_CLOUME_CONTENT,
						DatabaseHelper.MESSAGE_CLOUME_FROM,
						DatabaseHelper.MESSAGE_CLOUME_TIME }, new int[] {
						R.id.message_content, R.id.message_phone,
						R.id.message_time });
		setListAdapter(simpleAdapter);
		System.out.println("yadong"+listView);
	}
	private ArrayList<HashMap<String, String>> initValue() {
		ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String,String>>();
		List<Message> messages = messageHandler.getListOfMessage();
		for(Message message : messages){
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(DatabaseHelper.MESSAGE_CLOUME_CONTENT, message.getMeesage_content());
			map.put(DatabaseHelper.MESSAGE_CLOUME_FROM, message.getMessage_from());
			map.put(DatabaseHelper.MESSAGE_CLOUME_TIME, message.getMeesage_time());
			map.put(DatabaseHelper.MESSAGE_CLOUME_TYPE, message.getMessage_id()+"");
			arrayList.add(map);
		}
		return arrayList;

	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		System.out.println("yadong "+l+"\t"+v+"\t"+position+"\t"+id);
		showDialog(0);
		super.onListItemClick(l, v, position, id);
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		return alertDialog;
	}
	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		View view = factory.inflate(R.layout.message_dialog, null);
		view.setBackgroundColor(Color.BLACK);
		
		TextView textView = (TextView)view.findViewById(R.id.show_phone_number);
		TextView textView1 = (TextView)view.findViewById(R.id.alert_title);
		TextView showMessageTime = (TextView) view.findViewById(R.id.show_message_time);
		TextView messageContent = (TextView) view.findViewById(R.id.message_content);
		Button delete =(Button)view.findViewById(R.id.delete_message);
		Button close =(Button)view.findViewById(R.id.close_message);
		delete.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
			}
		});
		close.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dismissDialog(0);
			}
		});
		delete.setText("delete");
		close.setText("close");
		textView.setText("15002869434");
		textView1.setText("ordering message");
		showMessageTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime()));
		messageContent.setText("command:reboot!wo lege ca  ni daodi yao gansme ");
		dialog.getWindow().setContentView(view);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0,1,1,"delete all");
		return super.onCreateOptionsMenu(menu);
	}
	
	
}
