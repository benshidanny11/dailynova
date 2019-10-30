package com.danny.dailynova.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.danny.dailynova.R;
import com.danny.dailynova.adapters.VideoAdapter;
import com.danny.dailynova.items.VideoItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentVideo extends Fragment {
    ProgressBar simpleProgressBar;
    VideoItem videoItem;
    VideoAdapter videoAdapter;
    ArrayList<VideoItem> videoItems;
    RecyclerView recVideoList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_videos,container,false);
        simpleProgressBar=(ProgressBar)rootView.findViewById(R.id.progres_in_fragment_video);
        recVideoList=(RecyclerView)rootView.findViewById(R.id.rec_video_list);

        getData();
        return rootView;
    }



    public void getData(){
        simpleProgressBar.setVisibility(View.VISIBLE);
        videoItems=new ArrayList<>();
        FirebaseFirestore.getInstance().collection("videos").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()){
                    simpleProgressBar.setVisibility(View.GONE);
                    for (DocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                    {
                        try
                        {

                            HashMap lyricsMap = (HashMap)documentSnapshot.getData();

                            videoItem = new VideoItem((String)lyricsMap.get("videoName"), (String)lyricsMap.get("videoDuration"),
                                    (String)lyricsMap.get("videoSource"), (String)lyricsMap.get("videoImageUri")
                                    , (String) lyricsMap.get("videoUrl"), (String) lyricsMap.get("videoUploadDate"),(Long)lyricsMap.get("videoLikes"),(Long) lyricsMap.get("videoDownloads"));
                            ((VideoAdapter)recVideoList.getAdapter()).updateAdapter(videoItem);
                        }catch (Exception e)
                        {
                            Toast.makeText(getActivity(), "In load data for loop: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    simpleProgressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "No videos found!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
//        FirebaseDatabase.getInstance().getReference().child("videos").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
//            {
//                try
//                {
//                 if (dataSnapshot.exists()){
//                     for (DataSnapshot ds:dataSnapshot.getChildren())
//                     {
//                         try
//                         {
//                             simpleProgressBar.setVisibility(View.GONE);
//                             HashMap lyricsMap = (HashMap)ds.getValue();
//
//                             videoItem = new VideoItem((String)lyricsMap.get("videoName"), (String)lyricsMap.get("videoDuration"),
//                                     (String)lyricsMap.get("videoSource"), (String)lyricsMap.get("videoImageUri")
//                                     , (String) lyricsMap.get("videoUrl"), (String) lyricsMap.get("videoUploadDate"),(Long)lyricsMap.get("videoLikes"),(Long) lyricsMap.get("videoDownloads"));
//                             ((VideoAdapter)recVideoList.getAdapter()).updateAdapter(videoItem);
//                         }catch (Exception e)
//                         {
//                             Toast.makeText(getActivity(), "In load data for loop: "+e.getMessage(), Toast.LENGTH_SHORT).show();
//                         }
//                     }
//                 }else {
//                     simpleProgressBar.setVisibility(View.GONE);
//                     Toast.makeText(getActivity(), "No videos found", Toast.LENGTH_SHORT).show();
//                 }
//                }
//                catch (Exception e)
//                {
//                    Toast.makeText(getActivity(), "In load data: "+e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        videoAdapter = new VideoAdapter(getActivity(), videoItems);
        LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recVideoList.setLayoutManager(localLinearLayoutManager);
        recVideoList.setHasFixedSize(true);
        recVideoList.setAdapter(videoAdapter);
    }
}
