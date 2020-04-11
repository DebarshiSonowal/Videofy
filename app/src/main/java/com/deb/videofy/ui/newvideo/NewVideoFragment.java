package com.deb.videofy.ui.newvideo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deb.videofy.Adapter;
import com.deb.videofy.Home;
import com.deb.videofy.R;
import com.deb.videofy.audiofile;
import com.deb.videofy.dialogbox;
import com.deb.videofy.videofile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class NewVideoFragment extends Fragment implements dialogbox.dialogboxlistener {
    RecyclerView mRecyclerView;
    Adapter mAdapter;

    DatabaseReference mReference;
    FirebaseUser mUser;
    ArrayList<String> item;
    ArrayList<String> file;
    String mButton,uid;
    Boolean Admin;
    Boolean flag = false;
    DatabaseReference mDatabaseReference;
    @Override
    public void onStart() {
        super.onStart();

    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        item = new ArrayList<>();
        file = new ArrayList<>();
        mButton = "Download";
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = mUser.getUid();

        if(mUser.getUid().equals("OspHadu29BSdph2zUbr8jzVLOg22")){
            Admin = true;
        }
        else
            Admin = false;

//        item.add("Second Card View");
//        item.add("Third Card View");
//        item.add("Forth Card View");
//        item.add("Five Card View");
//        mButton = "Download";


        View root = inflater.inflate(R.layout.fragment_newvideos, container, false);
        final RecyclerView mRecyclerView = root.findViewById(R.id.newrecycle);
        final CardView mCard = root.findViewById(R.id.addbtn);
        final CardView upcard = root.findViewById(R.id.uploadedbtn);
        final  CardView downcard = root.findViewById(R.id.downloadebtn);
        final Button down = root.findViewById(R.id.button2);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        mAdapter = new Adapter(getContext(),item,mButton);
//        mRecyclerView.setAdapter(mAdapter);
        mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();

            }
        });
        upcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        downcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        if(Admin)
            {
                mDatabaseReference.child("Total files").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                        {
                            item.add(dataSnapshot1.getKey().toString());
                            file.add(dataSnapshot1.getValue().toString());
                        }
                        mAdapter = new Adapter(getContext(),item,mButton,file,uid);
                        mRecyclerView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }else {
                mDatabaseReference.child("User").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                        {
                            if(dataSnapshot1.getKey().equals(uid))
                            {
                                for(DataSnapshot dataSnapshot2:dataSnapshot1.getChildren())
                                {
                                    if(dataSnapshot2.getKey().equals("total")){
                                        for(DataSnapshot dataSnapshot3:dataSnapshot2.getChildren())
                                        {
                                            item.add(dataSnapshot3.getKey());
                                            file.add(dataSnapshot3.getValue().toString());
                                        }

                                    }
                                }
                            }
                            mAdapter = new Adapter(getContext(),item,mButton,file,uid);
                            mRecyclerView.setAdapter(mAdapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        }
//    down.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//        }
//    });


        return root;
    }
    private String niceLink (String filename,String uid1){
        String link;
        // Points to the root reference
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference linkRef = storageRef.child("Upload").child("Video").child(uid1).child(filename);
        link = linkRef.getDownloadUrl().toString();
        return link;
    }
    private void openDialog() {
        dialogbox mdialog = new dialogbox();
        mdialog.show(getFragmentManager(),"example diaog");
    }

    @Override
    public void getInput(Boolean temp) {
            flag = temp;
    }
}
