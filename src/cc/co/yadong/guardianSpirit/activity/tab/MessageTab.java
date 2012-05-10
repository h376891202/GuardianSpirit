package cc.co.yadong.guardianSpirit.activity.tab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
import cc.co.yadong.guardianSpirit.util.Xlog;

public class MessageTab extends ListActivity {
	private ListView listView;
	private LayoutInflater factory;
	private MessageHandler messageHandler;
	private int clickitem = -1;
	private SimpleAdapter mAdapter;
	private ArrayList<HashMap<String, String>> mMessages;
	private boolean mIsInit = false;
	private TextView mEmptyView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.odering_message_list);
		mEmptyView = (TextView) findViewById(R.id.emptyView);
		mEmptyView.setVisibility(View.GONE);
		listView = getListView();
		factory = LayoutInflater.from(this);
		messageHandler = new MessageHandler(this);
		super.onCreate(savedInstanceState);
		initValue();
		SmsIncommingReceiver receiver = new SmsIncommingReceiver();
		IntentFilter filter = new IntentFilter();
		if (mMessages.size() == 0) {
			mEmptyView.setVisibility(View.VISIBLE);
		}
		filter.addAction("co.cc.yadong.new");
		registerReceiver(receiver, filter);
		mAdapter = new SimpleAdapter(this, mMessages, R.layout.message_item,
				new String[] { DatabaseHelper.MESSAGE_CLOUME_CONTENT,
						DatabaseHelper.MESSAGE_CLOUME_FROM,
						DatabaseHelper.MESSAGE_CLOUME_TIME }, new int[] {
						R.id.message_content, R.id.message_phone,
						R.id.message_time });
		setListAdapter(mAdapter);
		System.out.println("yadong" + listView);
		mIsInit = true;

	}

	private void checkListIsEmpty() {
		if (mMessages.size() == 0) {
			mEmptyView.setVisibility(View.VISIBLE);
		}
	}

	private void initValue() {
		Xlog.defualV(" INIT VALUE");
		mMessages = new ArrayList<HashMap<String, String>>();
		List<Message> listMessages = messageHandler.getListOfMessage();
		if (null != listMessages) {
			for (Message message : listMessages) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put(DatabaseHelper.MESSAGE_CLOUME_CONTENT,
						message.getMeesage_content());
				map.put(DatabaseHelper.MESSAGE_CLOUME_FROM,
						message.getMessage_from());
				map.put(DatabaseHelper.MESSAGE_CLOUME_TIME,
						message.getMeesage_time());
				map.put(DatabaseHelper.MESSAGE_CLOUME_TYPE,
						message.getMessage_type() + "");
				map.put("_id", message.getMessage_id() + "");
				mMessages.add(map);
			}
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		System.out.println("yadong " + l + "\t" + v + "\t" + position + "\t"
				+ id);
		clickitem = position;
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

		TextView textView = (TextView) view
				.findViewById(R.id.show_phone_number);
		TextView textView1 = (TextView) view.findViewById(R.id.alert_title);
		TextView showMessageTime = (TextView) view
				.findViewById(R.id.show_message_time);
		TextView messageContent = (TextView) view
				.findViewById(R.id.message_content);
		Button delete = (Button) view.findViewById(R.id.delete_message);
		Button close = (Button) view.findViewById(R.id.close_message);
		final Map<String, String> message = mMessages.get(clickitem);
		Xlog.defualV("create dialog! clickitem = "+clickitem +"message = "+message);
		delete.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dismissDialog(0);
				AlertDialog alertDialog = new AlertDialog.Builder(
						MessageTab.this)
						.setTitle("Waring")
						.setMessage("do you want delete this message")
						.setPositiveButton("ok",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										messageHandler.deleteMessage(Integer
												.parseInt(message.get("_id")));
										mMessages.remove(clickitem);
										mAdapter.notifyDataSetChanged();
										checkListIsEmpty();
									}
								}).setNegativeButton("cancel", null).create();
				alertDialog.show();
			}
		});
		close.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dismissDialog(0);
			}
		});

		delete.setText("delete");
		close.setText("close");

		textView.setText(message.get(DatabaseHelper.MESSAGE_CLOUME_FROM));
		textView1.setText("ordering message");
		showMessageTime
				.setText(message.get(DatabaseHelper.MESSAGE_CLOUME_TIME));
		messageContent.setText(message
				.get(DatabaseHelper.MESSAGE_CLOUME_CONTENT));
		dialog.getWindow().setContentView(view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, "delete all");
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		messageHandler.deleteAllMessage();
		reFlashList();
		return super.onOptionsItemSelected(item);
	}
	private class SmsIncommingReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Xlog.defualV("receive aciton="+intent.getAction());
			reFlashList();
		}

	}

	private void reFlashList() {
		if (mIsInit) {
			initValue();
			mAdapter = new SimpleAdapter(MessageTab.this, mMessages,
					R.layout.message_item, new String[] {
							DatabaseHelper.MESSAGE_CLOUME_CONTENT,
							DatabaseHelper.MESSAGE_CLOUME_FROM,
							DatabaseHelper.MESSAGE_CLOUME_TIME }, new int[] {
							R.id.message_content, R.id.message_phone,
							R.id.message_time });
			setListAdapter(mAdapter);
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mIsInit = false;
		messageHandler.close();
	}

	
}
