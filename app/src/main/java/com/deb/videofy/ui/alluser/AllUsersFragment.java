package com.deb.videofy.ui.alluser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import java.util.ArrayList;

public class AllUsersFragment extends Fragment {
    Adapter1 mAdapter1;
    ArrayList<String> item;
    ArrayList<Integer> upld;
    ArrayList<Integer> dwnld;

    private AllUsersViewModel mAllUsersViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        item = new ArrayList<>();
        item.add("First User");
        item.add("Second User");
        item.add("Third User");
        item.add("Forth User");
        item.add("Five User");

        upld = new ArrayList<>();
        upld.add(4353);
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
