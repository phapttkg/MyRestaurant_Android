package hue.com.myapplication.activityadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import hue.com.myapplication.R;
import hue.com.myapplication.adapter.RecyclerView_showBill;
import hue.com.myapplication.dao.UserDao;
import hue.com.myapplication.model.Bill;

public class ShowbillDetailadActivity extends AppCompatActivity {
    RecyclerView recbad;
    ImageView imgbackctsad;
    Button btnhuybad,btnttbad;
    FirebaseDatabase database;
    DatabaseReference bill;
    String id="",date,payment,username,diachi,states;
    int gia;
    TextView tvGiasb;
    RecyclerView_showBill.RecyclerAdapter adapter;
    ProgressDialog mProgressDialog;
    Bill b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showbill_detailad);
        mProgressDialog = new ProgressDialog(this);
        recbad= findViewById(R.id.recbad);
        imgbackctsad= findViewById(R.id.imgbackctsad);
        btnhuybad= findViewById(R.id.btnhuybad);
        btnttbad= findViewById(R.id.btnttbad);
        tvGiasb=findViewById(R.id.tvGiasb);

        database= FirebaseDatabase.getInstance();
        bill= database.getReference("Bill");
        recbad.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recbad.setLayoutManager(layoutManager);
        imgbackctsad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        gia= UserDao.bill.getTotal();
        tvGiasb.setText(gia+" VNĐ");
        id= getIntent().getStringExtra("ida");
        btnhuybad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        date=UserDao.bill.getDate();
        states="Approved";
        payment=UserDao.bill.getPayments();
        username=UserDao.bill.getUsername();
        diachi=UserDao.bill.getAddress();
        btnttbad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedatabas();

            }
        });

        load();
    }


    public void load(){
        adapter= new RecyclerView_showBill.RecyclerAdapter(this, UserDao.bill.getFoods());
        adapter.notifyDataSetChanged();
        recbad.setAdapter(adapter);
    }

    private void updatedatabas() {
        Query query= bill.orderByChild("billid").equalTo(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    ds.getRef().child("billid").setValue(id);
                    ds.getRef().child("state").setValue(states);
                    ds.getRef().child("address").setValue(diachi);
                    ds.getRef().child("total").setValue(gia);
                    ds.getRef().child("username").setValue(username);
                    ds.getRef().child("date").setValue(date);
                    ds.getRef().child("payments").setValue(payment);

                }
                mProgressDialog.dismiss();
                Toast.makeText(ShowbillDetailadActivity.this, "Hoàn Thành", Toast.LENGTH_SHORT).show();
                finish();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
