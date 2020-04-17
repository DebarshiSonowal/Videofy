package com.deb.videofy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class videofile extends AppCompatActivity {
    Button selectfile,uploadfile;
    TextView notificatiom;
    EditText mEditText;
    StorageReference filepath,storageReference;
    Uri mUri;
    Spinner mSpinner;
    Boolean Admin;
    Date mDate = new Date();
    String uid,user,muid;
    ProgressDialog mProgressDialog;
    SimpleDateFormat sdf = new SimpleDateFormat("DD/MM/YY", Locale.getDefault());
    FirebaseStorage mStorage;
    DatabaseReference local;
    FirebaseDatabase mDatabase;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videofile);
        local = FirebaseDatabase.getInstance().getReference();
        final FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = mUser.getUid();
        Toast.makeText(this,uid,Toast.LENGTH_SHORT).show();

        arrayList = new ArrayList<String>();

        if(mUser.getUid().equals("OspHadu29BSdph2zUbr8jzVLOg22")){
            Admin = true;
        }
        else
            Admin = false;

        mStorage= FirebaseStorage.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        mSpinner = findViewById(R.id.spinner);
        selectfile = findViewById(R.id.selectbtn);
        mEditText = findViewById(R.id.filename);
        uploadfile = findViewById(R.id.upload);
        notificatiom = findViewById(R.id.notify);

        if(Admin)
        {
            mSpinner.setEnabled(true);
        }

        else
        {
            mSpinner.setEnabled(false);
            muid = uid;

        }
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user = parent.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(),user,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        local.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item:dataSnapshot.getChildren()){
                    for(DataSnapshot dataSnapshot1:item.getChildren())
                    {
                        if(dataSnapshot1.getKey().equals("Username"))
                        {
                            arrayList.add(dataSnapshot1.getValue().toString());
                            Log.d("uid",item.getKey().toString());

                        }
                    }

                }
                ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_dropdown_item,arrayList);
                mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(mAdapter);

                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        selectfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(videofile.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                {
                    selectVideo();
                }
                else
                    ActivityCompat.requestPermissions(videofile.this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},9);
            }
        });
        uploadfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(TextUtils.isEmpty(mEditText.getText())){
                   Toast.makeText(videofile.this,"Enter the file name",Toast.LENGTH_SHORT).show();
               }
               else
               {
                   if(mUri != null)
                   {
                       Log.d("Uploaded ",user);
                       local.child("user").addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           for(DataSnapshot item:dataSnapshot.getChildren()){
                               for(DataSnapshot dataSnapshot1:item.getChildren())
                               {
                                   Log.d("Uploaded ","first");
                                   if(dataSnapshot1.getKey().equals("Username"))
                                   {
                                       Log.d("Uploaded ","second");
                                       if(dataSnapshot1.getValue().equals(user))
                                       {
                                           Log.d("Uploaded ","");
                                           muid = item.getKey().toString();
                                           Log.d("Uploaded ",muid);
                                           Toast.makeText(getApplicationContext(),muid,Toast.LENGTH_SHORT).show();
                                           Log.d("Uploaded User agot",muid+"");
                                           uploadvideo(mUri);
                                       }

                                   }
                               }

                           }
                       }
                       @Override
                       public void onCancelled(@NonNull DatabaseError databaseError) {

                       }
                   });
//                       Log.d("Uploaded User agot",muid+"");
//                       uploadvideo(mUri);

                   }


                   else
                       Toast.makeText(videofile.this,"Please upload a file",Toast.LENGTH_SHORT).show();
               }
            }
        });

    }

    private void uploadvideo(Uri uri) {
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = mUser.getUid();
        if(!Admin)
            muid = uid;
        Context context;
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setProgress(0);
        mProgressDialog.show();


        final String filename =mEditText.getText().toString();
       storageReference = mStorage.getReference();
        filepath =  storageReference.child("Uploads").child("Video").child(muid).child(filename);
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!urlTask.isSuccessful());
                Uri downloadUrl = urlTask.getResult();
                final String url = String.valueOf(downloadUrl);
//                String url = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                DatabaseReference reference = mDatabase.getReference();
                if(Admin)
                {
                    reference.child("User").child(muid).child("Video").child(filename).setValue(url);

                }
                reference.child("User").child(muid).child("total") .child(filename).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(videofile.this,"File Successfully uploaded",Toast.LENGTH_SHORT).show();
                            Log.d("Uploaded User 1",muid);
                        }
                        else
                            Toast.makeText(videofile.this,"File not uploaded",Toast.LENGTH_SHORT).show();
                    }
                });
                reference.child("user").child(muid).child("Video") .child(filename).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(videofile.this,"File Successfully uploaded",Toast.LENGTH_SHORT).show();
                            Log.d("Uploaded user 2",muid);
                        }
                        else
                            Toast.makeText(videofile.this,"File not uploaded",Toast.LENGTH_SHORT).show();
                    }
                });
                reference.child("Total files").child(filename).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(videofile.this,"File Successfully uploaded",Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(videofile.this,"File not uploaded",Toast.LENGTH_SHORT).show();
                    }
                });
                reference.child("User").child(muid).child("Video") .child(filename).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(videofile.this,"File Successfully uploaded",Toast.LENGTH_SHORT).show();
                            Log.d("Uploaded User 3",muid);
                        }
                        else
                            Toast.makeText(videofile.this,"File not uploaded",Toast.LENGTH_SHORT).show();
                    }
                });
                finish();
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(videofile.this,"File not uploaded",Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    int currentProgress = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    mProgressDialog.setProgress(currentProgress);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 9 && grantResults[0] ==  PackageManager.PERMISSION_GRANTED)
        {
            selectVideo();

        }
        else
            Toast.makeText(videofile.this,"please give permission",Toast.LENGTH_SHORT).show();

    }

    private void selectVideo() {

        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 86 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mUri=data.getData();
            notificatiom.setText("A file is seleted "+data.getData().getLastPathSegment());
        }
        else
            Toast.makeText(videofile.this,"Please upload a file",Toast.LENGTH_SHORT).show();
    }

}
