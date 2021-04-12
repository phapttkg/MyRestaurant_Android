package hue.com.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import hue.com.myapplication.R;
import hue.com.myapplication.dao.HdctDao;
import hue.com.myapplication.model.Billdetail;

public class RecyclerView_showBill extends RecyclerView.ViewHolder{
    TextView tvTenMAct,tvSLct,tvGiact;
    ImageView imgxoact,imagefoodct;
    public RecyclerView_showBill(@NonNull View itemView) {
        super(itemView);
        imagefoodct = itemView.findViewById(R.id.imagefoodct);
        imgxoact = itemView.findViewById(R.id.imgxoact);

        tvTenMAct= itemView.findViewById(R.id.tvTenMAct);
        tvSLct = itemView.findViewById(R.id.tvSLct);
        tvGiact=itemView.findViewById(R.id.tvGiact);
        imgxoact.setVisibility(View.GONE);
    }




    public static  class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView_showBill>{
        private Context context;
        HdctDao hdctDao;

        private List<String> ten;
        private List<Integer> sl;
        public RecyclerAdapter(Context context,List<String> ten,List<Integer>sl) {
            this.context = context;
            this.ten = ten;
            this.sl=sl;
        }

        List<Billdetail> billdetails= new ArrayList<>();

        public RecyclerAdapter(Context context, List<Billdetail> billdetails) {
            this.context = context;
            this.billdetails = billdetails;
        }

        @NonNull
        @Override
        public RecyclerView_showBill onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater= LayoutInflater.from(parent.getContext());
            View itemView= inflater.inflate(R.layout.item_hoadonct,parent,false);
            RecyclerView_showBill viewholder = new RecyclerView_showBill(itemView);
            return viewholder;
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerView_showBill holder, int position) {
            hdctDao= new HdctDao(context);
            final Billdetail b= billdetails.get(position);
            holder.tvTenMAct.setText(billdetails.get(position).getFoodname());
            holder.tvSLct.setText(billdetails.get(position).getQuantity()+"");
            holder.tvGiact.setText(billdetails.get(position).getGia()+"");


            Picasso.get().load(billdetails.get(position).getImage()).into(holder.imagefoodct);



        }

        @Override
        public int getItemCount() {
            return billdetails.size();
        }


    }
}
