package istanbuli.a1c2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import istanbuli.a1c2.tabmenu.TopBarActivity;

public class AboutActivity extends AppCompatActivity {
    ImageView websiteLink,phoneCall,emailMsg,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        back=findViewById(R.id.back);
        websiteLink=findViewById(R.id.websiteLink);
        phoneCall=findViewById(R.id.phoneCall);
        emailMsg=findViewById(R.id.emailMsg);
        websiteLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.istanbulibedoon.ir"));
                startActivity(browserIntent);
            }
        });
        phoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "+989382240484"));
                startActivities(new Intent[]{intent});
            }
        });
        emailMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "enbehzadi@gmail.com"));
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

}
