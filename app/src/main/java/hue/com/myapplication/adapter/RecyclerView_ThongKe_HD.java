package hue.com.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import hue.com.myapplication.R;
import hue.com.myapplication.model.Bill;

public class RecyclerView_ThongKe_HD extends RecyclerView.Adapter<RecyclerView_ThongKe_HD.ViewHolder>{
    ArrayList<Bill> list;
    Context context;
    DatabaseReference mData;
    String spn;
    public RecyclerView_ThongKe_HD(Context context, ArrayList<Bill> list) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(viewGroup.getContext());
        View itemView=layoutInflater.inflate(R.layout.item_thongke_hoadon,viewGroup,false);
        mData= FirebaseDatabase.getInstance().getReference();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final Bill bill = list.get(i);
        viewHolder.tvUser.setText("MHD"+bill.getBillid());
        viewHolder.tvTT.setText(""+bill.getTotal());


    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvUser,tvTT;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvUser=itemView.findViewById(R.id.tvtenma);
            tvTT=itemView.findViewById(R.id.tvGia);
        }
    }

}
