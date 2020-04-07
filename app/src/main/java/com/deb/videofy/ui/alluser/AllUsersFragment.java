package com.deb.videofy.ui.alluser;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deb.videofy.Adapter;
import com.deb.videofy.Adapter1;
import com.deb.videofy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class AllUsersFragment extends Fragment {
    Adapter1 mAdapter1;
    String mString;
    ArrayList<String> item;
    ArrayList<Integer> upld;
    ArrayList<Integer> dwnld;
    DatabaseReference root,local,store;
    FirebaseDatabase mFirebaseDatabase;
    Integer i;
    Integer m;
    private AllUsersViewModel mAllUsersViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        root = FirebaseDatabase.getInstance().getReference();
        local = root.child("user");
        store = root.child("User");

        upld = new ArrayList<>();
        item = new ArrayList<>();
        local.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    for(DataSnapshot dataSnapshot2:dataSnapshot1.getChildren())
                    {
                        if("Username".equals(dataSnapshot2.getKey())){
                            mString =(String) dataSnapshot2.getValue();
                            item.add(mString);
                        }
                        if(("Video".equals(dataSnapshot2.getKey()))){
                            if(dataSnapshot2.exists()) {
                                upld.add((int) dataSnapshot2.getChildrenCount());
                            }
                            else
                                upld.add(0);
                        }
                        if(("Audio".equals(dataSnapshot2.getKey()))){
                            if(dataSnapshot2.exists()) {
                                upld.add((int) dataSnapshot2.getChildrenCount());
                            }
                            else
                                upld.add(0);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        store.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        store.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    if(dataSnapshot1.getKey().equals("Video"))
                    {

                        upld.add((int)dataSnapshot1.getChildrenCount());
                        Log.d("Upload", String.valueOf(dataSnapshot1.getChildrenCount()));

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        item.add("Second User");
        item.add("Third User");
        item.add("Forth User");
        item.add("Five User");

//        upld = new ArrayList<>();

        upld.add(1321);
        upld.add(633);
        upld.add(978);
        upld.add(23);

        dwnld = new ArrayList<>();
        dwnld.add(453543);
        dwnld.add(53553);
        dwnld.add(6535);
        dwnld.add(973242);
        dwnld.add(2334224);



        mAllUsersViewModel =
                ViewModelProviders.of(this).get(AllUsersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_allusers, container, false);
        final RecyclerView mRecyclerView = root.findViewById(R.id.recyclerView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter1 = new Adapter1(getLayoutInflater(),item,upld,dwnld);
        mRecyclerView.setAdapter(mAdapter1);
        mAllUsersViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }
}
