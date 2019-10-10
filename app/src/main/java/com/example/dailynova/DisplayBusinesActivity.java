package com.example.dailynova;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dailynova.utils.ScreenSizeUtil;

public class DisplayBusinesActivity extends AppCompatActivity {
   TextView txtBusinessContent,txtBusinessTitle,txtBusinessCategory,txtBusinessSource,txtBusinessReference;
   ImageView imgBusinessImage;
   Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_busines);
        //Views Instantiation
        txtBusinessContent=(TextView)findViewById(R.id.txt_business_content_in_display_business);
        txtBusinessTitle=(TextView)findViewById(R.id.txt_business_title_in_display_business);
        txtBusinessCategory=(TextView)findViewById(R.id.txt_business_category_in_display_business);
        txtBusinessSource=(TextView)findViewById(R.id.txt_business_source_in_display_business);
        txtBusinessReference=(TextView)findViewById(R.id.txt_business_reference_in_display_business);
        imgBusinessImage=(ImageView)findViewById(R.id.img_business_image_in_display_business);
        imgBusinessImage.setMaxHeight(ScreenSizeUtil.getScreenWidth(this));
        bundle=getIntent().getExtras();
        //Assign Data to views
        assignData();

    }

    private void assignData() {

        if (bundle!=null){
            Glide.with(this).load(bundle.getString("businessImageUrl")).into(imgBusinessImage);
            txtBusinessTitle.setText(bundle.getString("businessTitle"));
            txtBusinessContent.setText(bundle.getString("businessContent"));
            txtBusinessSource.setText(bundle.getString("businessSource"));
            txtBusinessCategory.setText(bundle.getString("businessCategory"));
            txtBusinessReference.setText("Read more on "+bundle.getString("businessSource"));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
