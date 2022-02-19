package istanbuli.a1c2.behzadi.farsi;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

public class TFace {
	//Variables
	Context	mycontext;
	public Typeface	byekan,tehran,homa,dastnevis;
	public TFace(Context context) {
		mycontext=context;
		byekan= Typeface.createFromAsset(mycontext.getAssets(), "fonts/byekan.ttf");
		tehran= Typeface.createFromAsset(mycontext.getAssets(), "fonts/TehranB.ttf");
	}
	
	public void set_tface(View[] view,Typeface tf){
		for (View view2 : view) {
			((TextView)view2).setTypeface(tf);
		}
	}

}
