package hue.com.myapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import hue.com.myapplication.Fragment.CartFragment;
import hue.com.myapplication.activityuser.CartActivity;
import hue.com.myapplication.R;
import hue.com.myapplication.dao.HdctDao;
import hue.com.myapplication.model.Billdetail;

public class RecyclerView_BillDetail extends RecyclerView.ViewHolder {
    TextView tvTenMAct,tvSLct,tvGiact;
    ImageView imgxoact,imagefoodct;
    HdctDao hdctDao;
    public RecyclerView_BillDetail(@NonNull View itemView) {
        super(itemView);
        imagefoodct = itemView.findViewById(R.id.imagefoodct);
         imgxoact = itemView.findViewById(R.id.imgxoact);
        tvTenMAct= itemView.findViewById(R.id.tvTenMAct);
        tvSLct = itemView.findViewById(R.id.tvSLct);
        tvGiact=itemView.findViewById(R.id.tvGiact);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListenner.onItemClick(view,getAdapterPosition());

            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListenner.onItemLongClick(view,getAdapterPosition());
                return true;
            }
        });
    }

    private RecyclerView_BillDetail.ClickListener mClickListenner;




    public interface ClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view, int postion);
    }
    public void setOnClickListener(RecyclerView_BillDetail.ClickListener clickListener) {
        mClickListenner= clickListener;

    }
    public void setDetails(Context context, String billid, String name,int gia,int soluong, String image){
        ImageView imagefoodct = itemView.findViewById(R.id.imagefoodct);
        ImageView imgxoact = itemView.findViewById(R.id.imgxoact);

        TextView tvTenMAct= itemView.findViewById(R.id.tvTenMAct);
        TextView tvSLct = itemView.findViewById(R.id.tvSLct);
        TextView tvGiact=itemView.findViewById(R.id.tvGiact);
        tvTenMAct.setText(name);
        tvGiact.setText(gia+" VNĐ");
        tvSLct.setText(soluong+"");
        Picasso.get().load(image).into(imagefoodct);
    }




    public static  class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView_BillDetail>{
        private Context context;
        HdctDao hdctDao;

        List<Billdetail> billdetails= new ArrayList<>();

        public RecyclerAdapter(Context context, List<Billdetail> billdetails) {
            this.context = context;
            this.billdetails = billdetails;
        }

        @NonNull
        @Override
        public RecyclerView_BillDetail onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater= LayoutInflater.from(parent.getContext());
            View itemView= inflater.inflate(R.layout.item_hoadonct,parent,false);
            RecyclerView_BillDetail viewholder = new RecyclerView_BillDetail(itemView);

            return viewholder;
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerView_BillDetail holder, int position) {
            hdctDao= new HdctDao(context);
            final Billdetail b= billdetails.get(position);
            holder.tvTenMAct.setText(billdetails.get(position).getFoodname());
            holder.tvSLct.setText(billdetails.get(position).getQuantity()+"");
            holder.tvGiact.setText(billdetails.get(position).getGia()+"");
            Picasso.get().load(billdetails.get(position).getImage()).into(holder.imagefoodct);
            holder.setOnClickListener(new RecyclerView_BillDetail.ClickListener() {
                @Override
                public void onItemClick(View view, final int position) {

                    holder.imgxoact.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder dialogdelete= new AlertDialog.Builder(context);
                            dialogdelete.setTitle("Xóa");
                            dialogdelete.setMessage("bạn có muốn xóa không");
                            dialogdelete.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    Intent intent= new Intent(context, CartActivity.class);
                                    context.startActivity(intent);

                                    int g = billdetails.get(position).getGia();
                                    int qt= billdetails.get(position).getQuantity();
                                    int tt= g*qt;
                                    showdeletedialog(b);
                                    notifyDataSetChanged();


                                }
                            });

                            dialogdelete.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            dialogdelete.show();
                        }
                    });
                }

                @Override
                public void onItemLongClick(View view, int postion) {

                }
            });


        }

        @Override
        public int getItemCount() {
            return billdetails.size();
        }


        public void showdeletedialog(Billdetail b){

            try {
                hdctDao.deletek(b);
                billdetails.remove(b);
                notifyDataSetChanged();
                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();

            }catch (Exception e){
                Toast.makeText(context, "xóa thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
