package in.codersclub.todolist.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ConcurrentModificationException;
import java.util.List;

import in.codersclub.todolist.AddNewTask.AddNewTask;
import in.codersclub.todolist.MainActivity;
import in.codersclub.todolist.R;
import in.codersclub.todolist.model.ToDoModel;
import in.codersclub.todolist.utils.DBHelper;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {

    private List<ToDoModel> mList;
    private MainActivity activity;
    private DBHelper myDB;

    public ToDoAdapter(DBHelper myDB,MainActivity activity){
        this.activity=activity;
        this.myDB=myDB;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
     final ToDoModel item= mList.get(position);
     holder.checkBox.setText(item.getTask());
     holder.checkBox.setChecked(toBoolean(item.getStatus()));
     holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
             if (isChecked) {
                 myDB.updateStatus(item.getId(), 1);


             } else
                 myDB.updateStatus(item.getId(), 0);
         }

     });


    }

    private boolean toBoolean(int num) {
        return num!=0;}

        public Context getContext(){

        return activity;
        }

        public void setTasks(List<ToDoModel> mList){
         this.mList =mList;
         notifyDataSetChanged();
        }

        public void deleteTask(int position){
         ToDoModel item =mList.get(position);
         myDB.deleteTask(item.getId());
         mList.remove(position);
         notifyItemRemoved(position);
        }
        public void editTask(int position ){
         ToDoModel item= mList.get(position);
         Bundle bundle = new Bundle();
         bundle.putInt( "id",item.getId());
         bundle.putString("task",item.getTask());
            AddNewTask task= new AddNewTask();
            task.setArguments(bundle);
            task.show(activity.getSupportFragmentManager(),task.getTag());

        }


    @Override
    public int getItemCount() {
        return mList.size();

    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            checkBox =itemView.findViewById(R.id.checkbox);

        }
    }
    }