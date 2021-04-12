package hue.com.myapplication.activityuser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import hue.com.myapplication.R;
import hue.com.myapplication.dao.HdctDao;
import hue.com.myapplication.model.Billdetail;

public class ThongTin_MonAnActivity extends AppCompatActivity {
    ImageView imgbacktt,imagefoodtt,imggiamtt,imggiuatt,imgtangtt;
    TextView tvGiatt,tvSLtt,tvTenMAtt;
    Button btnAddCart;
    int number= 1;
    FirebaseDatabase database;
    DatabaseReference food;
    DatabaseReference billdetail;
    DatabaseReference users;
    String tenma,image,categrid,foodid;
    int gia,total;
        HdctDao hdctDao;
    String billid,a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin__mon_an);
        String a1= String.valueOf(System.currentTimeMillis());
        database= FirebaseDatabase.getInstance();
        food= database.getReference("Food");
        billdetail= database.getReference("Billdetail");
        users=database.getReference("User");
        hdctDao= new HdctDao(this);
        imgbacktt= findViewById(R.id.imgbacktt);
        imagefoodtt= findViewById(R.id.imagefoodtt);
        imggiamtt= findViewById(R.id.imggiamtt);
        imggiuatt= findViewById(R.id.imggiuatt);
        imgtangtt= findViewById(R.id.imgtangtt);
        tvGiatt= findViewById(R.id.tvGiatt);
        tvSLtt= findViewById(R.id.tvSLtt);
        tvTenMAtt= findViewById(R.id.tvTenMAtt);
        btnAddCart= findViewById(R.id.btnAddCart);
        imgbacktt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgtangtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               number= number+1;
               tvSLtt.setText(number+"");
                total= gia*number;
                tvGiatt.setText(total+"  VNĐ");
            }
        });
        imggiamtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(number<=1){
                    number=1;
                    tvSLtt.setText(number+"");
                }else {
                    number= number-1;
                    tvSLtt.setText(number+"");
                    total= gia*number;
                    tvGiatt.setText(total+"  VNĐ");
                }

            }
        });

        tenma=getIntent().getStringExtra("foodname");
        gia= getIntent().getIntExtra("price",0);
        image= getIntent().getStringExtra("foodimage");
        foodid= getIntent().getStringExtra("foodid");
        categrid=getIntent().getStringExtra("categrid");
        tvTenMAtt.setText(tenma);
        tvGiatt.setText(gia+"  VNĐ");
        Picasso.get().load(image).into(imagefoodtt);

        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Billdetail b= new Billdetail();
                b.setId(foodid);
                b.setFoodname(tenma);
                b.setGia(gia);
                b.setQuantity(number);
                b.setImage(image);


                boolean kiemtra= hdctDao.addcard(b);
                if(kiemtra){
                    Toast.makeText(ThongTin_MonAnActivity.this, " đã thêm ", Toast.LENGTH_SHORT).show();
//                    Intent intent= new Intent(ThongTin_MonAnActivity.this,CartActivity.class);
//                    startActivity(intent);

                }else {
                    Toast.makeText(ThongTin_MonAnActivity.this, "that bai", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        btnAddCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                a= UserDao.cruser.getPhone();
//
//                final String a1= String.valueOf(System.currentTimeMillis());
//                 billid= billdetail.push().getKey();
////                Billdetail b= new Billdetail(billid,tenma,gia,number,image);
//                final HashMap<String, Object> cartmap=new HashMap<>();
//                cartmap.put("foodname",tenma);
//                cartmap.put("gia",gia);
//                cartmap.put("billid",billid);
//                cartmap.put("image",image);
//                cartmap.put("quantity",number);
//
//                billdetail.child("User View").child(a).child("Products").child(a1).
//                        updateChildren(cartmap)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful()){
//                            billdetail.child("Admin View").child(a).child("Products").child(a1).
//                                    updateChildren(cartmap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//
//                                        Toast.makeText(ThongTin_MonAnActivity.this, "add to card", Toast.LENGTH_SHORT).show();
//                                        Intent intent= new Intent(ThongTin_MonAnActivity.this,CartActivity.class);
//                                        startActivity(intent);
//                                    }
//                                }
//                            });
//
//                        }
//                    }
//                });
//
//
//
//            }
//        });
    }
}
