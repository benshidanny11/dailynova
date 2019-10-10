package com.example.dailynova.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class DownloadTaskUtil extends AsyncTask<String,Void,Void> {

    Context context;
    ImageView imgDownload;
    ProgressBar progressBar;
    String fileType;

    public DownloadTaskUtil(Context context, ImageView imgDownload, ProgressBar progressBar, String fileType) {
        this.context = context;
        this.imgDownload = imgDownload;
        this.progressBar = progressBar;
        this.fileType = fileType;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        imgDownload.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(String... strings) {

       if (fileType.equals("video"))
       {
           DownloadManager downloadManager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
           Uri uri = Uri.parse(strings[0]);
           DownloadManager.Request request = new DownloadManager.Request(uri);
           request.setVisibleInDownloadsUi(true);        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
           request.setDestinationInExternalPublicDir(Environment.getRootDirectory().getAbsolutePath()+"/DailyNova/Videos", uri.getLastPathSegment());
           downloadManager.enqueue(request);
       }
       else {
           DownloadManager downloadManager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
           Uri uri = Uri.parse(strings[0]);
           DownloadManager.Request request = new DownloadManager.Request(uri);
           request.setVisibleInDownloadsUi(true);        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
           request.setDestinationInExternalPublicDir(Environment.getRootDirectory().getAbsolutePath()+"/DailyNova/audios", uri.getLastPathSegment());
           downloadManager.enqueue(request);

       }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if (fileType.equals("video")){
            imgDownload.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            Toast.makeText(context, "Video is downloaded", Toast.LENGTH_SHORT).show();
        }
        else {
            imgDownload.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            Toast.makeText(context, "Song is downloaded", Toast.LENGTH_SHORT).show();
        }
    }
}
