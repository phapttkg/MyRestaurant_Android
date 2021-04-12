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

public class RecyclerView_Tke extends RecyclerView.ViewHolder {
    TextView tvtenmatk,tvsltk;
    ImageView imageMAtk;
    public RecyclerView_Tke(@NonNull View itemView) {
        super(itemView);
        imageMAtk = itemView.findViewById(R.id.imageMAtk);
        tvsltk= itemView.findViewById(R.id.tvsltk);
        tvtenmatk = itemView.findViewById(R.id.tvtenmatk);

    }

    public static  class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView_Tke>{
        private Context context;
        HdctDao hdctDao;

        private List<String> ten;
        private List<Integer> sl;
        public RecyclerAdapter(Context context,List<String> ten,List<Integer>sl) {
            this.context = context;
            this.ten = ten;
            this.sl=sl;
        }
        @NonNull
        @Override
        public RecyclerView_Tke onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater= LayoutInflater.from(parent.getContext());
            View itemView= inflater.inflate(R.layout.item_thongkema,parent,false);
            RecyclerView_Tke viewholder = new RecyclerView_Tke(itemView);
            return viewholder;
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerView_Tke holder, int position) {

            holder.tvtenmatk.setText(ten.get(position));
            holder.tvsltk.setText("Số Lượng: "+sl.get(position));


        }

        @Override
        public int getItemCount() {
            return sl.size();
        }


    }
}
