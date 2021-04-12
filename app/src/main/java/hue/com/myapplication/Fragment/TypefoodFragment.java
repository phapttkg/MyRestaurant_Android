package hue.com.myapplication.Fragment;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Locale;

import hue.com.myapplication.activityuser.MonAnActivity;
import hue.com.myapplication.R;
import hue.com.myapplication.adapter.RecyclerView_Type;
import hue.com.myapplication.model.Category;

import static android.app.Activity.RESULT_OK;


public class TypefoodFragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference category;
    Button btnsearchlma,btnMic;
    EditText edtSearchlma;
    String searchtext;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    RecyclerView ryclelma;
    FirebaseRecyclerAdapter<Category, RecyclerView_Type> firebaseRecyclerAdapte;
    public TypefoodFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_typefood, container, false);
        btnsearchlma=view.findViewById(R.id.btnsearchlma);
        edtSearchlma=view.findViewById(R.id.edtSearchlma);
        btnMic=view.findViewById(R.id.btnMic);
        ryclelma= view.findViewById(R.id.ryclelma);
        database= FirebaseDatabase.getInstance();
        category= database.getReference("Category");
        ryclelma.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        ryclelma.setLayoutManager(new GridLayoutManager(getContext(),2));
        loadtl();
        btnsearchlma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String test = edtSearchlma.getText().toString();
                searchtext=test.substring(0,1).toUpperCase()+test.substring(1);

                if(edtSearchlma.getText().toString().length()==0){
                    loadtl();
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
        return view;
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
                    edtSearchlma.setText(result.get(0));
                }
                break;
            }
        }
    }

    public void loadtl(){
        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(category, Category.class).build();

        firebaseRecyclerAdapte=new FirebaseRecyclerAdapter<Category, RecyclerView_Type>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RecyclerView_Type holder, int position, @NonNull Category model) {
                holder.setDetails(getContext().getApplicationContext(),model.getCategoryid(),
                        model.getName(),model.getImage());
            }


            @NonNull
            @Override
            public RecyclerView_Type onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent,
                        false);
                ImageView imagelm = view.findViewById(R.id.imagelm);
                TextView tvLoaiMA= view.findViewById(R.id.tvLoaiMA);
                RecyclerView_Type viewholder= new RecyclerView_Type(view);

                viewholder.setOnClickListener(new RecyclerView_Type.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        final   String categoryid = getItem(position).getCategoryid();
                        String tenloai= getItem(position).getName();
                        Intent foodlist= new Intent(getContext(), MonAnActivity.class);
                        foodlist.putExtra("tenl",tenloai);
                        foodlist.putExtra("categoryid",categoryid);
                        //  String k= firebaseRecyclerAdapte.getRef(position).getKey();
                        startActivity(foodlist);
                    }

                    @Override
                    public void onItemLongClick(View view, int postion) {

                    }
                });

                return viewholder;
            }

        };

        ryclelma.setAdapter(firebaseRecyclerAdapte);
        firebaseRecyclerAdapte.startListening();
        firebaseRecyclerAdapte.notifyDataSetChanged();
    }


    public void search(String searchText){
        Query firebaseSearch= category.orderByChild("name").startAt(searchText).endAt(searchText+"\uf8ff");

        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(category.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff"),
                        Category.class).build();

        FirebaseRecyclerAdapter<Category, RecyclerView_Type> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Category, RecyclerView_Type>
                (options) {
            @Override
            protected void onBindViewHolder(@NonNull RecyclerView_Type holder, int position, @NonNull Category model) {
                holder.setDetails(getContext().getApplicationContext(),
                        model.getCategoryid(),model.getName(),model.getImage());
            }

            @NonNull
            @Override
            public RecyclerView_Type onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent,
                        false);
                RecyclerView_Type viewholder= new RecyclerView_Type(view);


                viewholder.setOnClickListener(new RecyclerView_Type.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        final   String categoryid = getItem(position).getCategoryid();
                        String tenloai= getItem(position).getName();
                        Intent foodlist= new Intent(getContext(), MonAnActivity.class);
                        foodlist.putExtra("tenl",tenloai);
                        foodlist.putExtra("categoryid",categoryid);
                        //  String k= firebaseRecyclerAdapte.getRef(position).getKey();

                        startActivity(foodlist);
                    }

                    @Override
                    public void onItemLongClick(View view, int postion) {

                    }
                });
                return viewholder;
            }

        };

        ryclelma.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
        firebaseRecyclerAdapter.notifyDataSetChanged();
    }

}
