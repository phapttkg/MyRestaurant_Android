package hue.com.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hue.com.myapplication.R;
import hue.com.myapplication.model.User;

public class RecyclerView_User extends RecyclerView.ViewHolder {
    public RecyclerView_User(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListenner.onItemClick(view, getAdapterPosition());
            }
        });

    }
    private RecyclerView_User.ClickListener mClickListenner;

    public interface ClickListener {
        void onItemClick(View view, int position);
    }
    public void setOnClickListener(RecyclerView_User.ClickListener clickListener) {
        mClickListenner = clickListener;
    }

    public void SetUser(Context context, String username, String password, String fullname,
                        String phone, String gender, String ruler) {
        TextView tvusername = itemView.findViewById(R.id.tvTenUser);
        TextView tvruler = itemView.findViewById(R.id.tvRuler);
        tvusername.setText(username);
        tvruler.setText(ruler);
    }


    public static class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView_User> {
        private Context context;
        private List<User> users = new ArrayList<>();
        CardView cardView;

        public RecyclerAdapter(Context context, List<User> users) {
            this.context = context;
            this.users = users;
        }

        @NonNull
        @Override
        public RecyclerView_User onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.item_user, parent, false);
            return new RecyclerView_User(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView_User holder, int position) {

        }

        @Override
        public int getItemCount() {
            return users.size();
        }
    }


}

