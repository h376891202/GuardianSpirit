package cc.co.yadong.guardianSpirit.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
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

public class MainActivity extends TabActivity implements OnTabChangeListener {
	private static final String TAG = "MainActivity";
	private static final String MESSAGE_LIST_ACTIVITY = "message_tab";
	private static final String SETTING_ACTIVITY = "setting_tab";

	private TabHost mTabHost;
	private LayoutInflater mLayoutInflater;
	private Intent mIntent;
	private Map<String, TextView> views;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.v(TAG, "-----------------------oncreate()");
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

	private void setCurrentTab() {
		String action = mIntent.getAction();
		Activity activity = getLocalActivityManager().getActivity(
				mTabHost.getCurrentTabTag());
		if (activity != null) {
			activity.closeOptionsMenu();
		}
		if (MESSAGE_LIST_ACTIVITY.equals(action)) {
			mTabHost.setCurrentTabByTag(MESSAGE_LIST_ACTIVITY);
		} else if (SETTING_ACTIVITY.equals(action))
			mTabHost.setCurrentTabByTag(SETTING_ACTIVITY);

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

}
