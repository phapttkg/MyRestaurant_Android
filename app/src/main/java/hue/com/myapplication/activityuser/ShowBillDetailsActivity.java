package hue.com.myapplication.activityuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import hue.com.myapplication.R;
import hue.com.myapplication.adapter.RecyclerView_BillDetail;
import hue.com.myapplication.adapter.RecyclerView_showBill;
import hue.com.myapplication.dao.UserDao;
import hue.com.myapplication.model.Bill;
import hue.com.myapplication.model.Billdetail;
import hue.com.myapplication.model.User;

public class ShowBillDetailsActivity extends AppCompatActivity {
    RecyclerView recyclerhdcts;
    FirebaseDatabase database;
    DatabaseReference bill;
     ImageView imgbackcts;
    String id="";
    RecyclerView_showBill.RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bill_details);
        imgbackcts= findViewById(R.id.imgbackcts);
        recyclerhdcts = findViewById(R.id.recb);
        id= getIntent().getStringExtra("id");
        database= FirebaseDatabase.getInstance();
        bill= database.getReference("Bill");
        recyclerhdcts.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false);
        recyclerhdcts.setLayoutManager(layoutManager);
        imgbackcts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        load();

    }

    public void load(){
        adapter= new RecyclerView_showBill.RecyclerAdapter(this,UserDao.bill.getFoods());
        adapter.notifyDataSetChanged();
        recyclerhdcts.setAdapter(adapter);
    }
}
