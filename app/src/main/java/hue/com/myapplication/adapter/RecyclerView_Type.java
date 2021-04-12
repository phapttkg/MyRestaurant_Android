package hue.com.myapplication.adapter;

import android.content.Context;
import android.media.Image;
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

public class RecyclerView_Type extends RecyclerView.ViewHolder
        {
    public RecyclerView_Type(@NonNull View itemView) {
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

    private RecyclerView_Type.ClickListener mClickListenner;

    public interface ClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view, int postion);
    }
    public void setOnClickListener(RecyclerView_Type.ClickListener clickListener) {
        mClickListenner= clickListener;

    }

    public void setDetails(Context context, String categoryid, String name, String image){
        ImageView imagelm = itemView.findViewById(R.id.imagelm);
        TextView tvLoaiMA= itemView.findViewById(R.id.tvLoaiMA);

        tvLoaiMA.setText(name);
        Picasso.get().load(image).into(imagelm);
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
            View itemView= inflater.inflate(R.layout.item_category,parent,false);
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
