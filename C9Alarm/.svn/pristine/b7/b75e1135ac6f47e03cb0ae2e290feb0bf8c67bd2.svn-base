package holidaynotifaction;




import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DBHelper extends SQLiteOpenHelper  {  
    private static final String DB_NAME = "Holiday.db";  
    private static final String TBL_NAME = "Holiday";  
    private static final String CREATE_TBL = " create table "  
            + " Holiday(day integer ,month integer,notification text) ";  
      
    private SQLiteDatabase db;  
    DBHelper(Context c) {  
        super(c, DB_NAME, null, 2);  
    }  
    @Override  
    public void onCreate(SQLiteDatabase db) {  
        this.db = db;  
        db.execSQL(CREATE_TBL);  
        insertAll(iniHoliday(),db);
    }  
    
    public void insert(ContentValues values) {  
        SQLiteDatabase db = getWritableDatabase();  
        db.insert(TBL_NAME, null, values);  
        
        db.close();  
    }  
    public void insertAll(List<ContentValues> values,SQLiteDatabase db) {  
      
        Iterator<ContentValues>  i= values.iterator();
        while(i.hasNext()){
        	
        	db.insert(TBL_NAME, null, i.next());  
        }
        
        
        
    }  
    
    public Cursor query() {  
        SQLiteDatabase db = getWritableDatabase();  
        Cursor c = db.query(TBL_NAME, null, null, null, null, null, null);  
        return c;  
    }  
    public void del(int id) {  
        if (db == null)  
            db = getWritableDatabase();  
        db.delete(TBL_NAME, "_id=?", new String[] { String.valueOf(id) }); 
        
    }  
    public void delAll(int delcount) {  
        if (db == null)  
            db = getWritableDatabase(); 

        	db.execSQL("DELETE  FROM  "+TBL_NAME+" where _id <= (select _id from "+TBL_NAME+" limit 1 offset "+(delcount-1)+")");
 
    
		db.close();       
    }  
   
    public void close() {  
        if (db != null)  
            db.close();  
    }  
    @Override  
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  
    }  
    
    private List<ContentValues> iniHoliday() {
		List<ContentValues> lst = new ArrayList<ContentValues>();

		ContentValues v;
		v = new ContentValues();
		v.put("day", "21");
		v.put("month", "1");
		v.put("notification", "春节调班");
		lst.add(v);

		v = new ContentValues();
		v.put("day", "29");
		v.put("month", "1");
		v.put("notification", "春节调班");
		lst.add(v);

		v = new ContentValues();
		v.put("day", "22");
		v.put("month", "1");
		v.put("notification", "春节假期");
		lst.add(v);

		v = new ContentValues();
		v.put("day", "31");
		v.put("month", "3");
		v.put("notification", "清明调班");
		lst.add(v);

		v = new ContentValues();
		v.put("day", "2");
		v.put("month", "4");
		v.put("notification", "清明假期");
		lst.add(v);

		v = new ContentValues();
		v.put("day", "12");
		v.put("month", "1");
		v.put("notification", "测试调班");
		lst.add(v);
		
		v = new ContentValues();
		v.put("day", "13");
		v.put("month", "1");
		v.put("notification", "测试调班");
		lst.add(v);
		return lst;

	}
}  
