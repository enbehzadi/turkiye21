package istanbuli.a1c2.behzadi.buy;

import android.content.Context;

public class SaveBuy {

	//Static
		private static final String PREF="save_buy";
		private static final String PREF_FULL_VER="fullv";
		
	//Variables
		Context	mycontext;
	public SaveBuy(Context context) {
		mycontext=context;
	}
	public void save(){
		mycontext.getSharedPreferences(PREF, mycontext.MODE_PRIVATE).edit()
		.putBoolean(PREF_FULL_VER, true).commit();
	}
	
	public Boolean get(){
		return mycontext.getSharedPreferences(PREF, mycontext.MODE_PRIVATE)
				.getBoolean(PREF_FULL_VER, false);
	}
	
}
