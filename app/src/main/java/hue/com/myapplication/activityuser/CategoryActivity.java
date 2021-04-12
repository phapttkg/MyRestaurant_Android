package hue.com.myapplication.activityuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hue.com.myapplication.MainActivity;
import hue.com.myapplication.R;
import hue.com.myapplication.adapter.RecyclerView_Type;
import hue.com.myapplication.model.Category;

public class CategoryActivity extends AppCompatActivity {
FirebaseDatabase database;
DatabaseReference category;
ImageView imgback;
EditText edtSearchlm;
Button btnsearchlm;
RecyclerView ryclelma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        imgback= findViewById(R.id.imgback);
        edtSearchlm= findViewById(R.id.edtSearchlm);
        btnsearchlm= findViewById(R.id.btnsearchlm);
        ryclelma= findViewById(R.id.ryclelma);

        database= FirebaseDatabase.getInstance();
        category= database.getReference("Category");
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(CategoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        ryclelma.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        ryclelma.setLayoutManager(layoutManager);
        loadtl();
    }

    public void loadtl(){
        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>().setQuery(category, Category.class).build();

        FirebaseRecyclerAdapter<Category, RecyclerView_Type>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Category, RecyclerView_Type>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RecyclerView_Type holder, int position, @NonNull Category model) {
                holder.setDetails(getApplicationContext(),model.getCategoryid(),model.getName(),model.getImage());
            }

            @NonNull
            @Override
            public RecyclerView_Type onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);


                return new RecyclerView_Type(view);
            }

        };

        ryclelma.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
        firebaseRecyclerAdapter.notifyDataSetChanged();
    }
}
