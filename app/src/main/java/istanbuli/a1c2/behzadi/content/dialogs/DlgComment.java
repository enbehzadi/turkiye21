package istanbuli.a1c2.behzadi.content.dialogs;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import istanbuli.a1c2.R;


public class DlgComment extends Dialog{

	//Static
		public static final String	PREF="pref";
		public static final String  PREF_COMMENTS="comments";
	
	
	//Variables
		Context		mycontext;
		Activity	myActivity;
	//Views
		Button		btn_bazar,
					btn_exit;
		TextView	txt;
		
	public DlgComment(Context context) {
		super(context);
		mycontext=context;
		myActivity=(Activity) context;
	}
	
	public Boolean haveComment(){
		Boolean isComment=mycontext.getSharedPreferences(PREF, Context.MODE_PRIVATE)
				.getBoolean(PREF_COMMENTS, false);
		return isComment;
	}
	@Override
	public void show() {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

	}
	
	

}
