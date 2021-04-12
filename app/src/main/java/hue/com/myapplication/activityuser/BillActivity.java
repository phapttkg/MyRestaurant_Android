package hue.com.myapplication.activityuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hue.com.myapplication.R;
import hue.com.myapplication.adapter.RecyclerView_Bill;
import hue.com.myapplication.dao.UserDao;
import hue.com.myapplication.model.Bill;

public class BillActivity extends AppCompatActivity {
    RecyclerView recyclerbill;
    String billid;
    ImageView imgbackbill;
    FirebaseDatabase database;
    DatabaseReference bill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        recyclerbill= findViewById(R.id.recyclerbill);
        imgbackbill= findViewById(R.id.imgbackbill);
        database= FirebaseDatabase.getInstance();
        bill= database.getReference("Bill");
        recyclerbill.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerbill.setLayoutManager(layoutManager);
        imgbackbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        loadtl();
    }


    public void loadtl() {

        FirebaseRecyclerOptions<Bill> options = new FirebaseRecyclerOptions.Builder<Bill>().setQuery(bill, Bill.class).build();

        FirebaseRecyclerAdapter<Bill, RecyclerView_Bill> firebaseRecyclerAdapte = new FirebaseRecyclerAdapter<Bill, RecyclerView_Bill>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RecyclerView_Bill holder, int position, @NonNull final Bill model) {
                holder.setBill(getApplicationContext(), model.getBillid(), model.getDate(), model.getAddress(), model.getPayments(),model.getState(), model.getTotal(),model.getUsername());
                holder.setOnClickListener(new RecyclerView_Bill.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        billid = getItem(position).getBillid();
                        Intent intent = new Intent(BillActivity.this, ShowBillDetailsActivity.class);
                        intent.putExtra("id",billid);
                        UserDao.bill=model;
                        startActivity(intent);



                    }

                    @Override
                    public void onItemLongClick(View view, int postion) {

                    }
                });
            }

            @NonNull
            @Override
            public RecyclerView_Bill onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoadon, parent, false);
                final RecyclerView_Bill viewholder = new RecyclerView_Bill(view);

                return viewholder;
            }

        };

        recyclerbill.setAdapter(firebaseRecyclerAdapte);
        firebaseRecyclerAdapte.startListening();
        firebaseRecyclerAdapte.notifyDataSetChanged();


    }


}
