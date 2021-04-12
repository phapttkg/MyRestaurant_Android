package hue.com.myapplication.activityadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import hue.com.myapplication.R;
import hue.com.myapplication.adapter.RecyclerView_MonAnad;
import hue.com.myapplication.model.Food;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class MonAnad1Activity extends AppCompatActivity {
    ImageView imagefoodma,imgbackmaad;
    Button btnCancelma,btnOKma,btnthemmaad;
    EditText edtTenMA,edtGiaBanma;
    RecyclerView recyclermaad;
    int REQUEST_CODE_FOLDER=432;
    FirebaseDatabase database;
    DatabaseReference food;
    DatabaseReference category;
    String tl,foodid, tenloai;
    String categorid,categoriid;
    String image1;
    //folder path for firebase
    String mStoragePath=" All_Image_upload/";
    //creating Storageference and DataBase referrence
    StorageReference mStorageReference;
    //Creat URI
    Uri mFilePathUri;
    ProgressDialog mProgressDialog;
    TextView tvLoaiMAad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_an1);

        btnthemmaad= findViewById(R.id.btnthemmaad);
        tvLoaiMAad=findViewById(R.id.tvLoaiMAad);
        recyclermaad= findViewById(R.id.recyclermaad);
        imgbackmaad= findViewById(R.id.imgbackmaad);

        mProgressDialog = new ProgressDialog(this);
        database= FirebaseDatabase.getInstance();
        category = database.getReference();
        food= database.getReference("Food");
        mStorageReference= getInstance().getReference();
        recyclermaad.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager
                .VERTICAL, false);
        recyclermaad.setLayoutManager(layoutManager);

        imgbackmaad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tenloai=getIntent().getStringExtra("tenl");
        tvLoaiMAad.setText(tenloai);
        if(getIntent()!=null)
            categoriid= getIntent().getStringExtra("categoryid");
        if(!categoriid.isEmpty()&& categoriid!=null){
            loadma(categoriid);
        }

        btnthemmaad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();
            }
        });

        loadma(categoriid);

    }

    public void dialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_addma);
        imagefoodma = dialog.findViewById(R.id.imagefoodma);
        edtTenMA = dialog.findViewById(R.id.edtTenMA);
        edtGiaBanma = dialog.findViewById(R.id.edtGiaBanma);
        btnOKma = dialog.findViewById(R.id.btnOKma);
        btnCancelma = dialog.findViewById(R.id.btnCancelma);

        int width = (int) (getResources().getDisplayMetrics().widthPixels * 1);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.8);
        dialog.getWindow().setLayout(width, height);
        dialog.show();


        btnCancelma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        imagefoodma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent();
                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"select image"),
                        REQUEST_CODE_FOLDER);
            }
        });
        btnOKma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // showid();
                uploadDaTatoFirebase();

            }


            public void uploadDaTatoFirebase(){
                if(mFilePathUri != null){



                    if(edtTenMA.getText().toString().isEmpty()){
                        Toast.makeText(MonAnad1Activity.this, "Tên Món Ăn Không bỏ trống",
                                Toast.LENGTH_SHORT).show();
                    } else if (edtGiaBanma.getText().toString().isEmpty()) {
                        Toast.makeText(MonAnad1Activity.this, "Giá Không Bỏ Trống",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        //setting progress bar title
                        mProgressDialog.setTitle("Image is uplad..");
                        //show dialg
                        mProgressDialog.show();
                        //create second storageRefence
                        final StorageReference storageReference2nd= mStorageReference.child(mStoragePath +
                                System.currentTimeMillis()+ "." + getFileExtension(mFilePathUri));
                        //adding addOnSucessListener to StorafeReference
                        storageReference2nd.putFile(mFilePathUri).addOnSuccessListener(new OnSuccessListener
                                <UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                storageReference2nd.getDownloadUrl().addOnSuccessListener(new OnSuccessListener
                                        <Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        final Uri dowload= uri;
                                        //get title
                                        try {
                                            String tenma= edtTenMA.getText().toString();
                                            String categoryid= categoriid;
                                            String foodid= food.push().getKey();
                                            int gia= Integer.parseInt(edtGiaBanma.getText().toString());
                                            mProgressDialog.dismiss();
                                            //Toast.makeText(MonAnad1Activity.this, "Images up load", Toast.LENGTH_SHORT).show();
                                            Food foods= new Food(foodid,categoryid,tenma,gia,dowload.toString());
                                            //getting image uload id
                                            String imageupId= food.push().getKey();
                                            // add image upload child element into databaser
                                            food.child(imageupId).setValue(foods);
                                            Toast.makeText(MonAnad1Activity.this, "Đã Thêm", Toast.LENGTH_SHORT).show();

                                            dialog.dismiss();
                                        }catch (Exception e){
                                            Toast.makeText(MonAnad1Activity.this, "nhap so", Toast.LENGTH_SHORT).show();
                                            mProgressDialog.dismiss();
                                        }



                                    }
                                });



                            }
                        })

                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        mProgressDialog.dismiss();
                                        Toast.makeText(MonAnad1Activity.this, e.getMessage(),
                                                Toast.LENGTH_SHORT).show();

                                    }
                                })

                                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                                        mProgressDialog.setTitle("Images is upload");
                                        dialog.dismiss();

                                    }
                                });
                    }


                }

                else {
                    Toast.makeText(MonAnad1Activity.this, "please select...",
                            Toast.LENGTH_SHORT).show();
                }
            }
//            public void showid() {
//                tl= spnLoaimaad.getSelectedItem().toString();
//                category.child("Category").orderByChild("name").equalTo(tl).addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot addressSnapshot : dataSnapshot.getChildren()) {
//                            Category s = dataSnapshot.getChildren().iterator().next().getValue(Category.class);
//                             categorid = s.getCategoryid();
//                            //Toast.makeText(getContext(), "" + categorid, Toast.LENGTH_SHORT).show();
//
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError error) {
//                    }
//
//
//                });
//
//            }
//

            // method to get selected image extension from file path uri
            private String getFileExtension(Uri uri) {

                ContentResolver contentResolver= getContentResolver();
                MimeTypeMap mimeTypeMap= MimeTypeMap.getSingleton();
                //returning the file extension
                return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
            }


        });
//        category.child("Category").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                final List<String> lmlist = new ArrayList<String>();
//
//
//                for (DataSnapshot addressSnapshot : dataSnapshot.getChildren()) {
//                    String tentl = addressSnapshot.child("name").getValue(String.class);
//                    if (tentl != null) {
//                        lmlist.add(tentl);
//                    }
//                }
//
//                spnLoaimaad = dialog.findViewById(R.id.spnLoaimaad);
//                ArrayAdapter<String> addressAdapter = new ArrayAdapter<String>(MonAnad1Activity.this, android.R.layout.simple_spinner_item, lmlist);
//                addressAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spnLoaimaad.setAdapter(addressAdapter);
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_FOLDER
                && resultCode== RESULT_OK
                && data != null
                && data.getData()!= null){
            mFilePathUri= data.getData();
            try {
                //getting select media
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),mFilePathUri);

                //getting select image intobitmap
                imagefoodma.setImageBitmap(bitmap);
            }catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void loadma(String categoriid){
        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(food.orderByChild("categorid").equalTo(categoriid), Food.class).build();

        FirebaseRecyclerAdapter<Food, RecyclerView_MonAnad> firebaseRecyclerAdapter=new
                FirebaseRecyclerAdapter<Food, RecyclerView_MonAnad>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RecyclerView_MonAnad holder, int position,
                                            @NonNull Food model) {
                holder.setDetails(getApplicationContext(),model.getFoodid(),model.getCategorid()
                        ,model.getFoodname(),model.getPrice(),model.getFoodimage());
            }

            @NonNull
            @Override
            public RecyclerView_MonAnad onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adma,
                        parent, false);
                RecyclerView_MonAnad viewholder= new RecyclerView_MonAnad(view);
                final ImageView imgxoama = view.findViewById(R.id.imgxoama);

                viewholder.setOnClickListener(new RecyclerView_MonAnad.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        final   String foodid = getItem(position).getFoodid();
                        final   String categoryid = getItem(position).getCategorid();
                        final String foodname = getItem(position).getFoodname();
                        final int price = getItem(position).getPrice();
                        final String image = getItem(position).getFoodimage();

                        imgxoama.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                showdeleteDialog(foodid,categoryid,foodname,price,image);
                            }
                        });
                    }

                    @Override
                    public void onItemLongClick(View view, int postion) {
                        final Dialog dialog = new Dialog(MonAnad1Activity.this);


                        dialog.setContentView(R.layout.dialog_addma);
                        imagefoodma = dialog.findViewById(R.id.imagefoodma);
                        edtTenMA = dialog.findViewById(R.id.edtTenMA);
                        edtGiaBanma = dialog.findViewById(R.id.edtGiaBanma);
                        btnOKma = dialog.findViewById(R.id.btnOKma);
                        btnCancelma = dialog.findViewById(R.id.btnCancelma);

                        int width = (int) (getResources().getDisplayMetrics().widthPixels * 1);
                        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.7);
                        dialog.getWindow().setLayout(width, height);
                        dialog.show();
                        btnCancelma.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                        btnOKma.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (edtTenMA.getText().toString().isEmpty()){
                                    Toast.makeText(MonAnad1Activity.this, "Tên Không Bỏ Trống",
                                            Toast.LENGTH_SHORT).show();
                                }else if (edtGiaBanma.getText().toString().isEmpty()){
                                    Toast.makeText(MonAnad1Activity.this, "Giá Không Bỏ Trống",
                                            Toast.LENGTH_SHORT).show();
                                }else {
                                    beginUpdate();
                                    dialog.dismiss();
                                }

                            }
                        });
                        imagefoodma.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent= new Intent();
                                intent.setType("image/*");

                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent,"select image")
                                        ,REQUEST_CODE_FOLDER);
                            }
                        });
                        String foodname= getItem(postion).getFoodname();
                        int gia=  getItem(postion).getPrice();
                        image1 = getItem(postion).getFoodimage();
                        foodid= getItem(postion).getFoodid();
                        edtTenMA.setText(foodname);
                        edtGiaBanma.setText(gia+"");
                        Picasso.get().load(image1).into(imagefoodma);


                    }
                });

                return viewholder;
            }


        };

        recyclermaad.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
        firebaseRecyclerAdapter.notifyDataSetChanged();
    }

    public void showdeleteDialog(final String foodid,final String categorid,final String foodname,
                                 final int price,final String foodimage){
        AlertDialog.Builder builder= new AlertDialog.Builder(MonAnad1Activity.this);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure to delete this?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Query mquery = food.orderByChild("foodid").equalTo(foodid);
                mquery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(MonAnad1Activity.this, "đã xóa", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(MonAnad1Activity.this, databaseError.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.create().show();


    }

    public void beginUpdate(){

        mProgressDialog.setMessage("updating..");
        mProgressDialog.show();
        deletpreviuosImge();

    }

    public void deletpreviuosImge(){
        StorageReference mPictrueRe= getInstance().getReferenceFromUrl(image1);
        mPictrueRe.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                uploadNewImage();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MonAnad1Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                mProgressDialog.dismiss();

            }
        });
    }

    private void uploadNewImage() {

        String imagename= System.currentTimeMillis()+ ".png";
        StorageReference storageReference2= mStorageReference.child(mStoragePath+imagename);
        //get bitmap from img
        Bitmap bitmap = ((BitmapDrawable)imagefoodma.getDrawable()).getBitmap();
        ByteArrayOutputStream baos= new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
        byte[]data= baos.toByteArray();
        UploadTask uploadTask= storageReference2.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(MonAnad1Activity.this, "new image", Toast.LENGTH_SHORT).show();

                //get url of new upload image
                Task<Uri> uriTask= taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful());
                Uri dowloadUri= uriTask.getResult();

                //upload datawwith new data
                updatedatabas(dowloadUri.toString());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MonAnad1Activity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressDialog.dismiss();
            }
        });

    }


    private void updatedatabas(final String s) {
        final String name1= edtTenMA.getText().toString();
        final String foodid1= food.push().getKey();
        final int gia= Integer.parseInt(edtGiaBanma.getText().toString());
        Query query= food.orderByChild("foodid").equalTo(foodid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    ds.getRef().child("foodname").setValue(name1);
                    ds.getRef().child("foodimage").setValue(s);
                    ds.getRef().child("price").setValue(gia);
                    ds.getRef().child("foodid").setValue(foodid);
                }

                mProgressDialog.dismiss();
                Toast.makeText(MonAnad1Activity.this, "update tc", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
