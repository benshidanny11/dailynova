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
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.danny.dailynova.R;
import com.danny.dailynova.adapters.LyricsAdapter;
import com.danny.dailynova.items.LyricsItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentLyrics extends Fragment {
    private ProgressBar simpleProgressBar;
    private LyricsItem lyrics;
    private LyricsAdapter lyricsAdapter;
    private ArrayList<LyricsItem> lyricsItems;
    private RecyclerView recLyricsList;
    private DocumentSnapshot lastVisible;
    private boolean isScrolling,isLastItemReached;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_lyrics,container,false);
        simpleProgressBar=(ProgressBar) rootView.findViewById(R.id.progress_in_fragment_lyrics);
        recLyricsList=(RecyclerView)rootView.findViewById(R.id.rec_lyrics_list);
        getData();
        return rootView;
    }
    private void getData(){
       simpleProgressBar.setVisibility(View.VISIBLE);
       lyricsItems=new ArrayList<>();
        Query query=FirebaseFirestore.getInstance().collection("lyrics").orderBy("currentTimeStamp", Query.Direction.ASCENDING).limit(5);
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()){
                    simpleProgressBar.setVisibility(View.GONE);
                    for (DocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                    {
                        try
                        {

                            HashMap lyricsMap = (HashMap)documentSnapshot.getData();

                            lyrics = new LyricsItem((String)lyricsMap.get("songName"), (String)lyricsMap.get("artistName"),
                                    (String)lyricsMap.get("artistImageUri"), (String)lyricsMap.get("songLyrics")
                                    , (String) lyricsMap.get("songUri"), (String) lyricsMap.get("postedOn"));
                            ((LyricsAdapter)recLyricsList.getAdapter()).updateAdapter(lyrics);
                        }catch (Exception e)
                        {
                            Toast.makeText(getActivity(), "In load data for loop: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    simpleProgressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "No data found!", Toast.LENGTH_SHORT).show();
                }

                lyricsAdapter = new LyricsAdapter(getActivity(), lyricsItems);
                final LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                recLyricsList.setLayoutManager(localLinearLayoutManager);
                recLyricsList.setHasFixedSize(true);
                recLyricsList.setAdapter(lyricsAdapter);

                lastVisible=queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() -1);

                RecyclerView.OnScrollListener onScrollListener=new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if (newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                            isScrolling=true;
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        int firstVisibleItem=localLinearLayoutManager.findFirstVisibleItemPosition();
                        int visibleItemCount=localLinearLayoutManager.getChildCount();
                        int totalItemCount=localLinearLayoutManager.getItemCount();
                        if (isScrolling&&(firstVisibleItem+visibleItemCount==totalItemCount)&&isLastItemReached){
                           isScrolling=false;
                            Query query=FirebaseFirestore.getInstance().collection("lyrics")
                                    .orderBy("currentTimeStamp", Query.Direction.ASCENDING)
                                    .startAfter(lastVisible)
                                    .limit(5);
                            query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    for (DocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                                    {
                                        try
                                        {

                                            HashMap lyricsMap = (HashMap)documentSnapshot.getData();

                                            lyrics = new LyricsItem((String)lyricsMap.get("songName"), (String)lyricsMap.get("artistName"),
                                                    (String)lyricsMap.get("artistImageUri"), (String)lyricsMap.get("songLyrics")
                                                    , (String) lyricsMap.get("songUri"), (String) lyricsMap.get("postedOn"));
                                            ((LyricsAdapter)recLyricsList.getAdapter()).updateAdapter(lyrics);
                                        }catch (Exception e)
                                        {
                                            Toast.makeText(getActivity(), "In load data for loop: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                    lyricsAdapter.notifyDataSetChanged();
                                    if (queryDocumentSnapshots.size()<5){
                                       isLastItemReached=false;
                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                        }
                    }
                };
                recLyricsList.addOnScrollListener(onScrollListener);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }

        });
   /*     FirebaseDatabase.getInstance().getReference().child("songs").child("lyrics_songs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                try
                {
                   if (dataSnapshot.exists()){
                       for (DataSnapshot ds:dataSnapshot.getChildren())
                       {
                           try
                           {
                               simpleProgressBar.setVisibility(View.GONE);
                               HashMap lyricsMap = (HashMap)ds.getValue();

                               lyrics = new LyricsItem((String)lyricsMap.get("songName"), (String)lyricsMap.get("artistName"),
                                       (String)lyricsMap.get("artistImageUri"), (String)lyricsMap.get("songLyrics")
                                       , (String) lyricsMap.get("songUri"), (String) lyricsMap.get("postedOn"));
                               ((LyricsAdapter)recLyricsList.getAdapter()).updateAdapter(lyrics);
                           }catch (Exception e)
                           {
                               Toast.makeText(getActivity(), "In load data for loop: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                           }
                       }
                   }else{
                       simpleProgressBar.setVisibility(View.GONE);
                       Toast.makeText(getActivity(), "No lyrics found!", Toast.LENGTH_SHORT).show();
                   }
                }
                catch (Exception e)
                {
                    Toast.makeText(getActivity(), "In load data: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/



    }
}
