package istanbuli.a1c2.behzadi.buy;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import istanbuli.a1c2.R;
import istanbuli.a1c2.behzadi.content.myshareprefrence;
import istanbuli.a1c2.behzadi.farsi.TFace;
import istanbuli.a1c2.behzadi.util.IabHelper;
import istanbuli.a1c2.behzadi.util.IabResult;
import istanbuli.a1c2.behzadi.util.Purchase;
import istanbuli.a1c2.functions.functions;
import istanbuli.a1c2.intro.Splash_screen;


public class BuyProduct extends Activity{

	//Buy Modes
	public	static final int MODE_FULL_VER=2;

	// Debug tag, for logging
	static final String TAG = "Debug";
	// SKUs for our products
	private static String Product_Key="istanbulibedoona1";
	// (arbitrary) request code for the purchase flow
	static final int RC_REQUEST = 0;
	// The helper object
	IabHelper mHelper;
	String base64EncodedPublicKey;
	//Variables
	IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener;
	int mode;
	String sku_fo_buy;
	Boolean conected=false;
	Bundle reciveb;
	String statasusback;
	//Views
	Button		btn_full;
	//=========================================================================================

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);



		setContentView(R.layout.dlg_show_buy);

		Intent getIntent = getIntent();
		String value = getIntent.getStringExtra("product");
		Product_Key=value;
		mode=MODE_FULL_VER;
		sku_fo_buy=Product_Key;

		FindViews();
		SetListeners();
		Prepare();
		SetListeners_Buy_Object();

		try {
			Prepare_Buy_Object();
			conected=true;

		} catch (Exception e) {
			Toast.makeText(BuyProduct.this, "هنگام اتصال به برنامه کافه  بازار خطایی رخ داده است از نصب آخرین نسخه کافه ه بازار بر روی دستگاهتان مطمئن شوید",Toast.LENGTH_LONG).show();
			finish();


		}

	}
	//=========================================================================================
	private void SetListeners_Buy_Object() {
		//Listeners for After Shop
		mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
			public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
				if (result.isFailure()) {
					Log.d(TAG, "Error purchasing: " + result);
					Toast.makeText(BuyProduct.this, "در خرید مشکلی پیش آمده:-(",Toast.LENGTH_LONG).show();

					return;
				}
				else if (purchase.getSku().equals(sku_fo_buy)) {
					// give user access to premium content and update the UI
					Toast.makeText(BuyProduct.this, "خرید با موفقیت انجام شد برنامه مجدد اجرا میشود",Toast.LENGTH_LONG).show();


					functions fnc = new functions(BuyProduct.this,BuyProduct.this);
					myshareprefrence mysh=new myshareprefrence(BuyProduct.this);
					//Toast.makeText(baseContext, baseContext.resources.getString(R.string.pay_successfully), Toast.LENGTH_LONG).show()

					if(Product_Key.equalsIgnoreCase("C2"))
					{
						Product_Key="C1+";
					}
					Map<String, String> params = new HashMap<>();
					params.put("method", "buy");
					params.put("UserName",mysh.getUser());
					params.put("Pruduct",Product_Key);
					params.put("Price","100000");
					params.put("Id_Price",purchase.getOriginalJson());
					fnc.getRequestUpdate_shop(BuyProduct.this,params);


				}
			}



		};

	}
	//=========================================================================================
	private void Prepare_Buy_Object() {
		base64EncodedPublicKey = "MIHNMA0GCSqGSIb3DQEBAQUAA4G7ADCBtwKBrwDmdx00mK8CoLUUczPi1jhaULL9EN4FZoBO7CCqb7aFeFFChyx6Yt6brpO1Lj6gL1myJK6Uo4S3Rd3CECUPTyUY6ajgsXzHO85wJGyt+oymYU5ArNWMLlZ+O9JGbpm9yMtVdtxrKLKPelR29DMTxiZVp4F13ySDGTvru5AGupVBxPDHgmsbxz87C4ReljYgkQyMK5wtq09T+2E4fEA65Vhp7kmPaYVm3SJ6f6JwMG0CAwEAAQ==";

		;
		// You can find it in your Bazaar console, in the Dealers section.
		// It is recommended to add more security than just pasting it in your source code;
		mHelper = new IabHelper(this, base64EncodedPublicKey);
		Log.d(TAG, "Starting setup.");
		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			public void onIabSetupFinished(IabResult result) {
				Log.d(TAG, "Setup finished.");

				if (!result.isSuccess()) {
					// Oh noes, there was a problem.
					Log.d(TAG, "Problem setting up In-app Billing: " + result);
					Toast.makeText(BuyProduct.this, "اتصال به برنامه  کافه  بازار خطایی رخ داده است از نصب آخرین نسخه کافه بازار بر روی دستگاهتان مطمئن شوید ",Toast.LENGTH_SHORT).show();

				}
				// Hooray, IAB is fully set up!
			}
		});
	}
	//=========================================================================================
	private void FindViews() {
		TFace tf=new TFace(this);
		btn_full=(Button)findViewById(R.id.dlg_buy_btn1);btn_full.setTypeface(tf.byekan);
		TextView tvTitle =(TextView)findViewById(R.id.tv_title_buy);tvTitle.setTypeface(tf.byekan);;
		TextView detailMain =(TextView)findViewById(R.id.tv_detailes);
		detailMain.setTypeface(tf.byekan);
		tvTitle.setText(Product_Key);
		detailMain.setText(getResources().getString(R.string.details_min));
	}


	//=========================================================================================
	private void SetListeners() {
		btn_full.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				mode=MODE_FULL_VER;
				sku_fo_buy=Product_Key;
				try {
					mHelper.launchPurchaseFlow(BuyProduct.this, Product_Key, RC_REQUEST, mPurchaseFinishedListener, "payload-string");
					//getPrice();
				} catch (Exception e) {
					Toast.makeText(BuyProduct.this, "اتصال به برنامه  کافه  بازار خطایی رخ داده است از نصب آخرین نسخه کافه بازار بر روی دستگاهتان مطمئن شوید ",Toast.LENGTH_SHORT).show();

				}

			}
		});

	}
	//=========================================================================================
	private void Prepare() {
		SaveBuy sa=new SaveBuy(this);
		Boolean full=sa.get();



	}


	//=========================================================================================
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);

		// Pass on the activity result to the helper for handling
		if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
			super.onActivityResult(requestCode, resultCode, data);
		} else {
			Log.d(TAG, "onActivityResult handled by IABUtil.");
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(!conected) return;
		if (mHelper != null) mHelper.dispose();
		mHelper = null;
	}

}
