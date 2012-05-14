package cc.co.yadong.guardianSpirit.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;
import cc.co.yadong.guardianSpirit.R;
import cc.co.yadong.guardianSpirit.activity.tab.MessageTab;
import cc.co.yadong.guardianSpirit.activity.tab.SettingTab;
import cc.co.yadong.guardianSpirit.util.Xlog;

public class MainActivity extends TabActivity implements OnTabChangeListener,OnClickListener {
	private static final String MESSAGE_LIST_ACTIVITY = "message_tab";
	private static final String SETTING_ACTIVITY = "setting_tab";
	private static final int DLG_SURE_EXIT = 1;

	private TabHost mTabHost;
	private LayoutInflater mLayoutInflater;
	private Intent mIntent;
	private Map<String, TextView> views;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Xlog.defualV("-----------------------oncreate()");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mIntent = getIntent();
		mTabHost = getTabHost();
		views = new HashMap<String, TextView>();
		TabWidget tabWidget = mTabHost.getTabWidget();
		tabWidget.setBackgroundResource(R.drawable.tab_bg);
		mLayoutInflater = LayoutInflater.from(this);
		setupSettingTab();
		setupMessageTab();
		mTabHost.setOnTabChangedListener(this);
		views.get(mTabHost.getCurrentTabTag()).setTextColor(
				getResources().getColor(R.color.tab_label_select));

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Xlog.defualV("onKeyDown keyCode = "+keyCode);
		if(keyCode == KeyEvent.KEYCODE_BACK){
			showDialog(DLG_SURE_EXIT);
		}
		return super.onKeyDown(keyCode, event);
	}

	private void setupMessageTab() {
		Intent intent = new Intent();
		intent.setClass(this, MessageTab.class);
		final View view = mLayoutInflater.inflate(R.layout.tab_short, null);
		ImageView imageView = (ImageView) (view.findViewById(R.id.tab_icon));
		imageView.setImageResource(R.drawable.tab_message);
		TextView textView = (TextView) (view.findViewById(R.id.tab_label));
		textView.setText("message");
		views.put(MESSAGE_LIST_ACTIVITY, textView);
		mTabHost.addTab(mTabHost.newTabSpec(MESSAGE_LIST_ACTIVITY)
				.setIndicator(view).setContent(intent));
	}

	private void setupSettingTab() {
		Intent intent = new Intent();
		intent.setClass(this, SettingTab.class);
		View view = mLayoutInflater.inflate(R.layout.tab_short, null);
		ImageView imageView = (ImageView) (view.findViewById(R.id.tab_icon));
		imageView.setImageResource(R.drawable.tab_setting);
		TextView textView = (TextView) (view.findViewById(R.id.tab_label));
		textView.setText("setting");
		views.put(SETTING_ACTIVITY, textView);
		mTabHost.addTab(mTabHost.newTabSpec(SETTING_ACTIVITY)
				.setIndicator(view).setContent(intent));
	}

	public void onTabChanged(String tabId) {
		for (TextView textView : views.values())
			textView.setTextColor(getResources().getColor(
					R.color.tab_label_unselect));
		TextView textView = views.get(tabId);
		textView.setTextColor(getResources().getColor(R.color.tab_label_select));

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		Xlog.defualV("on onContentChanged");
		super.onConfigurationChanged(newConfig);
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		Xlog.defualV("onCreate Dialog id = "+id);
		if(id == DLG_SURE_EXIT){
			return new AlertDialog.Builder(this).setTitle(R.string.warning)
					.setMessage(R.string.sure_exit)
					.setPositiveButton(R.string.ok, this)
					.setNegativeButton(R.string.cancel, this).create();
		}
		return super.onCreateDialog(id);
	}
	public void onClick(DialogInterface dialog, int which) {
		if(which == DialogInterface.BUTTON_POSITIVE)
			this.finish();
		else
			dismissDialog(DLG_SURE_EXIT);
	}
}
