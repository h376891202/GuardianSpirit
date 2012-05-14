package cc.co.yadong.guardianSpirit.handler;

import android.content.Context;
import cc.co.yadong.guardianSpirit.database.DatabaseAdapter;

public class OrderHandler {
	private DatabaseAdapter adapter;
	private Context mContext;
	
	public OrderHandler(Context context) {
		mContext = context;
		adapter = new DatabaseAdapter(mContext);
	}

	public void close(){
		adapter.close();
	}
	 
	
	
	
}
