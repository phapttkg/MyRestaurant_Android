package hue.com.myapplication.activityuser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hue.com.myapplication.R;

public class Update_inforUSActivity extends AppCompatActivity {
    ImageView back;
    EditText edtfullname, edtphone;
    Spinner spgender;
    Button btnOk, btnCan;
    String usn;
    String g;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_infor_us);
        AnhXa();
        Intent intent = getIntent();
        usn = intent.getStringExtra("tenus");
        String fullname = intent.getStringExtra("fullname");
        String phone = intent.getStringExtra("phone");
        String gender = intent.getStringExtra("gender");

        edtfullname.setText(fullname);
        edtphone.setText(phone);

        String[] gen = {"Nam","Nữ","Khác"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Update_inforUSActivity.this,
                android.R.layout.simple_list_item_1, gen);
        spgender.setAdapter(arrayAdapter);
        for (int i = 0; i < spgender.getCount(); i++) {
            if (spgender.getItemAtPosition(i).toString().equals(gender)) {
                spgender.setSelection(i);
            }
        }

        spgender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                g = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("User");

                String fn = edtfullname.getText().toString();
                String sdt = edtphone.getText().toString();
                try {
                    myRef.child(usn).child("fullname").setValue(fn);
                    myRef.child(usn).child("phone").setValue(sdt);
                    myRef.child(usn).child("gender").setValue(g);
                    finish();
                } catch (Exception ex) {
                    Log.e("ERROR", ex.toString());
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public void AnhXa() {
        back = findViewById(R.id.back);
        edtfullname = findViewById(R.id.edtFullname);
        edtphone = findViewById(R.id.edtPhone);
        spgender = findViewById(R.id.spGender);
        btnOk = findViewById(R.id.btnOK);
        btnCan = findViewById(R.id.btnCancel);
    }
    }

