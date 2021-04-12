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
import java.util.List;

import hue.com.myapplication.R;
import hue.com.myapplication.dao.HdctDao;
import hue.com.myapplication.model.Bill;

public class RecyclerView_Thongke_User extends RecyclerView.ViewHolder {
    TextView tvtenma,tvGia;
    ImageView imageMAtk;
    public RecyclerView_Thongke_User(@NonNull View itemView) {
        super(itemView);
        imageMAtk = itemView.findViewById(R.id.imageMAtk);
        tvtenma= itemView.findViewById(R.id.tvtenma);
        tvGia = itemView.findViewById(R.id.tvGia);

    }

    public static  class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView_Thongke_User>{
        private Context context;
        HdctDao hdctDao;

        private List<String> user;
        private List<Integer> total;
        public RecyclerAdapter(Context context,List<String> user,List<Integer>total) {
            this.context = context;
            this.user = user;
            this.total=total;
        }
        @NonNull
        @Override
        public RecyclerView_Thongke_User onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater= LayoutInflater.from(parent.getContext());
            View itemView= inflater.inflate(R.layout.item_thongke_user,parent,false);
            RecyclerView_Thongke_User viewholder = new RecyclerView_Thongke_User(itemView);


            return viewholder;
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerView_Thongke_User holder, int position) {

            holder.tvtenma.setText(user.get(position));
            holder.tvGia.setText(total.get(position)+"");
        }

        @Override
        public int getItemCount() {
            return total.size();
        }

    }

}
