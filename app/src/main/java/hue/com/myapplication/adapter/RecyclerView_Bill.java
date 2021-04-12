package hue.com.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hue.com.myapplication.R;
import hue.com.myapplication.model.Bill;

public class RecyclerView_Bill extends RecyclerView.ViewHolder {
    public RecyclerView_Bill(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListenner.onItemClick(view, getAdapterPosition());

            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListenner.onItemLongClick(view, getAdapterPosition());
                return true;
            }
        });
    }

    private RecyclerView_Bill.ClickListener mClickListenner;

    public interface ClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int postion);
    }
    public void setOnClickListener(RecyclerView_Bill.ClickListener clickListener) {
        mClickListenner = clickListener;

    }

    public void setBill(Context context, String billid, String date, String address, String payments,
                        String state, int total, String username) {
        TextView tvNgayhd = itemView.findViewById(R.id.tvNgayhd);
        TextView tvGiahd = itemView.findViewById(R.id.tvGiahd);
        TextView tvTenHD = itemView.findViewById(R.id.tvTenHD);
        TextView tvhttt = itemView.findViewById(R.id.tvhttt);
        TextView tvdchd = itemView.findViewById(R.id.tvdchd);

        tvTenHD.setText("MHD"+billid);
        tvGiahd.setText(total + " VNĐ");
        tvhttt.setText(payments);
        tvdchd.setText("Address: "+address);
        tvNgayhd.setText(date);
    }
    public static  class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView_Bill>{
        private Context context;
        private List<Bill> bills= new ArrayList<>();

        public RecyclerAdapter(Context context, List<Bill> bills) {
            this.context = context;
            this.bills = bills;
        }

        @NonNull
        @Override
        public RecyclerView_Bill onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater= LayoutInflater.from(parent.getContext());
            View itemView= inflater.inflate(R.layout.item_hoadon,parent,false);
            return new RecyclerView_Bill(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView_Bill holder, int position) {

        }

        @Override
        public int getItemCount() {
            return bills.size();
        }
    }

}
