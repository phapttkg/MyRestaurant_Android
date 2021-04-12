package hue.com.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hue.com.myapplication.database.dphelper;
import hue.com.myapplication.model.Billdetail;

public class HdctDao {

    SQLiteDatabase database;

    public HdctDao(Context context) {
        dphelper dphelper = new dphelper(context);
        database = dphelper.open();
    }

    public boolean addcard(Billdetail b){
        ContentValues contentValues= new ContentValues();
        contentValues.put(dphelper.TB_HDCT_foodid,b.getId());
        contentValues.put(dphelper.TB_HDCT_foodname,b.getFoodname());
        contentValues.put(dphelper.TB_HDCT_gia,b.getGia());
        contentValues.put(dphelper.TB_HDCT_SOLUONG,b.getQuantity());
        contentValues.put(dphelper.TB_HDCT_image,b.getImage());

        long kiemtra= database.insert(dphelper.TB_HDCT,null,contentValues);
        if(kiemtra !=0){
            return true;
        }else {
            return false;
        }

    }
    public ArrayList<Billdetail> getall() {
        ArrayList<Billdetail> list = new ArrayList<Billdetail>();
        String truyvan = " SELECT * from " + dphelper.TB_HDCT ;
        Cursor cursor = database.rawQuery(truyvan, null);
        while (cursor.moveToNext()) {
            Billdetail b = new Billdetail();
            int id = cursor.getInt(0);
            String foodid = cursor.getString(1);
            String foodname = cursor.getString(2);
            int price = cursor.getInt(3);
            int soluong = cursor.getInt(4);
            String image = cursor.getString(5);
            list.add(new Billdetail(id,foodid,foodname,price,soluong,image));
        }
        return list;
    }

    public void deletek(Billdetail b){
        database.delete(dphelper.TB_HDCT,dphelper.TB_HDCT_billid +"=?",
                new String[]{String.valueOf(b.getBillid())});
    }
    public void deleteItem(){


        database.delete(dphelper.TB_HDCT, null, null);

    }
}
