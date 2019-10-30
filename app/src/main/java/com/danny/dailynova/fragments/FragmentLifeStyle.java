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
import com.danny.dailynova.adapters.LifestyleAdapter;
import com.danny.dailynova.builders.LifestyleBuilder;
import com.danny.dailynova.items.LifeStyleItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentLifeStyle extends Fragment {

    ProgressBar progressBar;
    RecyclerView recLifestyleList;
    ArrayList<LifeStyleItem> lifeStyleItems;
    LifestyleAdapter adapter;
    LifeStyleItem item;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_lifestyle,container,false);
        recLifestyleList=(RecyclerView)rootView.findViewById(R.id.rec_lifestyle_list);
        progressBar=(ProgressBar)rootView.findViewById(R.id.progress_in_fragment_lifestyle);
        loadData();
        return rootView;
    }

    private void loadData() {

        progressBar.setVisibility(View.VISIBLE);
        lifeStyleItems=new ArrayList<>();
        FirebaseFirestore.getInstance().collection("lifestyles").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()){
                    for (final QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                    {
                        try
                        {
                            progressBar.setVisibility(View.GONE);
                            HashMap lifeStyleMap = (HashMap)documentSnapshot.getData();

                            item = new LifestyleBuilder()
                                    .setLifeStyleTitle((String)lifeStyleMap.get("lifeStyleTitle"))
                                    .setLifestyleContent((String)lifeStyleMap.get("lifeStyleContent"))
                                    .setLifestyleSource((String)lifeStyleMap.get("lifestyleSource"))
                                    .setLifestyleUploadDate((String)lifeStyleMap.get("postedOn"))
                                    .setLifestyleImageUrl((String)lifeStyleMap.get("lifeStyleImageUrl"))
                                    .setLifestyleCategory((String)lifeStyleMap.get("lifeStyleCategory"))
                                    .build();


                            ((LifestyleAdapter)recLifestyleList.getAdapter()).updateAdapter(item);
                        }catch (Exception e)
                        {
                            Toast.makeText(getActivity(), "In load data for loop: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "No lifestyle found!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
     /*   FirebaseDatabase.getInstance().getReference().child("lifestyle").addValueEventListener(new ValueEventListener() {
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
                               progressBar.setVisibility(View.GONE);
                               HashMap lifeStyleMap = (HashMap)ds.getValue();

                               item = new LifestyleBuilder()
                                       .setLifeStyleTitle((String)lifeStyleMap.get("lifeStyleTitle"))
                                       .setLifestyleContent((String)lifeStyleMap.get("lifeStyleContent"))
                                       .setLifestyleSource((String)lifeStyleMap.get("lifestyleSource"))
                                       .setLifestyleUploadDate((String)lifeStyleMap.get("postedOn"))
                                       .setLifestyleImageUrl((String)lifeStyleMap.get("lifeStyleImageUrl"))
                                       .setLifestyleCategory((String)lifeStyleMap.get("lifeStyleCategory"))
                                       .build();


                               ((LifestyleAdapter)recLifestyleList.getAdapter()).updateAdapter(item);
                           }catch (Exception e)
                           {
                               Toast.makeText(getActivity(), "In load data for loop: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                           }
                       }
                   }else {
                       progressBar.setVisibility(View.GONE);
                       Toast.makeText(getActivity(), "No lifestyle found!", Toast.LENGTH_SHORT).show();
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


        adapter = new LifestyleAdapter(getActivity(), lifeStyleItems);
        LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recLifestyleList.setLayoutManager(localLinearLayoutManager);
        recLifestyleList.setHasFixedSize(true);
        recLifestyleList.setAdapter(adapter);
    }
}

