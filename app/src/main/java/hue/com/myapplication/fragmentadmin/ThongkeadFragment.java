package hue.com.myapplication.fragmentadmin;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import hue.com.myapplication.LoginActivity;
import hue.com.myapplication.R;
import hue.com.myapplication.adapter.RecyclerView_ThongKe_HD;
import hue.com.myapplication.adapter.RecyclerView_Thongke_User;
import hue.com.myapplication.adapter.RecyclerView_Tke;
import hue.com.myapplication.model.Bill;
import hue.com.myapplication.model.Billdetail;


import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;


public class ThongkeadFragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference bill;
    TextView tvNgay,tvThang,tvNam;
    DatabaseReference mData,food;
    Bill b;
    String hientai;
    ArrayList<Bill> list;
    ArrayList<Bill> list2;
    List<Billdetail> fn;
    List<Billdetail> tenma;
    ArrayList<String> ten;
    ArrayList<Integer> sl;
    ArrayList<String> listuser;
    ArrayList<Integer> listtotal;
    ImageView logout;
    Map<String, Integer> sum;
    Map<String, Integer> sumus;
    RecyclerView_ThongKe_HD adapter1;
    RecyclerView_Tke.RecyclerAdapter adapter;
    RecyclerView_Thongke_User.RecyclerAdapter adapter2;
    RecyclerView recyclersale,recyhd,recyuser;
    Map<String, Integer> sortedMap;
    Map<String, Integer> UserMap;
    String k,us;
    int v,total1;
    String ngaymua;
    String hientaingay;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_thongkead, container, false);
        logout=view.findViewById(R.id.logout);
        recyclersale=view.findViewById(R.id.recyclersale);
        recyhd=view.findViewById(R.id.recyhd);
        recyuser=view.findViewById(R.id.recyuser);
        tvNgay=view.findViewById(R.id.tvNgay);
        tvThang=view.findViewById(R.id.tvthang);
        tvNam=view.findViewById(R.id.tvNam);
        mData= FirebaseDatabase.getInstance().getReference();
        database= FirebaseDatabase.getInstance();
        fn=new ArrayList<Billdetail>();
        tenma=new ArrayList<Billdetail>();
        list= new ArrayList<Bill>();
        list2= new ArrayList<Bill>();
        bill= database.getReference("Bill");
        food= database.getReference("Food");
        recyclersale.setHasFixedSize(true);
        recyhd.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);

        recyclersale.setLayoutManager(layoutManager);
        recyhd.setLayoutManager(layoutManager1);
        recyuser.setLayoutManager(layoutManager2);
        tknam();
        tkngay();
        tkthang();
        tkmonan();
        tkhd();
        tkuser();

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


    public void tkngay(){
        mData.child("Bill").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int sum = 0;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    b = data.getValue(Bill.class);
                    ngaymua = b.getDate();
                    getCurrentDate();

                    String ngaynay = ngaymua;
                    hientaingay = hientai;
                    if (ngaynay.equalsIgnoreCase(hientaingay)) {
                        int TT = (((data.child("total").getValue(Integer.class))));
                        sum = sum + TT;
                        tvNgay.setText(""+sum);
                    } else {
                        tvNgay.setText(""+sum);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void tkthang(){

        mData.child("Bill").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int sum = 0;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    b = data.getValue(Bill.class);
                    String thangmua = b.getDate();
                    getCurrentDate();

                    String thangnay = thangmua.substring(3);
                    String hientaithang = hientai.substring(3);
                    if (thangnay.equalsIgnoreCase(hientaithang)) {
                        int TT = ((data.child("total").getValue(Integer.class)));
                        sum = sum + TT;
                        tvThang.setText(""+sum);
                    } else {
                        tvThang.setText(""+sum);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void tknam(){

        mData.child("Bill").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int sum = 0;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    b = data.getValue(Bill.class);
                    String nammua = b.getDate();
                    getCurrentDate();
                    String namnay = nammua.substring(6);
                    String hientainam = hientai.substring(6);
                    if (namnay.equalsIgnoreCase(hientainam)) {
                        int TT = (((data.child("total").getValue(Integer.class))));
                        sum = sum + TT;
                      tvNam.setText(""+sum);
                    } else {
                      tvNam.setText(""+sum);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void tkhd(){
        mData.child("Bill").orderByChild("total").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int sum=0;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    b = data.getValue(Bill.class);
                    list.add(b);

                }
                Collections.reverse(list);
                adapter1 = new RecyclerView_ThongKe_HD(getContext(), list);
                recyhd.setAdapter(adapter1);
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void getCurrentDate(){

        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = sdf.format(calendar1.getTime());
        hientai=currentDate;
    };


    public void tkuser(){
       bill.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for (DataSnapshot childDataSnapshot:dataSnapshot.getChildren()){
                       Bill bills = childDataSnapshot.getValue(Bill.class);
                       list2.add(bills);

               }

               sumus= list2.stream().collect(
                       Collectors.groupingBy(Bill::getUsername, Collectors.summingInt(Bill::getTotal)));

               List<Map.Entry<String, Integer>> entries = new LinkedList<Map.Entry<String, Integer>>(sumus.entrySet());

               Comparator<Map.Entry<String, Integer>> valueComparator = new Comparator<Map.Entry<String,
                       Integer>>() {
                   @Override
                   public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                       return o1.getValue().compareTo(o2.getValue());
                   }
               };
               Collections.sort(entries, Map.Entry.comparingByValue(Comparator.reverseOrder()));
               listuser=new ArrayList<String>();
               listtotal=new ArrayList<Integer>();
               UserMap = new LinkedHashMap<String, Integer>();
               for (Map.Entry<String, Integer> entry : entries) {
                   UserMap.put(entry.getKey(), entry.getValue());
                   us= entry.getKey();
                   total1= entry.getValue();
                   listuser.add(us);
                   listtotal.add(total1);

               }
               adapter2= new RecyclerView_Thongke_User.RecyclerAdapter(getContext(),listuser,listtotal);
               adapter2.notifyDataSetChanged();
               recyuser.setAdapter(adapter2);

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
    }

    public void tkmonan(){


    bill.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                if (childDataSnapshot.child("foods").getValue() != null) {

                    for (DataSnapshot data : childDataSnapshot.child("foods").getChildren()) {

                            Billdetail model = data.getValue(Billdetail.class);
                            tenma.add(model);

//                        int qt= data.child("quantity").getValue(int.class);
//                        String ten = data.child("foodname").getValue(String.class);
//                        arrs.add(ten);
//                        q.add(String.valueOf(qt));

                    }

                }

                sum= tenma.stream().collect(
                        Collectors.groupingBy(Billdetail::getFoodname,
                                Collectors.summingInt(Billdetail::getQuantity)));

//                List<String> sorted = sum.entrySet().stream()
//                        .sorted(reverseOrder(comparing(Map.Entry::getValue)))
//                        .map(Map.Entry::getKey)
//                        .collect(toList());
//                Toast.makeText(getContext(), ""+sorted, Toast.LENGTH_SHORT).show();

                List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(sum.entrySet());

                Comparator<Map.Entry<String, Integer>> valueComparator = new Comparator<Map.Entry<String,
                        Integer>>() {
                    @Override
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                        return o1.getValue().compareTo(o2.getValue());
                    }
                };
                Collections.sort(list, Map.Entry.comparingByValue(Comparator.reverseOrder()));
                ten=new ArrayList<String>();
                sl=new ArrayList<Integer>();
                sortedMap = new LinkedHashMap<String, Integer>();
                for (Map.Entry<String, Integer> entry : list) {
                    sortedMap.put(entry.getKey(), entry.getValue());
                     k= entry.getKey();
                     v= entry.getValue();
                    ten.add(k);
                    sl.add(v);

                }
                adapter= new RecyclerView_Tke.RecyclerAdapter(getContext(),ten,sl);
                adapter.notifyDataSetChanged();
                recyclersale.setAdapter(adapter);

            }


        }


        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });

}


    }


