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
import hue.com.myapplication.model.Category;
import hue.com.myapplication.model.Food;

public class RecyclerView_MonAn extends RecyclerView.ViewHolder {

    public RecyclerView_MonAn(@NonNull View itemView) {
        super(itemView);

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

    private RecyclerView_MonAn.ClickListener mClickListenner;

    public interface ClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view, int postion);
    }

    public void setOnClickListener(RecyclerView_MonAn.ClickListener clickListener) {
        mClickListenner= clickListener;

    }
    public void setDetails(Context context, String foodid, String categorid,
                           String foodname,int price,String foodimage){
        ImageView imgfoodma = itemView.findViewById(R.id.imgfoodma);
        TextView tvTenMA= itemView.findViewById(R.id.tvTenMA);
        TextView tvGia = itemView.findViewById(R.id.tvGia);

        tvGia.setText(price+ " VNĐ");
        tvTenMA.setText(foodname);
        Picasso.get().load(foodimage).into(imgfoodma);
    }

    public static class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView_MonAn>{
        private Context context;
        private List<Food> foodList= new ArrayList<>();
        public RecyclerAdapter(Context context, List<Food> foodList) {
            this.context = context;
            this.foodList = foodList;
        }

        @NonNull
        @Override
        public RecyclerView_MonAn onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater= LayoutInflater.from(parent.getContext());
            View itemView= inflater.inflate(R.layout.item_ma,parent,false);
            return new RecyclerView_MonAn(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView_MonAn holder, int position) {

        }

        public  void filreredList(ArrayList<Food> filterlist){
            foodList= filterlist;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return foodList.size();
        }
    }
}
