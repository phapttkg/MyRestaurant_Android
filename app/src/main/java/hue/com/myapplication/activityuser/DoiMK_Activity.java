package hue.com.myapplication.activityuser;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hue.com.myapplication.LoginActivity;
import hue.com.myapplication.R;
import hue.com.myapplication.dao.UserDao;

public class DoiMK_Activity extends AppCompatActivity {
    ImageView imvback, imvcheck;
    EditText edtusername, edtpass, edtnewpass, edtconfirmpass;
    Button btnOk, btnCancel;
    String pass, newpass, confirmpass;
    String un, pa, fu, sd, gen, ru;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mk_);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("User");
        AnhXa();
        un = UserDao.cruser.getUsname();
        pa = UserDao.cruser.getPassword();
        fu = UserDao.cruser.getFullname();
        sd = UserDao.cruser.getPhone();
        gen = UserDao.cruser.getGender();
        ru = UserDao.cruser.getRuler();
        edtusername.setText(un);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((edtpass.getText().length() == 0)) {
                    Toast.makeText(DoiMK_Activity.this, "Mật Khẩu không được để trống", Toast.LENGTH_SHORT).show();
                } else if (edtnewpass.getText().length() == 0) {
                    Toast.makeText(DoiMK_Activity.this, "Mật Khẩu Mới không được để trống", Toast.LENGTH_SHORT).show();
                } else if (edtconfirmpass.getText().length() == 0) {
                    Toast.makeText(DoiMK_Activity.this, "Mời Confirm lại mật khẩu", Toast.LENGTH_SHORT).show();
                } else if (edtpass.getText().toString().equals(pa)) {

                    if (edtnewpass.getText().toString().equals(edtconfirmpass.getText().toString())) {
                        imvcheck.setImageResource(R.drawable.checkicon);
                        try {
                            myRef.child(un).child("password").setValue(edtnewpass.getText().toString());
                            myRef.child(un).child("fullname").setValue(fu);
                            myRef.child(un).child("phone").setValue(sd);
                            myRef.child(un).child("gender").setValue(gen);
                            myRef.child(un).child("ruler").setValue(ru);

                            final ProgressDialog mDialog = new ProgressDialog(DoiMK_Activity.this);
                            mDialog.setMessage("Logout....");
                            mDialog.show();
                            Intent intent = new Intent(DoiMK_Activity.this, LoginActivity.class);
                            startActivity(intent);
                        } catch (Exception ex) {
                            Log.e("ERROR", ex.toString());
                        }
                    } else {
                        Toast.makeText(DoiMK_Activity.this, "Mời Confirm lại mật khẩu", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(DoiMK_Activity.this, "Sai Mật Khẩu", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    public void checkmk() {
        if ((edtnewpass.getText().length() == 0) && (edtconfirmpass.getText().length() == 0)) {

        } else if (edtnewpass.getText().toString().equals(edtconfirmpass.getText().toString())) {
            imvcheck.setImageResource(R.drawable.checkicon);
        }
    }

    public void AnhXa() {
        edtusername = findViewById(R.id.edtEmail);
        edtpass = findViewById(R.id.edtPass);
        edtnewpass = findViewById(R.id.edtNewPass);
        edtconfirmpass = findViewById(R.id.edtConfirmPass);
        btnOk = findViewById(R.id.btnDmOK);
        btnCancel = findViewById(R.id.btnCancel);
        imvback = findViewById(R.id.back);
        imvcheck = findViewById(R.id.imvcheck);

    }

}

