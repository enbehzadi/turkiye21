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
import istanbuli.a1c2.behzadi.farsi.TFace;


public class DlgResult extends Dialog{

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
		
	public DlgResult(Context context) {
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

		//Prepare
			TFace tf=new TFace(mycontext);
			btn_bazar.setTypeface(tf.byekan);
			btn_exit.setTypeface(tf.byekan);
			txt.setTypeface(tf.byekan);
		//setLisetenrs
			btn_bazar.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					mycontext.getSharedPreferences(PREF, Context.MODE_PRIVATE)
						.edit().putBoolean(PREF_COMMENTS, true).commit();
					Intent intent =new Intent(Intent.ACTION_EDIT);
					String pName=mycontext.getApplicationContext().getPackageName();
					intent.setData(Uri.parse("http://cafebazaar.ir/app/"+pName+"/?l=fa"));
					mycontext.startActivity(intent);
					myActivity.finish();
				}
			});
			btn_exit.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					myActivity.finish();
				}
			});
		super.show();
	}
	
	

}
