package com.example.dailynova;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DisplayLifeStyleActivity extends AppCompatActivity {
    TextView lifestyleTitle,lifestyleSource,lifestyleContent,lifestyleCategory;
    ImageView imgLifestyle;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_life_style);
        lifestyleTitle=(TextView)findViewById(R.id.txt_lifestyle_title_in_display_lifestyle);
        lifestyleSource=(TextView)findViewById(R.id.txt_lifestyle_source_in_display_life_style);
        lifestyleContent=(TextView)findViewById(R.id.txt_lifestyle_content_in_display_lifestyle);
        lifestyleCategory=(TextView)findViewById(R.id.txt_lifestyle_category_in_display_lifestyle);
        imgLifestyle=(ImageView)findViewById(R.id.img_lifestyle_image_in_display_lifestyle);
        bundle=getIntent().getExtras();
        if (bundle!=null){
            lifestyleTitle.setText(bundle.getString("lifestyle_title"));
            lifestyleSource.setText(bundle.getString("lifestyle_source"));
            lifestyleContent.setText(bundle.getString("lifestyle_body"));
            lifestyleCategory.setText(bundle.getString("lifestyle_category"));
            Glide.with(this).load(bundle.getString("lifestyle_image")).into(imgLifestyle);
        }
    }
}
