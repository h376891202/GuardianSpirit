package cc.co.yadong.guardianSpirit.util;

import android.util.Log;

public class Xlog {
	private static final boolean IS_LOG_ON = true;
	private static final String DEFAULT_TAG = "GuardianSpirit";

	public static void v(String tag, String messsage) {
		if (IS_LOG_ON)
			Log.v(tag, messsage);
	}

	public static void defualV(String message) {
		if (IS_LOG_ON)
			Log.v(DEFAULT_TAG, message);
	}
}
