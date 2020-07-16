package com.example.admin.help1;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.help1.ProfileActivity;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    View mview;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mview = inflater.inflate(R.layout.fragment_profile, container, false);


        TextView tname = mview.findViewById(R.id.display_Name);
        TextView tmobile = mview.findViewById(R.id.display_mobile);
        TextView temail = mview.findViewById(R.id.display_Email);

        TextView tgender = mview.findViewById(R.id.display_gender);
        Button editbutton = mview.findViewById(R.id.saveBtn);

        tname.setText(DataClass.InnerData.getDataName());
        tmobile.setText(DataClass.InnerData.getDataPhone());
        tgender.setText(DataClass.InnerData.getDataGender());

        temail.setText(DataClass.InnerData.getDataEmail());
        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ProfileActivity.class);
                startActivity(i);
                getActivity().finish();


            }
        });

        return mview;
    }

}
