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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class videofile extends AppCompatActivity {
    Button selectfile,uploadfile;
    TextView notificatiom;
    Uri mUri;
    Date mDate = new Date();
    String uid;
    ProgressDialog mProgressDialog;
    SimpleDateFormat sdf = new SimpleDateFormat("DD/MM/YY", Locale.getDefault());
    FirebaseStorage mStorage;
    FirebaseDatabase mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videofile);
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = mUser.getUid();
        Toast.makeText(this,uid,Toast.LENGTH_SHORT).show();


        mStorage= FirebaseStorage.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        selectfile = findViewById(R.id.selectbtn);
        uploadfile = findViewById(R.id.upload);
        notificatiom = findViewById(R.id.notify);

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
                if(mUri != null)
                    uploadvideo(mUri);
                else
                    Toast.makeText(videofile.this,"Please upload a file",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void uploadvideo(Uri uri) {
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = mUser.getUid();
        Context context;
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setProgress(0);
        mProgressDialog.show();


        final String filename = System.currentTimeMillis()+"";
        StorageReference storageReference = mStorage.getReference();
        storageReference.child("Uploads").child("Video").child(uid).child(filename).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String url = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                DatabaseReference reference = mDatabase.getReference();

                reference.child("user").child(uid).child("Video") .child(filename).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
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
                reference.child("User").child(uid).child("Video") .child(filename).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
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
