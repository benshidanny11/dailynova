package com.example.dailynova.adapters;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.dailynova.R;
import com.example.dailynova.utils.VIdeoUriTaskUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.FileHolder> {
   private ArrayList<File> files;
   private Context context;
   private VIdeoUriTaskUtil taskUtil;

    public FilesAdapter(ArrayList<File> files, Context context) {
        this.files = files;
        this.context = context;
    }

    @NonNull
    @Override
    public FileHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_file_list,parent,false);
        return new FileHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FileHolder fileHolder, final int position) {
              if (getFileType(files.get(position)).equals("video")){
                  taskUtil=new VIdeoUriTaskUtil(context,fileHolder.imgFilePhoto);
                  taskUtil.execute(Uri.fromFile(files.get(position)));
                  //Glide.with(context).load(uriFromVideo(Uri.fromFile(files.get(position)))).placeholder(R.drawable.ic_image_black_24dp).into(fileHolder.imgFilePhoto);
                  fileHolder.imgVideoPhoto.setVisibility(View.VISIBLE);

              }else {
                  fileHolder.imgVideoPhoto.setVisibility(View.GONE);
                  Glide.with(context).load(Uri.fromFile(files.get(position))).placeholder(R.drawable.ic_image_black_24dp).into(fileHolder.imgFilePhoto);
              }
        fileHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                final File toImage=new File( Environment.getExternalStorageDirectory()+"/DailyNova/Media/WhatsApp Images");
                final File toVideo=new File( Environment.getExternalStorageDirectory()+"/DailyNova/Media/WhatsApp Video");
                if (!toImage.exists())
                {
                    toImage.mkdirs();

                }
                if (!toVideo.exists()){
                    toVideo.mkdir();
                }
                if (getFileType(files.get(position)).equals("video")){
                   Thread t=new Thread(new Runnable() {
                       @Override
                       public void run() {
                           try {
                               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                       Files.copy(
                                               new File(files.get(position).getPath()).toPath(),new File(toVideo+"/"+ String.valueOf(new Date().getTime())+".mp4").toPath(),
                                               StandardCopyOption.COPY_ATTRIBUTES,
                                               StandardCopyOption.REPLACE_EXISTING);
                                   }
                               }
                               galleryAddFile("WhatsApp Video");
                               Looper.prepare();
                               Toast.makeText(context, "Saved to: "+toVideo, Toast.LENGTH_SHORT).show();
                               Looper.loop();
                           } catch (IOException e) {
                               System.out.print(e.getStackTrace());
                               Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                               e.printStackTrace();
                           }
                       }
                   });
                   t.start();
                }
                else {
                    Thread t=new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        Files.copy(
                                                new File(files.get(position).getPath()).toPath(),new File(toImage+"/"+ String.valueOf(new Date().getTime())+".jpg").toPath(),
                                                StandardCopyOption.COPY_ATTRIBUTES,
                                                StandardCopyOption.REPLACE_EXISTING);
                                    }
                                }
                                galleryAddFile("WhatsApp Images");
                                Looper.prepare();
                                Toast.makeText(context, "Saved to: "+toImage, Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            } catch (IOException e) {
                                System.out.print(e.getStackTrace());
                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    });
                    t.start();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    class FileHolder extends RecyclerView.ViewHolder
    {
       ImageView imgFilePhoto,imgVideoPhoto;
       RelativeLayout lytImages;
        public FileHolder(@NonNull View itemView) {
            super(itemView);
            imgFilePhoto=(ImageView)itemView.findViewById(R.id.img_file_photo_in_row);
            imgVideoPhoto=(ImageView)itemView.findViewById(R.id.img_video_icon_in_row);
            lytImages=(RelativeLayout)itemView.findViewById(R.id.lyt_images);
        }
    }
    private Uri uriFromVideo(Uri uri)
    {
        Bitmap bitmap=null;
        Matrix matrix=null;
        try {
            InputStream is=context.getContentResolver().openInputStream(uri);
            bitmap= BitmapFactory.decodeStream(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (bitmap==null)
        {
            bitmap= ThumbnailUtils.createVideoThumbnail(uri.getPath(), MediaStore.Images.Thumbnails.MINI_KIND);
            matrix=new Matrix();

        }
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
       return Uri.parse(path);
    }

    private String getFileType(File file){
        String ext = file.getName().substring(file.getName().indexOf(".") + 1);
        String type="";
        if (ext.equalsIgnoreCase("mp4")){
            type="video";
        }
        else {
            type = "photo";
        }
        return type;
    }

    private void galleryAddFile(String fileTail) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            final Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File f = new File("file://"+ Environment.getExternalStorageDirectory()+"/DailyNova/Media/"+fileTail);
            scanIntent.setData(Uri.fromFile(f));
            context.sendBroadcast(scanIntent);
        } else {
            final Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory()));
            context.sendBroadcast(intent);
        }




    }
}
