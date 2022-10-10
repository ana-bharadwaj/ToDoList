package in.codersclub.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import in.codersclub.todolist.AddNewTask.AddNewTask;
import in.codersclub.todolist.adapter.ToDoAdapter;
import in.codersclub.todolist.model.ToDoModel;
import in.codersclub.todolist.utils.DBHelper;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListener {

     private RecyclerView mRecyclerView;
     private FloatingActionButton fab;
     private DBHelper myDb;
     private List<ToDoModel> mlist;
     private ToDoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView=findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fab=findViewById(R.id.Add);
        myDb= new DBHelper(MainActivity.this);
        mlist=new ArrayList<>();
        adapter=new ToDoAdapter(myDb,MainActivity.this);
        mRecyclerView.setAdapter(adapter);
        mlist= myDb.getAllTasks();
        Collections.reverse(mlist);
        adapter.setTasks(mlist);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
            }
        });
        ItemTouchHelper itemTouchHelper= new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        mlist= myDb.getAllTasks();
        Collections.reverse(mlist);
        adapter.setTasks(mlist);
        adapter.notifyDataSetChanged();

    }
}