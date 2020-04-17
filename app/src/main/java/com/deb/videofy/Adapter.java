package com.deb.videofy;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deb.videofy.ui.newvideo.NewVideoFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.os.Environment.DIRECTORY_DOWNLOADS;


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private Context context ;
    private LayoutInflater mLayoutInflater;
    private List<String> data;
    private List<String> link;
    private String btntitle,uid;
    private Button mButton;
    private Date mDate = new Date();
    Locale locale;
    private SimpleDateFormat sdf = new SimpleDateFormat("DD/MM/YYYY",Locale.getDefault());

    public Adapter(Context context, List<String> data, String btntitle,List<String>link,String uid){

        this.mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
        this.link = link;
        this.uid = uid;
        this.btntitle = btntitle;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = mLayoutInflater.inflate(R.layout.custom_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final String item_name1 = data.get(position);
        viewHolder.title.setText(item_name1);
        viewHolder.mButton.setText(btntitle);
        viewHolder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                file nf = new file(sdf.format(mDate),item_name1);
                DownloadManager downloadmanager = (DownloadManager) context.
                        getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(link.get(position));
//                Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/videofiy.appspot.com/o/Uploads%2FVideo%2FOspHadu29BSdph2zUbr8jzVLOg22%2Fno?alt=media&token=3934536d-8f46-473d-908e-2789e3be1a04");
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalFilesDir(context, DIRECTORY_DOWNLOADS , data.get(position));
                downloadmanager.enqueue(request);
                FirebaseDatabase.getInstance().getReference().child("Downloaded").child(uid).push().setValue(nf);
                FirebaseDatabase.getInstance().getReference(). child("user").child(uid).child("Downloaded").push().setValue(nf);
                FirebaseDatabase.getInstance().getReference(). child("Total files").child("Downloaded").push().setValue(nf);
            }
        });
    }



    @Override
    public int getItemCount() {
        return data.size();
    }
//    private void downloadManager(String url) {
//        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
//        request.setDescription("download");
//        request.setTitle(""+songtitle);
//// in order for this if to run, you must use the android 3.2 to compile your app
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            request.allowScanningByMediaScanner();
//            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        }
//        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, ""+songtitle+".mp4");
//
//// get download service and enqueue file
//        DownloadManager manager = (DownloadManager) getA.getSystemService(Context.DOWNLOAD_SERVICE);
//        manager.enqueue(request);
//    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView title,amount;
        Button mButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.name);
            mButton = itemView.findViewById(R.id.button2);
        }
    }
}
