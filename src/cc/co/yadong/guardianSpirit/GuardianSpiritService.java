package cc.co.yadong.guardianSpirit;

import java.io.File;
import java.util.ArrayList;

import android.app.Service;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.provider.ContactsContract.RawContacts;
import cc.co.yadong.guardianSpirit.util.Xlog;

public class GuardianSpiritService extends Service{
	public static final String COMMAND = "command";
	public static final String DELETE_ALL_PHOTOT_ACTION = "delete_photo";
	public static final String DELETE_ALL_CONTACTS_ACTION = "delete_contacts";
	public static final String DELETE_PHOTOT_VALUE = "url";
	public static final String WORK_COMPLATE = "cc.co.yadong.work_complate";
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		String action = intent.getAction();
		if(DELETE_ALL_PHOTOT_ACTION.equals(action)){
			String path = intent.getExtras().getString(DELETE_PHOTOT_VALUE);
			deletePath(path);
		}else if(DELETE_ALL_CONTACTS_ACTION.equals(action)){
			deleteContacts();
		}else
			Xlog.defualV("ERROR !!!! -- ERROR action");
//		Intent intent2 = new Intent(WORK_COMPLATE);
//		intent2.putExtra(COMMAND, action);
//		sendBroadcast(new Intent(intent2));
	}
	
	private void deletePath(String path) {
		File file = new File(path);
		if (file.exists()) {
			if (!file.isDirectory()) {
				file.delete();
			} else {
				String files[] = file.list();
				if (files.length == 0) {
					file.delete();
				} else {
					for (String filepath : files) {
						deletePath(path+"/"+filepath);
					}
				}
			}
		}
	}
	
	private void deleteContacts(){
		ContentResolver cr = this.getContentResolver();
		Cursor cursor = cr
				.query( android.provider.ContactsContract.Data.CONTENT_URI,
						new String[] { android.provider.ContactsContract.Data.RAW_CONTACT_ID },
						null, null, null);
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		while (cursor.moveToNext()) {
			long id = cursor
					.getLong(cursor
							.getColumnIndex(android.provider.ContactsContract.Data.RAW_CONTACT_ID));
			ops.add(ContentProviderOperation.newDelete(
					ContentUris.withAppendedId(RawContacts.CONTENT_URI, id))
					.build());
			Xlog.defualV("delete contacts id="+id);
			try {
				this.getContentResolver().applyBatch(
						ContactsContract.AUTHORITY, ops);
			} catch (Exception e) {
				Xlog.defualV("ERROR !!!!!!!!!!!!! deleteAllContacts " + e);
			}
		}
		cursor.close();
	}
	
}
