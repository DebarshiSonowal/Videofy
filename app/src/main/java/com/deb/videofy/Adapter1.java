package com.deb.videofy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter1 extends RecyclerView.Adapter<Adapter1.ViewHolder> {
    String upld1 ;
    private LayoutInflater mLayoutInflater;
    private List<String> data;
    private List<Integer> upld;
    private List<Integer> dwnld;

    public Adapter1(LayoutInflater layoutInflater, List<String> data, List<Integer> upld, List<Integer> dwnld) {
        mLayoutInflater = layoutInflater;
        this.data = data;
        this.upld = upld;
        this.dwnld = dwnld;
    }

    @NonNull
    @Override
    public Adapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = mLayoutInflater.inflate(R.layout.cutom_view2, parent, false);
        return new Adapter1.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        String item_name1 = data.get(position);

//            position = position + 1;


        try {
            upld1 = upld.get(position).toString();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            upld1 = "0";
        }
        String dwnld1 = dwnld.get(position).toString();

        viewHolder.upload.setText(upld1);
        viewHolder.download.setText(dwnld1);
        viewHolder.name.setText(item_name1);
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        TextView upload,download,name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            upload= itemView.findViewById(R.id.txtupld);
            download = itemView.findViewById(R.id.txtdwn);
            name = itemView.findViewById(R.id.name);

        }
    }
}


