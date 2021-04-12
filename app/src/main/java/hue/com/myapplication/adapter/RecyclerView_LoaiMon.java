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

public class RecyclerView_LoaiMon extends RecyclerView.ViewHolder {
    public RecyclerView_LoaiMon(@NonNull View itemView) {
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
    private RecyclerView_LoaiMon.ClickListener mClickListenner;

    public interface ClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view, int postion);
    }
    public void setOnClickListener(RecyclerView_LoaiMon.ClickListener clickListener) {
        mClickListenner= clickListener;

    }
    public void setDetails(Context context, String categoryid, String name, String image){
        ImageView imagefoodlma = itemView.findViewById(R.id.imagefoodlma);
        TextView tvtenlma= itemView.findViewById(R.id.tvtenlma);
        ImageView imgxoalma = itemView.findViewById(R.id.imgxoalma);

        tvtenlma.setText(name);
        Picasso.get().load(image).into(imagefoodlma);
    }

    public static  class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView_Type>{
        private Context context;
        private List<Category> categoryList= new ArrayList<>();

        public RecyclerAdapter(Context context, List<Category> categoryList) {
            this.context = context;
            this.categoryList = categoryList;
        }

        @NonNull
        @Override
        public RecyclerView_Type onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater= LayoutInflater.from(parent.getContext());
            View itemView= inflater.inflate(R.layout.item_addlma,parent,false);
            return new RecyclerView_Type(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView_Type holder, int position) {

        }

        @Override
        public int getItemCount() {
            return categoryList.size();
        }
    }
}
