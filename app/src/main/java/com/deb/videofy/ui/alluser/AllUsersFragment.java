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
    Boolean flag = true;
    Integer m;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        root = FirebaseDatabase.getInstance().getReference();
        local = root.child("user");
        store = root.child("User");

        upld = new ArrayList<>();
        item = new ArrayList<>();
        dwnld = new ArrayList<>();

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
//        store.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
//                {
//                    if(dataSnapshot1.getKey().equals("Video"))
//                    {
//
//                        upld.add((int)dataSnapshot1.getChildrenCount());
//                        Log.d("Upload", String.valueOf(dataSnapshot1.getChildrenCount()));
//
//                    }
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });




//        upld = new ArrayList<>();




        dwnld.add(453543);
        dwnld.add(53553);
        dwnld.add(6535);
        dwnld.add(973242);
        dwnld.add(2334224);




        View root = inflater.inflate(R.layout.fragment_allusers, container, false);
        final RecyclerView mRecyclerView = root.findViewById(R.id.recyclerView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        upld.clear();
        local.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    Log.d("Flag Value 0", "" + dataSnapshot1.getKey());
                    for(DataSnapshot dataSnapshot2:dataSnapshot1.getChildren())
                    {
                            if ("Username".equals(dataSnapshot2.getKey())) {
                                mString = (String) dataSnapshot2.getValue();
                                Log.d("Flag Value 1", "Username" + dataSnapshot2.getValue());
                                item.add(mString);
                            }
                            if (("Video".equals(dataSnapshot2.getKey()))) {
                                    Log.d("Flag Value 2", "Video  " + dataSnapshot2.getChildrenCount());
                                    upld.add((int) dataSnapshot2.getChildrenCount());

                            }

//                            if(!dataSnapshot1.hasChild("Video")){
//                                upld.add(0);
//                              Log.d("Flag Value 3", "No  0");
//                            }
                        }
                    if(item.size() != upld.size())
                    {
                        Log.d("Flag Value 3", "");
                        upld.add(0);
                    }
                  }
            mAdapter1 = new Adapter1(getLayoutInflater(),item,upld,dwnld);
                mRecyclerView.setAdapter(mAdapter1);
            };


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }
}
