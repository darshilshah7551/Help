package com.example.admin.help1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Map;


public class ProfileActivity extends AppCompatActivity {

    EditText name;
    EditText phone;
    EditText email;
    RadioGroup rg;
    RadioButton rb;
    int selectedId;

    Map<String, String> m;
    ProfileFragment fragobj;

    public ProfileFragment getInfo() {

        return fragobj;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Button saveBtn = findViewById(R.id.saveBtn);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.mobile);
        email=findViewById(R.id.email);
        rg=findViewById(R.id.Rg);


        String sessionName = getIntent().getStringExtra("name");
        name.setText(sessionName);

        String sessionEmail = getIntent().getStringExtra("EXTRA_SESSION_ID");
        email.setText(sessionEmail);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedId = rg.getCheckedRadioButtonId();
                rb = findViewById(selectedId);

                if(name.getText() == null ||phone.getText()==null||email.getText()==null||selectedId==-1) {
                    Toast.makeText(ProfileActivity.this,"Fields cannot be empty", Toast.LENGTH_LONG).show();
                }
                else {
                    storeData();
                    Intent intent = new Intent(ProfileActivity.this, MapsActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public void storeData() {
        DataClass.InnerData.setDataName(name.getText().toString());
        DataClass.InnerData.setDataPhone(phone.getText().toString());
        DataClass.InnerData.setDataEmail(email.getText().toString());
        DataClass.InnerData.setDataGender(rb.getText().toString());
    }
}