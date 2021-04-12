package hue.com.myapplication.activityuser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Locale;

import hue.com.myapplication.R;
import hue.com.myapplication.adapter.RecyclerView_MonAn;
import hue.com.myapplication.model.Food;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class MonAnActivity extends AppCompatActivity {
    RecyclerView recyma;
    Button btnsearchma, btnMic;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    EditText edtSearchma;
    ImageView imgbackma;
    TextView tvLoaiMA;
    FirebaseDatabase database;
    DatabaseReference food;
    DatabaseReference category;
    String categoriid="";
    String tenloai="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_an);
        recyma=findViewById(R.id.recyma);
        btnsearchma=findViewById(R.id.btnsearchma);
        edtSearchma=findViewById(R.id.edtSearchma);
        btnMic = findViewById(R.id.btnMic);
        imgbackma=findViewById(R.id.imgbackma);
        tvLoaiMA= findViewById(R.id.tvLoaiMA);
        database= FirebaseDatabase.getInstance();
        category = database.getReference();
        food= database.getReference("Food");
        recyma.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyma.setLayoutManager(new GridLayoutManager(this,2));

        imgbackma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tenloai=getIntent().getStringExtra("tenl");
        tvLoaiMA.setText(tenloai);
        if(getIntent()!=null)
            categoriid= getIntent().getStringExtra("categoryid");
            if(!categoriid.isEmpty()&& categoriid!=null){
                loadma(categoriid);
            }
        btnsearchma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchtext= edtSearchma.getText().toString();
                if(edtSearchma.getText().toString().length()==0){
                    loadma(categoriid);
                }else {
                    search(searchtext);
                }

            }
        });
        btnMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeechInput();
            }
        });

    }
    public void loadma(String categoriid){
        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(food.orderByChild("categorid").equalTo(categoriid), Food.class).build();
        FirebaseRecyclerAdapter<Food, RecyclerView_MonAn> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Food, RecyclerView_MonAn>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RecyclerView_MonAn holder, int position, @NonNull Food model) {
                holder.setDetails(getApplicationContext(),model.getFoodid(),model.getCategorid(),
                        model.getFoodname(),model.getPrice(),model.getFoodimage());
            }
            @NonNull
            @Override
            public RecyclerView_MonAn onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ma, parent, false);
                RecyclerView_MonAn viewholder= new RecyclerView_MonAn(view);

                viewholder.setOnClickListener(new RecyclerView_MonAn.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent= new Intent(MonAnActivity.this, ThongTin_MonAnActivity.class);
                        intent.putExtra("foodid",getItem(position).getFoodid());
                        intent.putExtra("foodname",getItem(position).getFoodname());
                        intent.putExtra("foodimage",getItem(position).getFoodimage());
                        intent.putExtra("categrid",getItem(position).getCategorid());
                        intent.putExtra("price",getItem(position).getPrice());
                        startActivity(intent);

                    }

                    @Override
                    public void onItemLongClick(View view, int postion) {

                    }
                });
                return viewholder;
            }

        };

        recyma.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
        firebaseRecyclerAdapter.notifyDataSetChanged();
    }


    public void search(String searchText){

        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(food.orderByChild("foodname").equalTo(searchText), Food.class).build();

        FirebaseRecyclerAdapter<Food, RecyclerView_MonAn> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Food, RecyclerView_MonAn>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RecyclerView_MonAn holder, int position, @NonNull Food model) {
                holder.setDetails(getApplicationContext(),model.getFoodid(),model.getCategorid(),
                        model.getFoodname(),model.getPrice(),model.getFoodimage());
            }
            @NonNull
            @Override
            public RecyclerView_MonAn onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ma, parent, false);
                RecyclerView_MonAn viewholder= new RecyclerView_MonAn(view);
                viewholder.setOnClickListener(new RecyclerView_MonAn.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent= new Intent(MonAnActivity.this,ThongTin_MonAnActivity.class);
                        intent.putExtra("foodid",getItem(position).getFoodid());
                        intent.putExtra("foodname",getItem(position).getFoodname());
                        intent.putExtra("foodimage",getItem(position).getFoodimage());
                        intent.putExtra("categrid",getItem(position).getCategorid());
                        intent.putExtra("price",getItem(position).getPrice());
                        startActivity(intent);

                    }

                    @Override
                    public void onItemLongClick(View view, int postion) {

                    }
                });
                return viewholder;
            }

        };

        recyma.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
        firebaseRecyclerAdapter.notifyDataSetChanged();
    }
    public void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    edtSearchma.setText(result.get(0));
                }
                break;
            }
        }
    }
}
