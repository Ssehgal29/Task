package com.example.task1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtSectionName;
    Button btnAddSection;
    ListView sectionList;
    String sectionName,lectureName;
    ArrayList<String> sectionArrayList;
    ArrayAdapter<String> sectionAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Main Activity!");
        setId();
        setListener();
        createSectionList();

    }
    public void setId(){
        edtSectionName=findViewById(R.id.sectionName);
        btnAddSection=findViewById(R.id.addSection);
        sectionList=findViewById(R.id.sectionList);
    }
    public void setListener(){
        btnAddSection.setOnClickListener(this);
    }
    public void setStringValue(){
        sectionName=edtSectionName.getText().toString();
    }
    public void createSectionList(){
        sectionArrayList = new ArrayList<>();
        sectionAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,sectionArrayList);
        sectionList.setAdapter(sectionAdapter);
        sectionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, sectionArrayList.get(i), Toast.LENGTH_SHORT).show();
                final Dialog lectureDialog = new Dialog(MainActivity.this);
                lectureDialog.setContentView(R.layout.lecture_dialog_layout);
                TextView currentSection = lectureDialog.findViewById(R.id.currentSection);
                final EditText edtLectureName = lectureDialog.findViewById(R.id.lectureName);
                Button btnAddLecture = lectureDialog.findViewById(R.id.addLecture);
                currentSection.setText(sectionArrayList.get(i));
                btnAddLecture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        lectureName=edtLectureName.getText().toString();
                        if (lectureName.equals("")){
                            edtLectureName.setError("Please Enter Lecture Name!");
                            edtLectureName.requestFocus();
                        }else {
                            Toast.makeText(MainActivity.this, lectureName+" Added Successfully", Toast.LENGTH_SHORT).show();
                            lectureDialog.dismiss();
                        }
                    }
                });
                lectureDialog.show();

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addSection:
                setStringValue();
                if (sectionName.equals("")){
                    edtSectionName.setError("Please Enter Section Name!");
                    edtSectionName.requestFocus();
                }else {
                    addToSectionList(sectionName);
                    edtSectionName.setText("");
                }
                break;
        }
    }
    public void addToSectionList(String sectionName){
        sectionArrayList.add(sectionName);
        sectionAdapter.notifyDataSetChanged();
    }
}
