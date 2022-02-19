package istanbuli.a1c2.behzadi.content.dialogs;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import istanbuli.a1c2.R;


public class DlgReset extends Dialog{

	//Static
		public static final String	PREF="pref";
		public static final String  PREF_COMMENTS="comments";
	
	
	//Variables
		Context		mycontext;
		Activity	myActivity;
	//Views
		Button		btn_yes,
					btn_no;
		TextView	txt;
		boolean st;
	public DlgReset(Context context) {
		super(context);
		mycontext=context;
		myActivity=(Activity) context;
		st=false;
	}
	
	public Boolean haveComment(){
		Boolean isComment=mycontext.getSharedPreferences(PREF, Context.MODE_PRIVATE)
				.getBoolean(PREF_COMMENTS, false);
		return isComment;
	}
	
	public Boolean show(final String id) {
		  

		super.show();
		return st;
	}
	
	

}
