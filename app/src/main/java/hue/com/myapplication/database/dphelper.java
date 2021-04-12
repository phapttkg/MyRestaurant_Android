package hue.com.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dphelper extends SQLiteOpenHelper {

    public static String TB_HDCT="HDCT";


    public static String TB_HDCT_billid="ID";
    public static String TB_HDCT_foodid="FOODID";
    public static String TB_HDCT_foodname="FOONAME";
    public static String TB_HDCT_gia="PRICE";
    public static String TB_HDCT_SOLUONG="QUANTITY";
    public static String TB_HDCT_image="IMAGE";


    public dphelper(Context context)  {
        super(context, "billdetil", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String hdct="CREATE TABLE "+TB_HDCT + " ( " + TB_HDCT_billid+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_HDCT_foodid+" TEXT, "
                +TB_HDCT_foodname+" TEXT, "
                +TB_HDCT_gia+" INTEGER, "
                +TB_HDCT_SOLUONG +" INTEGER, "
                + TB_HDCT_image + " TEXT )";








        db.execSQL(hdct);


    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public SQLiteDatabase open(){
        return this.getWritableDatabase();
    }
}
