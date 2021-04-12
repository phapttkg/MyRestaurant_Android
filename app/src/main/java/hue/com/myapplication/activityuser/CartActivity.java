package hue.com.myapplication.activityuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

import hue.com.myapplication.R;
import hue.com.myapplication.adapter.RecyclerView_BillDetail;
import hue.com.myapplication.dao.HdctDao;
import hue.com.myapplication.dao.UserDao;
import hue.com.myapplication.model.Billdetail;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class CartActivity extends AppCompatActivity  {
    ImageView imgbackc;
    RecyclerView recyclercart;
   public TextView tvGiac;
    Button btnPayc;
    FirebaseDatabase database;
    RecyclerView_BillDetail.RecyclerAdapter adapter;
    DatabaseReference billdetail;
    int total;
    int sum1 = 0 ;
    List<Billdetail> cart = new ArrayList<>();
    HdctDao hdctDao;
    String a;
    TextView tvTenMAct,tvSLct,tvGiact;
    ImageView imgxoact,imagefoodct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        hdctDao= new HdctDao(this);
        imgbackc = findViewById(R.id.imgbackc);
        recyclercart = findViewById(R.id.recyclercart);
        tvGiac = findViewById(R.id.tvGiac);
        btnPayc = findViewById(R.id.btnPayc);
        database = FirebaseDatabase.getInstance();
        Billdetail b = new Billdetail();
        a= UserDao.cruser.getPhone();


        billdetail= database.getReference("Billdetail");
        recyclercart.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclercart.setLayoutManager(layoutManager);
        imgbackc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnPayc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(CartActivity.this,ThanhToan_Activity.class);
                intent.putExtra("tt",total);
                startActivity(intent);

            }
        });


            loadlist();


//        loadtl();
    }


//    public void loadtl() {
//        String a1= String.valueOf(System.currentTimeMillis());
//        FirebaseRecyclerOptions<Billdetail> options = new FirebaseRecyclerOptions.Builder<Billdetail>().setQuery( billdetail.child("User View").child(a).child("Products"), Billdetail.class).build();
//
//        FirebaseRecyclerAdapter<Billdetail, RecyclerView_BillDetail> firebaseRecyclerAdapte = new FirebaseRecyclerAdapter<Billdetail, RecyclerView_BillDetail>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull RecyclerView_BillDetail holder, int position, @NonNull Billdetail model) {
//                holder.setDetails(getApplicationContext(), model.getBillid(), model.getFoodname(), model.getGia(), model.getQuantity(), model.getImage());
//                total = (model.getGia()) * (model.getQuantity());
//                sum1 = sum1 + total;
//                tvGiac.setText(sum1 + ". VNĐ");
//
//            }
//
//            @NonNull
//            @Override
//            public RecyclerView_BillDetail onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoadonct, parent, false);
//                RecyclerView_BillDetail viewholder = new RecyclerView_BillDetail(view);
//                final ImageView imgxoact = view.findViewById(R.id.imgxoact);
//
//
//                viewholder.setOnClickListener(new RecyclerView_BillDetail.ClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        final String billid = getItem(position).getBillid();
//                        final String foodname = getItem(position).getFoodname();
//                        final int gia = getItem(position).getGia();
//                        final int quantity = getItem(position).getQuantity();
//                        final String image = getItem(position).getImage();
//
//                        imgxoact.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//
//                                showdeleteDialog(billid, foodname, gia, quantity, image);
//
//                            }
//                        });
//
//                    }
//
//                    @Override
//                    public void onItemLongClick(View view, int postion) {
//
//                    }
//                });
//
//
//                return viewholder;
//            }
//
//        };
//
//        recyclercart.setAdapter(firebaseRecyclerAdapte);
//        firebaseRecyclerAdapte.startListening();
//        firebaseRecyclerAdapte.notifyDataSetChanged();
//
//
//    }


    public void showdeleteDialog(final String billid, final String foodname, final int gia, final int quantity, final String image) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure to delete this?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Query mquery = billdetail.child("User View").child(a).child("Products").orderByChild("billid").equalTo(billid);
                mquery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            ds.getRef().removeValue();
                            Billdetail b = dataSnapshot.getChildren().iterator().next().getValue(Billdetail.class);
                            int g=  b.getGia();
                            int sl= b.getQuantity();
                            int s= g*sl;
                            sum1 = sum1 - s;
                            tvGiac.setText(sum1+" VNĐ");
                        }
                        Toast.makeText(CartActivity.this, "đã xóa", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(CartActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });


            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.create().show();

    }

    public void loadlist(){
        cart= new ArrayList<>();
        cart=hdctDao.getall();
        adapter= new RecyclerView_BillDetail.RecyclerAdapter(this,cart);
        recyclercart.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        for(Billdetail b: cart)
            total+= ((b.getQuantity())*(b.getGia()));
        tvGiac.setText(total+" VNĐ");




    }

}
