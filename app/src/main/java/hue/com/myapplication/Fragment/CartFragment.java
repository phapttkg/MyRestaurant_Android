package hue.com.myapplication.Fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import hue.com.myapplication.R;
import hue.com.myapplication.activityuser.ThanhToan_Activity;
import hue.com.myapplication.adapter.RecyclerView_BillDetail;
import hue.com.myapplication.dao.HdctDao;
import hue.com.myapplication.model.Billdetail;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    RecyclerView recyclercarts;
    FirebaseDatabase database;
    DatabaseReference billdetail;
    public TextView tvGiacs;
    Button btnPaycs;
    int total;
    RecyclerView_BillDetail.RecyclerAdapter adapter;

    List<Billdetail> cart = new ArrayList<>();
    HdctDao hdctDao;
    String billid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_cart, container, false);
        recyclercarts= view.findViewById(R.id.recyclercarts);
        tvGiacs= view.findViewById(R.id.tvGiacs);
        btnPaycs= view.findViewById(R.id.btnPaycs);
        hdctDao= new HdctDao(getContext());
        database= FirebaseDatabase.getInstance();
        billdetail= database.getReference("Billdetail");
        recyclercarts.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                false);
        recyclercarts.setLayoutManager(layoutManager);
        btnPaycs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(total <= 0){
                        Toast.makeText(getContext(), "cart trống", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent= new Intent(getContext(), ThanhToan_Activity.class);
                        intent.putExtra("tt",total);
                        startActivity(intent);
                    }

            }
        });
        loadlist();
        return  view;
    }

    public void loadlist(){
        cart= new ArrayList<>();
        cart=hdctDao.getall();
        adapter= new RecyclerView_BillDetail.RecyclerAdapter(getContext(),cart);
        recyclercarts.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        for(Billdetail b: cart)
            total+= ((b.getQuantity())*(b.getGia()));
        tvGiacs.setText(total+" VNĐ");


    }
}
