package com.danny.dailynova.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.danny.dailynova.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class VIdeoUriTaskUtil extends AsyncTask<Uri,Void, Uri> {
    private Context context;
    private ImageView imageView;
    public VIdeoUriTaskUtil(Context context,ImageView imageView) {
        this.context = context;
        this.imageView=imageView;
    }

    @Override
    protected Uri doInBackground(Uri... uris) {
        Bitmap bitmap=null;
        Matrix matrix=null;
        try {
            InputStream is=context.getContentResolver().openInputStream(uris[0]);
            bitmap= BitmapFactory.decodeStream(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (bitmap==null)
        {
            bitmap= ThumbnailUtils.createVideoThumbnail(uris[0].getPath(), MediaStore.Images.Thumbnails.MINI_KIND);
            matrix=new Matrix();

        }
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    @Override
    protected void onPostExecute(Uri uri) {
        super.onPostExecute(uri);
        Glide.with(context).load(uri).placeholder(R.drawable.ic_image_black_24dp).into(imageView);
        imageView.setImageURI(uri);
    }
}
