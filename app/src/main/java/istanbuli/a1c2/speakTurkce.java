package istanbuli.a1c2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.speech.tts.TextToSpeech;

import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.*;

public class speakTurkce
{
	TextToSpeech tts;


	public void setspeake(final Context cntx, final String sp)
	{

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			try {


			tts = new TextToSpeech(cntx, new TextToSpeech.OnInitListener() {

				boolean isGoogleAvaible =true;
				@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
				@Override
				public void onInit(int status) {

					Locale locale = new Locale("tr_TR");//set Locale
					tts.setLanguage(locale);

					try {
						tts.getVoices();
					}
					catch (Exception e)
					{

					}

					if (status != TextToSpeech.ERROR) {

					}
					if (status == TextToSpeech.SUCCESS) {

						String spconvert=converToTurkishCharacter(sp);
						String[] text = spconvert.split("\\.");//split with every "dot"

						for (int i = 0; i < text.length; i++) {
							HashMap uttrerance = new HashMap();
							uttrerance.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, Integer.toString(i));
							tts.speak(text[i], 1, uttrerance);

						}
					}


					//-------checks engine
					List engineList = tts.getEngines();

					for(Object strEngine : engineList){
						Log.d("tagg",strEngine.toString());
						if(strEngine.toString().equals("EngineInfo{name=com.google.android.tts}")){//check if google tts api engine is installed on device

							isGoogleAvaible = true;
						}
					}
					if(!isGoogleAvaible){
						Toast toast = Toast.makeText(cntx,
								"Google TTS Eksik...Lutfen yukleyin", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.BOTTOM, 0, 0);
						toast.show();
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setData(Uri.parse("market://details?id=com.google.android.tts"));
						cntx.startActivity(intent);//user should install google tts , if it is defaultly not installed
					}
					//---------------For missing data
					int code = tts.isLanguageAvailable(locale);
					if (code == -2 || code == -1) {
						try {


						Intent installIntent = new Intent();
						installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
						cntx.startActivity(installIntent);
						}
						catch (Exception e)
						{

						}
					}
				}

			}, "com.google.android.tts");

		}catch (Exception e)
		{

		}
		}
		else{
			Toast toast = Toast.makeText(cntx,
					"Ses destegi icin minumum Icecream Sandwich yuklu olmalı...", Toast.LENGTH_LONG);
			toast.setGravity(Gravity.BOTTOM, 0, 0);
			toast.show();
		}

	}

	private String converToTurkishCharacter(String text){


		text = text.replace("\u015f", "\u015f");
		text = text.replace("\u00e7", "\u00e7");
		text = text.replace("\u011f", "\u011f");
		text = text.replace("\u0131", "\u0131");
		text = text.replace("\u00fc", "\u00fc");
		text = text.replace("\u00f6", "\u00f6");
		// ----
		text = text.replace("\u011e", "\u011e");
		text = text.replace("\u0130", "\u0130");
		text = text.replace("\u00d6", "\u00d6");
		text = text.replace("\u00dc", "\u00dc");
		text = text.replace("\u015e", "\u015e");
		text = text.replace("\u00c7", "\u00c7");
		return  text;
	}

    
}