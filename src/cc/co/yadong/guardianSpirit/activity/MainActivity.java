package cc.co.yadong.guardianSpirit.activity;

import java.util.ArrayList;

import cc.co.yadong.guardianSpirit.R;
import cc.co.yadong.guardianSpirit.activity.tab.MessageTab;
import android.R.anim;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;

public class MainActivity extends TabActivity implements OnTabChangeListener{
	private static final String TAG = "MainActivity";
	
	private TabHost mTabHost;
	private LayoutInflater mLayoutInflater;
	private ArrayList<View> textViews;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.v(TAG,"-----------------------oncreate()");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		mTabHost = getTabHost();
		TabWidget tabWidget = mTabHost.getTabWidget();
		tabWidget.setBackgroundColor(android.R.color.background_light);
		mLayoutInflater = LayoutInflater.from(this);
		textViews = new ArrayList<View>();
		setupSettingTab();
		setupMessageTab();
		
	}
	private void setupMessageTab() {
		Intent intent = new Intent();
		intent.setClass(this, MessageTab.class);
		final View view = mLayoutInflater.inflate(R.layout.tab_short, null);
		view.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				view.setBackgroundColor(android.R.color.background_light);
			}
		});
		ImageView imageView = (ImageView)(view.findViewById(R.id.tab_icon));
		imageView.setImageResource(R.drawable.ic_launcher);
		TextView textView = (TextView)(view.findViewById(R.id.tab_label));
		textView.setText("2");
		textViews.add(view);
		mTabHost.addTab(mTabHost.newTabSpec("message_tb").setIndicator(view).setContent(intent));
	}
	private void setupSettingTab() {
		Intent intent = new Intent();
		intent.setClass(this, MessageTab.class);
		View view = mLayoutInflater.inflate(R.layout.tab_short, null);
		ImageView imageView = (ImageView)(view.findViewById(R.id.tab_icon));
		imageView.setImageResource(R.drawable.ic_launcher);
		TextView textView = (TextView)(view.findViewById(R.id.tab_label));
		textView.setText("2");
		textViews.add(view);
		mTabHost.addTab(mTabHost.newTabSpec("setting_tb").setIndicator(view).setContent(intent));
	}
	public void onTabChanged(String tabId) {
		System.out.println("yadong ----1");
		if(tabId.equals("message_tb")){
			textViews.get(0).setBackgroundColor(Color.RED);
		}else{
			textViews.get(1).setBackgroundColor(Color.RED);
		}
	}
	
}
