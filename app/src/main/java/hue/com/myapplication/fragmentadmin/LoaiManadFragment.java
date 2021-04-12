package hue.com.myapplication.fragmentadmin;


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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import hue.com.myapplication.LoginActivity;
import hue.com.myapplication.MainActivity;
import hue.com.myapplication.R;
import hue.com.myapplication.adapter.RecyclerView_LoaiMon;
import hue.com.myapplication.adapter.RecyclerView_Type;
import hue.com.myapplication.model.Category;

import static com.google.firebase.storage.FirebaseStorage.getInstance;


public class LoaiManadFragment extends Fragment {
    Dialog dialog;
    Button btnthemlmaad;
    RecyclerView recyclerlmaad;
    ImageView imagefoodlma,logout;
    EditText edtTenLoailma;
    Button btnCancellma,btnOKlma;
    ProgressDialog mProgressDialog;
    int REQUEST_CODE_FOLDER=432;
    FirebaseDatabase database;
    DatabaseReference category;
    //folder path for firebase
    String mStoragePath=" All_Image_upload/";
    //creating Storageference and DataBase referrence
    StorageReference mStorageReference;
    //Creat URI
    Uri mFilePathUri;
    String image1,categoryid,name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_loai_manad, container, false);
        btnthemlmaad= view.findViewById(R.id.btnthemlmaad);
        logout=view.findViewById(R.id.logout);
        recyclerlmaad= view.findViewById(R.id.recyclerlmaad);
        //progress dialog
        mProgressDialog = new ProgressDialog(getContext());
        database= FirebaseDatabase.getInstance();
        category= database.getReference("Category");
        mStorageReference= getInstance().getReference();
        //progress dialog
        mProgressDialog = new ProgressDialog(getContext());
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
                builder.setMessage("Bạn có chắc muốn đăng xuất khỏi ứng dụng !!!");
                builder.setCancelable(false);

                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Toast.makeText(getContext(), "Đăng xuất không thành công ",
                                Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Đăng xuất thành công ",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });

        btnthemlmaad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();
            }
        });
        recyclerlmaad.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerlmaad.setLayoutManager(layoutManager);
        loadlm();
        return view;
    }

    public void dialog(){
         dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_themloaima);
        imagefoodlma= dialog.findViewById(R.id.imagefoodlma);
        edtTenLoailma= dialog.findViewById(R.id.edtTenLoailma);
        btnCancellma= dialog.findViewById(R.id.btnCancellma);
        btnOKlma= dialog.findViewById(R.id.btnOKlma);

        int width = (int) (getContext().getResources().getDisplayMetrics().widthPixels * 1);
        int height = (int) (getContext().getResources().getDisplayMetrics().heightPixels * 0.7);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        btnCancellma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        imagefoodlma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent();
                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"select image")
                        ,REQUEST_CODE_FOLDER);
            }
        });
        btnOKlma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadDaTatoFirebase();


            }
        });
    }

    public void uploadDaTatoFirebase(){
        if(mFilePathUri != null){

            if (edtTenLoailma.getText().toString().isEmpty()){
                Toast.makeText(getContext(), "Tên Không Bỏ Trống", Toast.LENGTH_SHORT).show();
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
                                String tentl= edtTenLoailma.getText().toString();
                                String categoryid= category.push().getKey();
                                mProgressDialog.dismiss();
                                Category theLoai= new Category(categoryid,tentl,dowload.toString());
                                //getting image uload id
                                String imageuploadId= category.push().getKey();
                                // add image upload child element into databaser
                                category.child(imageuploadId).setValue(theLoai);

                                dialog.dismiss();
                                Toast.makeText(getContext(), "Đã Thêm", Toast.LENGTH_SHORT).show();
                            }
                        });



                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                mProgressDialog.dismiss();
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })

                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                                mProgressDialog.setTitle("Images is upload");

                            }
                        });
            }

        }

        else {
            Toast.makeText(getContext(), "please select...", Toast.LENGTH_SHORT).show();
        }
    }
    // method to get selected image extension from file path uri
    private String getFileExtension(Uri uri) {

        ContentResolver contentResolver= getContext().getContentResolver();
        MimeTypeMap mimeTypeMap= MimeTypeMap.getSingleton();
        //returning the file extension
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_FOLDER
                && resultCode== getActivity().RESULT_OK
                && data != null
                && data.getData()!= null){
            mFilePathUri= data.getData();
            try {
                //getting select media
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContext().getContentResolver()
                        ,mFilePathUri);

                //getting select image intobitmap
                imagefoodlma.setImageBitmap(bitmap);
            }catch (Exception e){
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void loadlm(){
        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(category, Category.class).build();

        FirebaseRecyclerAdapter<Category, RecyclerView_LoaiMon> firebaseRecyclerAdapter=new
                FirebaseRecyclerAdapter<Category, RecyclerView_LoaiMon>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RecyclerView_LoaiMon holder, int position,
                                            @NonNull Category model) {
                holder.setDetails(getContext().getApplicationContext(),model.getCategoryid()
                        ,model.getName(),model.getImage());
            }

            @NonNull
            @Override
            public RecyclerView_LoaiMon onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_addlma,
                        parent, false);
                    RecyclerView_LoaiMon viewholder= new RecyclerView_LoaiMon(view);
                final ImageView imgxoalma = view.findViewById(R.id.imgxoalma);
              viewholder.setOnClickListener(new RecyclerView_LoaiMon.ClickListener() {
                  @Override
                  public void onItemClick(View view, int position) {

                      final   String categoryid = getItem(position).getCategoryid();
                      final String name = getItem(position).getName();
                      final String image = getItem(position).getImage();

                      imgxoalma.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                            showdeleteDialog(categoryid,name,image);
                          }
                      });
                  }

                  @Override
                  public void onItemLongClick(View view, int postion) {
                      final Dialog dialog = new Dialog(getContext());
                      dialog.setContentView(R.layout.dialog_themloaima);
                      imagefoodlma= dialog.findViewById(R.id.imagefoodlma);
                      edtTenLoailma= dialog.findViewById(R.id.edtTenLoailma);
                      btnCancellma= dialog.findViewById(R.id.btnCancellma);
                      btnOKlma= dialog.findViewById(R.id.btnOKlma);

                      int width = (int) (getContext().getResources().getDisplayMetrics().widthPixels * 1);
                      int height = (int) (getContext().getResources().getDisplayMetrics().heightPixels * 0.65);
                      dialog.getWindow().setLayout(width, height);
                      dialog.show();
                        btnCancellma.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                      btnOKlma.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                            if (edtTenLoailma.getText().toString().isEmpty()){
                                Toast.makeText(getContext(), "Không Bỏ Trống", Toast.LENGTH_SHORT).show();
                            }else {
                                beginUpdate();
                                dialog.dismiss();
                            }

                          }
                      });
                      imagefoodlma.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                              Intent intent= new Intent();
                              intent.setType("image/*");

                              intent.setAction(Intent.ACTION_GET_CONTENT);
                              startActivityForResult(Intent.createChooser(intent,"select image")
                                      ,REQUEST_CODE_FOLDER);
                          }
                      });


                        categoryid = getItem(postion).getCategoryid();
                        name = getItem(postion).getName();
                       image1 = getItem(postion).getImage();

                      edtTenLoailma.setText(name);
                      Picasso.get().load(image1).into(imagefoodlma);

                  }
              });
                return viewholder;
            }

        };

        recyclerlmaad.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
        firebaseRecyclerAdapter.notifyDataSetChanged();
    }


    public void showdeleteDialog(final String categoryid,final String name,final String image){
        AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Are you sure to delete this?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Query mquery = category.orderByChild("categoryid").equalTo(categoryid);
                mquery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(getContext(), "đã xóa", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

                mProgressDialog.dismiss();

            }
        });
    }
//
    private void uploadNewImage() {

        String imagename= System.currentTimeMillis()+ ".png";
        StorageReference storageReference2= mStorageReference.child(mStoragePath+imagename);
        //get bitmap from img
        Bitmap bitmap = ((BitmapDrawable)imagefoodlma.getDrawable()).getBitmap();
        ByteArrayOutputStream baos= new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
        byte[]data= baos.toByteArray();
        UploadTask uploadTask= storageReference2.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getActivity(), "new image", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressDialog.dismiss();
            }
        });

    }

    private void updatedatabas(final String s) {
        final String name1= edtTenLoailma.getText().toString();

        Query query= category.orderByChild("categoryid").equalTo(categoryid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    ds.getRef().child("name").setValue(name1);
                    ds.getRef().child("image").setValue(s);
                    ds.getRef().child("categoryid").setValue(categoryid);
                }

                mProgressDialog.dismiss();
                Toast.makeText(getContext(), "update tc", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
