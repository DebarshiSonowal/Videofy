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

import java.util.ArrayList;

public class NewVideoFragment extends Fragment implements dialogbox.dialogboxlistener {
    RecyclerView mRecyclerView;
    Adapter mAdapter;

    private NewVideoViewModel mNewVideoViewModel;
    ArrayList<String> item;
    String mButton;
    Boolean flag = false;
    @Override
    public void onStart() {
        super.onStart();
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        item = new ArrayList<>();
        item.add("First Card View");
        item.add("Second Card View");
        item.add("Third Card View");
        item.add("Forth Card View");
        item.add("Five Card View");
        mButton = "Download";

        mNewVideoViewModel =
                ViewModelProviders.of(this).get(NewVideoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_newvideos, container, false);
        final RecyclerView mRecyclerView = root.findViewById(R.id.newrecycle);
        final CardView mCard = root.findViewById(R.id.addbtn);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new Adapter(getContext(),item,mButton);
        mRecyclerView.setAdapter(mAdapter);
        mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();

            }
        });
        mNewVideoViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        return root;
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
