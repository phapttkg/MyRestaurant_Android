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

public class RecyclerView_MonAnad extends RecyclerView.ViewHolder {

    public RecyclerView_MonAnad(@NonNull View itemView) {
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

    private RecyclerView_MonAnad.ClickListener mClickListenner;

    public interface ClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view, int postion);
    }
    public void setOnClickListener(RecyclerView_MonAnad.ClickListener clickListener) {
        mClickListenner= clickListener;

    }
    public void setDetails(Context context,String foodid, String categoryid, String foodname,
                           int price, String foodimage){
        ImageView imagefoodma = itemView.findViewById(R.id.imagefoodma);
        TextView tvtenma= itemView.findViewById(R.id.tvtenma);
        ImageView imgxoama = itemView.findViewById(R.id.imgxoama);
        TextView tvgiama=itemView.findViewById(R.id.tvgiama);
        tvtenma.setText(foodname);
        tvgiama.setText(price+"  VNĐ");
        Picasso.get().load(foodimage).into(imagefoodma);
    }

    public static  class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView_MonAnad>{
        private Context context;
        private List<Food> foodList= new ArrayList<>();

        public RecyclerAdapter(Context context, List<Food> foodList) {
            this.context = context;
            this.foodList = foodList;
        }

        @NonNull
        @Override
        public RecyclerView_MonAnad onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater= LayoutInflater.from(parent.getContext());
            View itemView= inflater.inflate(R.layout.item_adma,parent,false);
            return new RecyclerView_MonAnad(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView_MonAnad holder, int position) {

        }

        @Override
        public int getItemCount() {
            return foodList.size();
        }
    }
}
