package cc.co.yadong.guardianSpirit.activity.tab;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import cc.co.yadong.guardianSpirit.R;

public class MessageTab extends ListActivity{
	private ListView listView;
	private LayoutInflater factory;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		listView = getListView();
		factory = LayoutInflater.from(this);
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
		Button delete =(Button)view.findViewById(R.id.delete_message);
		Button close =(Button)view.findViewById(R.id.close_message);
		ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
		imageView.setImageResource(R.drawable.alert_img);
		delete.setText("delete");
		close.setText("close");
		textView.setText("15002869434");
		textView1.setText("message");
		dialog.getWindow().setContentView(view);
	}
	
	
	
	
}
