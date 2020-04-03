package com.deb.videofy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<String> data;
    private String btntitle;
    private Button mButton;

    public Adapter(Context context, List<String> data, String btntitle){

        this.mLayoutInflater = LayoutInflater.from(context);
        this.data = data;
        this.btntitle = btntitle;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = mLayoutInflater.inflate(R.layout.custom_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        String item_name1 = data.get(position);
        viewHolder.title.setText(item_name1);
        viewHolder.mButton.setText(btntitle);
    }




    @Override
    public int getItemCount() {
        return data.size();
    }

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
