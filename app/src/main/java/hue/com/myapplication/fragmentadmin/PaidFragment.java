package hue.com.myapplication.fragmentadmin;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hue.com.myapplication.R;
import hue.com.myapplication.activityuser.ShowBillDetailsActivity;
import hue.com.myapplication.adapter.RecyclerView_Bill;
import hue.com.myapplication.dao.UserDao;
import hue.com.myapplication.model.Bill;


public class PaidFragment extends Fragment {
    RecyclerView recyclerbilladp;
    FirebaseDatabase database;
    DatabaseReference bill;
    String billid;
    Button btnsearchmhdp;
    EditText edtSearchmhdp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_paid, container, false);
        btnsearchmhdp= view.findViewById(R.id.btnsearchmhdp);
        edtSearchmhdp= view.findViewById(R.id.edtSearchmhdp);
        recyclerbilladp= view.findViewById(R.id.recyclerbilladp);
        database= FirebaseDatabase.getInstance();
        bill= database.getReference("Bill");
        recyclerbilladp.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerbilladp.setLayoutManager(layoutManager);
        loadtl();

        btnsearchmhdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchtext= edtSearchmhdp.getText().toString();
                if(edtSearchmhdp.getText().toString().length()==0){
                    loadtl();
                }else {
                    search(searchtext);
                }


            }
        });

        return view;
    }



    public void loadtl() {
        FirebaseRecyclerOptions<Bill> options = new FirebaseRecyclerOptions.Builder<Bill>().
                setQuery(bill.orderByChild("state").equalTo("Approved"), Bill.class).build();

        FirebaseRecyclerAdapter<Bill, RecyclerView_Bill> firebaseRecyclerAdapte = new
                FirebaseRecyclerAdapter<Bill, RecyclerView_Bill>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RecyclerView_Bill holder,
                                            int position, @NonNull final Bill model) {
                holder.setBill(getContext().getApplicationContext(), model.getBillid(),
                        model.getDate(), model.getAddress(), model.getPayments(),model.getState(),
                        model.getTotal(),model.getUsername());
                holder.setOnClickListener(new RecyclerView_Bill.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        billid = getItem(position).getBillid();
                        Intent intent = new Intent(getContext(), ShowBillDetailsActivity.class);
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
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoadon,
                        parent, false);
                final RecyclerView_Bill viewholder = new RecyclerView_Bill(view);

                return viewholder;
            }

        };

        recyclerbilladp.setAdapter(firebaseRecyclerAdapte);
        firebaseRecyclerAdapte.startListening();
        firebaseRecyclerAdapte.notifyDataSetChanged();


    }
public void search(String searchtext){
    FirebaseRecyclerOptions<Bill> options = new FirebaseRecyclerOptions.Builder<Bill>()
            .setQuery(bill.orderByChild("billid").equalTo(searchtext), Bill.class).build();

    FirebaseRecyclerAdapter<Bill, RecyclerView_Bill> firebaseRecyclerAdapte = new
            FirebaseRecyclerAdapter<Bill, RecyclerView_Bill>(options) {
        @Override
        protected void onBindViewHolder(@NonNull RecyclerView_Bill holder,
                                        int position, @NonNull final Bill model) {
            holder.setBill(getContext().getApplicationContext(),
                    model.getBillid(), model.getDate(), model.getAddress(),
                    model.getPayments(),model.getState(), model.getTotal(),model.getUsername());
            holder.setOnClickListener(new RecyclerView_Bill.ClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    billid = getItem(position).getBillid();
                    Intent intent = new Intent(getContext(), ShowBillDetailsActivity.class);
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoadon,
                    parent, false);
            final RecyclerView_Bill viewholder = new RecyclerView_Bill(view);
            return viewholder;
        }

    };

    recyclerbilladp.setAdapter(firebaseRecyclerAdapte);
    firebaseRecyclerAdapte.startListening();
    firebaseRecyclerAdapte.notifyDataSetChanged();

}
}
