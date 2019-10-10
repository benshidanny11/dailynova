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
import com.example.dailynova.adapters.BusinessAdapter;
import com.example.dailynova.items.BusinessItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentBusiness extends Fragment {
    RecyclerView recBusinessList;
    BusinessAdapter businessAdapter;
    BusinessItem businessItem;
    ArrayList<BusinessItem> businessItems;
    ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_business,container,false);
        recBusinessList=(RecyclerView)rootView.findViewById(R.id.rec_business_list);
        progressBar=(ProgressBar)rootView.findViewById(R.id.progress_in_fragment_business);
        loadData();
        return rootView;
    }

    private void loadData() {
        progressBar.setVisibility(View.VISIBLE);
        businessItems=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("business").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists()){
                    try
                    {
                        for (DataSnapshot ds:dataSnapshot.getChildren())
                        {
                            try
                            {
                                progressBar.setVisibility(View.GONE);
                                HashMap lyricsMap = (HashMap)ds.getValue();

                                businessItem = new BusinessItem((String)lyricsMap.get("businessTitle"), (String)lyricsMap.get("businessContent"),
                                        (String)lyricsMap.get("businessImageUrl"),(String)lyricsMap.get("businessCategory")
                                        , (String) lyricsMap.get("businessSource"), (String) lyricsMap.get("businessUploadDate"));
                                ((BusinessAdapter)recBusinessList.getAdapter()).updateAdapter(businessItem);
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
                }else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "No data found!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        businessAdapter = new BusinessAdapter(getActivity(), businessItems);
        LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager(getActivity(), 1, false);
        recBusinessList.setLayoutManager(localLinearLayoutManager);
        recBusinessList.setHasFixedSize(true);
        recBusinessList.setAdapter(businessAdapter);
    }
}
