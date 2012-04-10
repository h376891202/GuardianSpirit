package cc.co.yadong.guardianSpirit.activity.tab;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import cc.co.yadong.guardianSpirit.R;

public class MessageTab extends ListActivity{
	private ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		listView = getListView();
		HashMap<String, String> maps = new HashMap<String, String>();
		maps.put("1", "command:reboot");
		maps.put("2", "150028694343");
		maps.put("3", "2012/12/12");
		HashMap<String, String> maps2 = new HashMap<String, String>();
		maps.put("1", "command:reboot");
		maps.put("2", "150028694343");
		maps.put("3", "2012/12/12");
		ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String,String>>();
		arrayList.add(maps);
		arrayList.add(maps2);
		arrayList.add(new HashMap<String, String>());
		arrayList.add(new HashMap<String, String>());
		
		arrayList.add(new HashMap<String, String>());
		arrayList.add(new HashMap<String, String>());
		arrayList.add(new HashMap<String, String>());
		arrayList.add(new HashMap<String, String>());
		arrayList.add(new HashMap<String, String>());arrayList.add(new HashMap<String, String>());
		arrayList.add(new HashMap<String, String>());
		
		arrayList.add(new HashMap<String, String>());
		super.onCreate(savedInstanceState);
		SimpleAdapter simpleAdapter = new SimpleAdapter(this,arrayList,R.layout.message_item,new String[]{"1","2","3"},new int []{R.id.message_content,R.id.message_phone,R.id.message_time});
		setListAdapter(simpleAdapter);
		System.out.println("yadong"+listView);
		listView.setOnLongClickListener(new OnLongClickListener() {
			public boolean onLongClick(View v) {
				System.out.println("yadong --- on lang click");
				return false;
			}
		});
	}
	

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		System.out.println("yadong "+l+"\t"+v+"\t"+position+"\t"+id);
		showDialog(0);
		super.onListItemClick(l, v, position, id);
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		Button button = new Button(this);
		button.setText("delete");
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				System.out.println("yadong- on button click!");
				
			}
		});
		AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle("message").setMessage("command:reboot").setView(button).setPositiveButton("ok", null).setNegativeButton("cancel", null).create();
		return alertDialog;
	}
	
	
	
}
