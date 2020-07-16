package com.example.admin.help1;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {

    private static final int SELECT_PHONE_NUMBER = 7551;
    View mview;
    ListView lv;
    int s = 0;
    int j = 0;
    public static final Object MY_REQUEST_INT = 177;
    ListAdapter lad;
    String name;
    String number;
    ArrayList<ContactModel> rowItems = new ArrayList<>();
    ArrayList<String> c= new ArrayList<>();
    ArrayList<String> cn= new ArrayList<>();

    public ContactsFragment() {
        Log.e("ASSSSSSSSSSS",DataClass.InnerData.getNumber()+"");
        lad = new ListAdapter(getActivity(),rowItems,cn,c);
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mview = inflater.inflate(R.layout.fragment_contacts, container, false);
        lv = mview.findViewById(R.id.view_contact);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        lad = new ListAdapter(getActivity(), rowItems,cn,c);


        FloatingActionButton fab = mview.findViewById(R.id.fab);


        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_CONTACTS)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Contacts access needed");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setMessage("please confirm Contacts access");//TODO put real question
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {


                    @TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(
                                new String[]
                                        {Manifest.permission.READ_CONTACTS}
                                , (Integer) MY_REQUEST_INT);

                    }
                });
                builder.show();
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_CONTACTS},
                        (Integer) MY_REQUEST_INT);
            }
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(
                                new String[]
                                        {Manifest.permission.READ_CONTACTS}
                                , (Integer) MY_REQUEST_INT);
                    }

                } else {
                    Intent i = new Intent(Intent.ACTION_PICK);
                    i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                    startActivityForResult(i, SELECT_PHONE_NUMBER);
                }
            }
        });
        return mview;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == SELECT_PHONE_NUMBER && resultCode == RESULT_OK) {

            Uri contactUri = data.getData();


            String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
            Cursor cursor = getActivity().getContentResolver().query(contactUri, projection,
                    null, null, null);


            if (cursor != null && cursor.moveToFirst()) {

                int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                int numberIndex1 = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                name = cursor.getString(numberIndex);
                number = cursor.getString(numberIndex1);


                if (lad.getCount() < 5 ) {
                    if (validate()) {
                        rowItems.add(new ContactModel(name, number));
                        c.add(number);
                        cn.add(name);
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "CONTACT ALREADY EXISTS", Toast.LENGTH_LONG).show();
                    }

                } else{
                    Toast.makeText(getActivity(), "CONTACTS LIMIT REACHED", Toast.LENGTH_LONG).show();
                }

                DataClass.InnerData.setNumber(c);
                DataClass.InnerData.setName(cn);
            }

            if (cursor != null) {
                cursor.close();
            }
        }

        lv.setAdapter(lad);
        j++;
    }

    public boolean validate() {


            for (int i = 0; i < c.size(); i++) {
                if (c.contains(name)) {
                    Log.e("HSAJHGKFJAHSKFHHAKJHKJA","nameCheck" + c);
                    return false;
                }

            }

        return true;
    }

}
