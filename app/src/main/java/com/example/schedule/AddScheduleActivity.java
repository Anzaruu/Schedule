package com.example.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class AddScheduleActivity extends AppCompatActivity {

    Button addCourseBtn, addTeacherBtn, createBtn;
    EditText addParaTxt, addRoomTxt;
    Spinner daySpinner, courseSpinner, teacherSpinner;
    String[] days = { "Понедельник", "Вторник", "Среда", "Четверг", "Пятица", "Суббота"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_schedule);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        addCourseBtn = findViewById(R.id.addCourseBtn);
        addTeacherBtn = findViewById(R.id.addTeacherBtn);
        createBtn = findViewById(R.id.createBtn);
        addParaTxt = findViewById(R.id.addParaTxt);
        addRoomTxt = findViewById(R.id.addRoomTxt);

        ConnectionToDB db = new ConnectionToDB(this);
        courseSpinner = findViewById(R.id.courseSpinner);
        ArrayAdapter<String> adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, db.getCourse());
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(adapter2);




        teacherSpinner = findViewById(R.id.teacherSpinner);
        ArrayAdapter<String> adapter3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, db.getTeacher());
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teacherSpinner.setAdapter(adapter3);

        daySpinner = findViewById(R.id.daySpinner);
        ArrayAdapter<String> adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, days);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(adapter1);


        final int[] countCourse = new int[1];
        final int[] countTeacher = new int[1];
        final String[] day = new String[1];
        AdapterView.OnItemSelectedListener itemSelectedteacher = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int teacher_id = position;
                countTeacher[0] = teacher_id+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };

        AdapterView.OnItemSelectedListener itemSelectedCourse = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int course_id = position;
                countCourse[0] = course_id+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };

        AdapterView.OnItemSelectedListener itemSelectedDay = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                day[0] = (String)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        teacherSpinner.setOnItemSelectedListener(itemSelectedteacher);
        courseSpinner.setOnItemSelectedListener(itemSelectedCourse);
        daySpinner.setOnItemSelectedListener(itemSelectedDay);


        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddScheduleActivity.this, AddCourseActivity.class);
                startActivity(intent);
            }
        });

        addTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddScheduleActivity.this, AddTeacherActivity.class);
                startActivity(intent);
            }
        });
        createBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (day[0].equals("") || addParaTxt.getText().toString().equals("") || addRoomTxt.getText().toString().equals("") || courseSpinner.isSelected() || teacherSpinner.isSelected()){
                    Toast.makeText(AddScheduleActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (db.insertDataSchedule(day[0], Integer.parseInt(addRoomTxt.getText().toString()), Integer.parseInt(addParaTxt.getText().toString()), countCourse[0], countTeacher[0]) == true){
                        Toast.makeText(AddScheduleActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(AddScheduleActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(AddScheduleActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}