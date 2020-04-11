package com.deb.videofy.ui.myprofile;

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

import com.deb.videofy.R;
import com.deb.videofy.user;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


public class MyProfileFragment extends Fragment {

    DatabaseReference toor;
    String aa;
    String name;
    String user,email,uid;

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Data", name+"");
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid =  currentFirebaseUser.getUid();
        email = currentFirebaseUser.getEmail();
        toor = FirebaseDatabase.getInstance().getReference().child("user");
        Toast.makeText(getContext(),uid,Toast.LENGTH_SHORT).show();
        View root = inflater.inflate(R.layout.fragment_myprofile, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
        final  TextView usnm = root.findViewById(R.id.usernm);
        final  TextView emlid = root.findViewById(R.id.emailid);
//        FirebaseDatabase.getInstance().getReference().child("qwert").child("1d1r2f2").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                if(dataSnapshot.getKey().equals("1d1r2f2")){
////                    for(DataSnapshot item: dataSnapshot.getChildren()){
////                        aa = (String)   item.child("asdf").getValue();
////                    }
////
////                }
//                name = (String) dataSnapshot.child("asdf").getValue();
////                String date = (String) dataSnapshot.child("date").getValue();
////                Log.d("Data", name+"");
//                usnm.setText(name);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

//        Log.d("Data", name+"");

//        FirebaseDatabase.getInstance().getReference("user")
//                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                user = (String) dataSnapshot.child("Username").getValue();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        toor.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                collectUsernames((Map<String,Object>) dataSnapshot.getValue());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });



        toor.addValueEventListener(new ValueEventListener() {
                                       @Override
                                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                           for (DataSnapshot item : dataSnapshot.getChildren()) {
//                                               if (item.getKey().equals("1xfcI3rEV8h7f6QHefIABl7Fpwq2")) {
                                                   for (DataSnapshot dataSnapshot1 : item.getChildren()) {
                                                       if (dataSnapshot1.getKey().equals("Email")) {
                                                           email = (String) dataSnapshot1.getValue();
                                                           emlid.setText(email);
                                                       }
                                                       if (dataSnapshot1.getKey().equals("Username")) {
                                                           user = (String) dataSnapshot1.getValue();
                                                           usnm.setText(user);
                                                       }
                                                   }
                                               }

//                                           }
//                user = (String) dataSnapshot.child("Username").getValue();
//                email = (String) dataSnapshot.child("Email").getValue();
//            }
                                       }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return root;
    }

//    private void collectUsernames(Map<String, Object>users) {
//        ArrayList<String> username = new ArrayList<>();
//
//        //iterate through each user, ignoring their UID
//        for (Map.Entry<String, Object> entry : users.entrySet()){
//
//            //Get user map
//            Map singleUser = (Map) entry.getValue();
//            //Get phone field and append to list
//            username.add((String) singleUser.get("Username"));
//        }
//    }
}
