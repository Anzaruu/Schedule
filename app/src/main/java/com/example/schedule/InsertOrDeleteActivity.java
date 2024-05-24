package com.example.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;

public class InsertOrDeleteActivity extends AppCompatActivity {
    Button insertBtn, deleteBtn;
    EditText addParaTxt, addRoomTxt;
    Spinner daySpinner, courseSpinner, teacherSpinner;
    String[] days = { "Понедельник", "Вторник", "Среда", "Четверг", "Пятица", "Суббота"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_insert_or_delete);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        deleteBtn = findViewById(R.id.deleteBtn);
        insertBtn = findViewById(R.id.insertBtn);
        addParaTxt = findViewById(R.id.addParaTxt);
        addRoomTxt = findViewById(R.id.addRoomTxt);

        Bundle arguments = getIntent().getExtras();
        String course = arguments.get("course_id").toString();
        String teacher = arguments.get("teacher_id").toString();
        String dayArgument = arguments.get("day").toString();
        String schedule_para = arguments.get("schedule_para").toString();
        String schedule_room = arguments.get("schedule_room").toString();

        addParaTxt.setText(schedule_para);
        addRoomTxt.setText(schedule_room);

        ConnectionToDB db = new ConnectionToDB(this);
        courseSpinner = findViewById(R.id.courseSpinner);
        ArrayAdapter<String> adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, db.getCourse());
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(adapter2);
        courseSpinner.setId(Arrays.asList(db.getCourse()).indexOf(course));


        teacherSpinner = findViewById(R.id.teacherSpinner);
        ArrayAdapter<String> adapter3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, db.getTeacher());
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teacherSpinner.setAdapter(adapter3);
        teacherSpinner.setId(Arrays.asList(db.getTeacher()).indexOf(teacher));

        daySpinner = findViewById(R.id.daySpinner);
        ArrayAdapter<String> adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, days);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(adapter1);
        daySpinner.setId(Arrays.asList(days).indexOf(dayArgument));
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            String day = daySpinner.getSelectedItem().toString();
            int course_id = courseSpinner.getId();
            int teacher_id =teacherSpinner.getId();


            @Override
            public void onClick(View v) {
                if (day.equals("") || addParaTxt.getText().toString().equals("") || addRoomTxt.getText().toString().equals("") || courseSpinner.isSelected() || teacherSpinner.isSelected()){
                    Toast.makeText(InsertOrDeleteActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (db.deleteDataSchedule(day, Integer.parseInt(addParaTxt.getText().toString()), Integer.parseInt(addRoomTxt.getText().toString()), course_id, teacher_id) == true){
                        Toast.makeText(InsertOrDeleteActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(InsertOrDeleteActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(InsertOrDeleteActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}