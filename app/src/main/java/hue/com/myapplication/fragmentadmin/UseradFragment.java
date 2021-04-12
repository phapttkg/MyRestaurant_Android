package hue.com.myapplication.fragmentadmin;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hue.com.myapplication.LoginActivity;
import hue.com.myapplication.R;
import hue.com.myapplication.TrangChu2Activity;
import hue.com.myapplication.activityadmin.Showinfor_UsadActivity;
import hue.com.myapplication.adapter.RecyclerView_User;
import hue.com.myapplication.model.User;

public class UseradFragment extends Fragment {

    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference user;
    ImageView logout;
    CardView cardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_userad, container, false);
        database = FirebaseDatabase.getInstance();
        user = database.getReference("User");
        recyclerView = view.findViewById(R.id.recyus);
        cardView = view.findViewById(R.id.cardview_us);
        recyclerView.setHasFixedSize(true);
        logout=view.findViewById(R.id.logout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        loaddata();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
                builder.setMessage("Bạn có chắc muốn đăng xuất khỏi ứng dụng !!!");
                builder.setCancelable(false);

                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Toast.makeText(getContext(), "Đăng xuất không thành công ", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Đăng xuất thành công ", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });



        return view;
    }
    public void loaddata() {
        FirebaseRecyclerOptions<User> options = new FirebaseRecyclerOptions.Builder<User>()
                .setQuery(user, User.class).build();
        final FirebaseRecyclerAdapter<User, RecyclerView_User> firebaseRecyclerAdapter = new
                FirebaseRecyclerAdapter<User, RecyclerView_User>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RecyclerView_User holder, int position, @NonNull User model) {
                holder.SetUser(getContext().getApplicationContext(), model.getUsname(),
                        model.getPassword(), model.getFullname(), model.getPhone(),
                        model.getGender(), model.getRuler());
            }

            @NonNull
            @Override
            public RecyclerView_User onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent,
                        false);
                final RecyclerView_User viewholder = new RecyclerView_User(view);
                viewholder.setOnClickListener(new RecyclerView_User.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        final String username = getItem(position).getUsname();
                        final String pass = getItem(position).getPassword();
                        final String fullname = getItem(position).getFullname();
                        final String phone = getItem(position).getPhone();
                        final String gender = getItem(position).getGender();
                        final String ruler = getItem(position).getRuler();

                        Intent intent = new Intent(getContext(), Showinfor_UsadActivity.class);
                        intent.putExtra("tenus",username);
                        intent.putExtra("pass",pass);
                        intent.putExtra("fullname",fullname);
                        intent.putExtra("phone",phone);
                        intent.putExtra("gender",gender);
                        intent.putExtra("ruler",ruler);
                        getActivity().startActivity(intent);


                    }
                });

                return viewholder;
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
        firebaseRecyclerAdapter.notifyDataSetChanged();

    }
}
