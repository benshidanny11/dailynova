package com.danny.dailynova;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DisplayNewsActivity extends AppCompatActivity {

    TextView txtNewsOwner,txtNewsTItle,txtNewsUploadDate,txtNewsBody;
    ImageView imgNewsImage;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_news);
        txtNewsOwner=(TextView)findViewById(R.id.txt_news_owner_in_display_news);
        txtNewsBody=(TextView)findViewById(R.id.txt_news_body_in_display_news);
        txtNewsTItle=(TextView)findViewById(R.id.txt_news_title_in_display_news);
        txtNewsUploadDate=(TextView)findViewById(R.id.txt_upload_date_in_display_news);
        imgNewsImage=(ImageView)findViewById(R.id.img_news_image_in_display_news);
        bundle=getIntent().getExtras();
        if (bundle!=null){
            txtNewsUploadDate.setText(bundle.getString("news_upload_date"));
            txtNewsBody.setText(bundle.getString("news_body"));
            txtNewsTItle.setText(bundle.getString("news_title"));
            txtNewsOwner.setText(bundle.getString("news_owner"));
            Glide.with(this).load(bundle.getString("news_image")).into(imgNewsImage);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
