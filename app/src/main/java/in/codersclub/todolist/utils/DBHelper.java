package in.codersclub.todolist.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import in.codersclub.todolist.model.ToDoModel;

public class DBHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private static final String DATABASE_NAME="TODO_DB";
    private static final String TABLE_NAME="TODO_Table";
    private static final String COLOUMN_1="ID";
    private static final String COLOUMN_2="TASK";
    private static final String COLOUMN_3="STATUS";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , TASK TEXT , STATUS INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
    public void insertTask(ToDoModel model){
        db=this.getWritableDatabase();
        ContentValues value= new ContentValues();
        value.put(COLOUMN_2,model.getTask());
        value.put(COLOUMN_3,0);
        db.insert(TABLE_NAME, null,value);

    }
    public void updateTask(int id,String task){
        db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLOUMN_2,task);
        db.update(TABLE_NAME,values, "ID=?",new String[]{String.valueOf(id)});
    }
    public void updateStatus(int id ,int status){
        db=this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(COLOUMN_3,status);
        db.update(TABLE_NAME,values,"ID=?",new String[]{String.valueOf(id)});
    }
    public void deleteTask(int id){
        db=this.getWritableDatabase();
        db.delete(TABLE_NAME,"ID=?",new String[]{String.valueOf(id)});

    }
    @SuppressLint("Range")
    public List<ToDoModel> getAllTasks(){
        db=this.getWritableDatabase();
       Cursor cursor=null;
       List<ToDoModel> modelList=new ArrayList<>();
       db.beginTransaction();
       try {
            cursor=db.query(TABLE_NAME,null,null,null,null,null,null,null);
            if(cursor!= null){
                if(cursor.moveToFirst()){
                    do{
                        ToDoModel task =new ToDoModel();
                        task.setId(cursor.getInt(cursor.getColumnIndex(COLOUMN_1)));
                        task.setTask(cursor.getString(cursor.getColumnIndex(COLOUMN_2)));
                        task.setStatus(cursor.getInt(cursor.getColumnIndex(COLOUMN_3)));
                        modelList.add(task);
                    }while(cursor.moveToNext());

                }
            }
       }
       finally {
           db.endTransaction();
           cursor.close();

       }
       return modelList;


    }
}
