package in.codersclub.todolist.AddNewTask;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import in.codersclub.todolist.OnDialogCloseListener;
import in.codersclub.todolist.R;
import in.codersclub.todolist.model.ToDoModel;
import in.codersclub.todolist.utils.DBHelper;

public class AddNewTask extends BottomSheetDialogFragment {
    public static final String TAG ="AddNewTask";
    private EditText mEditText;
    public Button mSaveButton;
    private DBHelper myDb;
    public static AddNewTask newInstance(){
        return new AddNewTask();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.add_new_task,container,false);
       return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditText=view.findViewById(R.id.ET);
        mSaveButton = view.findViewById(R.id.btn_save);
        myDb =new DBHelper(getActivity());
         boolean isUpdate =false;
         Bundle bundle =getArguments();
         if(bundle!=null){
             isUpdate=true;
             String task= bundle.getString("task");
             mEditText.setText(task);
             if(task.length()>0){
                 mSaveButton.setEnabled(false);
             }

         }
         mEditText.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }


             @Override
             public void onTextChanged(CharSequence s, int start, int count, int after) {
                if(s.toString().equals("")){
                    mSaveButton.setEnabled(false);
                    mSaveButton.setBackgroundColor(Color.GRAY);
                }
                else {
                    mSaveButton.setEnabled(true);
                    mSaveButton.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
                }
             }

             @Override
             public void afterTextChanged(Editable s) {

             }
         });
         final boolean finalIsUpdate= isUpdate;
         mSaveButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String text = mEditText.getText().toString();
                 if (finalIsUpdate) {
                     myDb.updateTask(bundle.getInt("id"), text);

                 } else {
                     ToDoModel item = new ToDoModel();
                     item.setTask(text);
                     item.setStatus(0);
                     myDb.insertTask(item);
                 }
                 dismiss();
             }

         });




    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity= getActivity();
        if(activity instanceof OnDialogCloseListener ) {
            ((OnDialogCloseListener) activity).onDialogClose(dialog);
        }
    }
}
