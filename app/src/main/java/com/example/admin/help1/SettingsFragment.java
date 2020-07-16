package com.example.admin.help1;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    View mview;
    GoogleSignInClient mGoogleSignInClient;
    GoogleApiClient mGoogleApiClient;
    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Inflate the layout for this fragment
        mview =  inflater.inflate(R.layout.fragment_settings, container, false);

        Button logout = mview.findViewById(R.id.logout_btn);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(@NonNull Status status) {
                                // ...
                                Toast.makeText(getActivity(),"Logged Out",Toast.LENGTH_SHORT).show();


                            }
                        });
                Intent i=new Intent(getActivity(),LoginActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        Button sup = mview.findViewById(R.id.support_btn);
        Button rep = mview.findViewById(R.id.report_btn);
        Button faq = mview.findViewById(R.id.faq_btn);

        sup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Under Construction. To be added in further updates.",Toast.LENGTH_SHORT).show();
            }
        });


        rep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Under Construction. To be added in further updates.",Toast.LENGTH_SHORT).show();
            }
        });


        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Under Construction. To be added in further updates.",Toast.LENGTH_SHORT).show();
            }
        });

        return mview;
    }


}
