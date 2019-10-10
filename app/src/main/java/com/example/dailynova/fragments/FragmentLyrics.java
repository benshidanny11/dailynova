package com.example.dailynova.fragments;

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

import com.example.dailynova.R;
import com.example.dailynova.adapters.LyricsAdapter;
import com.example.dailynova.items.LyricsItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentLyrics extends Fragment {
    ProgressBar simpleProgressBar;
    LyricsItem lyrics;
    LyricsAdapter lyricsAdapter;
    ArrayList<LyricsItem> lyricsItems;
    RecyclerView recLyricsList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_lyrics,container,false);
        simpleProgressBar=(ProgressBar) rootView.findViewById(R.id.progress_in_fragment_lyrics);
        recLyricsList=(RecyclerView)rootView.findViewById(R.id.rec_lyrics_list);
        getData();
        return rootView;
    }
    public void getData(){
       simpleProgressBar.setVisibility(View.VISIBLE);
       lyricsItems=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("songs").child("lyrics_songs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                try
                {
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
                }
                catch (Exception e)
                {
                    Toast.makeText(getActivity(), "In load data: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        lyricsAdapter = new LyricsAdapter(getActivity(), lyricsItems);
        LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager(getActivity(), 1, false);
        recLyricsList.setLayoutManager(localLinearLayoutManager);
        recLyricsList.setHasFixedSize(true);
        recLyricsList.setAdapter(lyricsAdapter);
    }
}
