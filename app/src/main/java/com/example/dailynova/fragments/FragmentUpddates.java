package com.example.dailynova.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dailynova.R;
import com.example.dailynova.adapters.NewsAdapter;
import com.example.dailynova.items.NewsItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentUpddates extends Fragment {
    RecyclerView recNewsList;
    NewsAdapter adapter;
    NewsItem item;
    ArrayList<NewsItem> newsItems;
    ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_updates,container,false);
        recNewsList=(RecyclerView)rootView.findViewById(R.id.rec_updates_list);
        progressBar=(ProgressBar)rootView.findViewById(R.id.progress_in_fragment_updates);
        loadData();
        return rootView;
    }

    private void loadData() {
        progressBar.setVisibility(View.VISIBLE);
        newsItems=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("news").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                try
                {
                    for (DataSnapshot ds:dataSnapshot.getChildren())
                    {
                        try
                        {
                            progressBar.setVisibility(View.GONE);
                            HashMap lyricsMap = (HashMap)ds.getValue();

                            item = new NewsItem((String)lyricsMap.get("newsBody"), (String)lyricsMap.get("newsCategory"),
                                    (String)lyricsMap.get("newsTitle"),(String)lyricsMap.get("newsOwner")
                                    , (String) lyricsMap.get("postedOn"), (String) lyricsMap.get("newsImageUrl"));
                            ((NewsAdapter)recNewsList.getAdapter()).updateAdapter(item);
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


        adapter = new NewsAdapter(getActivity(), newsItems);
        LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager(getActivity(), 1, false);
        recNewsList.setLayoutManager(localLinearLayoutManager);
        recNewsList.setHasFixedSize(true);
        recNewsList.setAdapter(adapter);
    }
}
