package com.deb.videofy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class dialogbox extends DialogFragment {
    private Boolean mBoolean = true;
    private dialogboxlistener mDialogboxlistener;
    private Button aubtn,vdbtn;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mDialogboxlistener = (dialogboxlistener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogbox,null);

        builder.setView(view).setTitle("Upload").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        aubtn = view.findViewById(R.id.audiobtn);
        vdbtn = view.findViewById(R.id.videobtn);
        aubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), audiofile.class);
                startActivity(intent);
            }
        });
        vdbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), videofile.class);
                startActivity(intent);
            }
        });


        return builder.create();

    }


    public  interface dialogboxlistener{
        void getInput(Boolean temp);
    }
}
