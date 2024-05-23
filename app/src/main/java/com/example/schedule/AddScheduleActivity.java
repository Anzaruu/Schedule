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
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, db.getCourse());
        // Определяем разметку для использования при выборе элемента
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        courseSpinner.setAdapter(adapter2);


        teacherSpinner = findViewById(R.id.teacherSpinner);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, db.getTeacher());
        // Определяем разметку для использования при выборе элемента
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        teacherSpinner.setAdapter(adapter3);

        daySpinner = findViewById(R.id.daySpinner);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, days);
        // Определяем разметку для использования при выборе элемента
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        daySpinner.setAdapter(adapter1);


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
            String day = daySpinner.getSelectedItem().toString();
            int course_id = courseSpinner.getId();
            int teacher_id =teacherSpinner.getId();

            @Override
            public void onClick(View v) {
                if (day.equals("") || addParaTxt.getText().toString().equals("") || addRoomTxt.getText().toString().equals("") || courseSpinner.isSelected() || teacherSpinner.isSelected()){
                    Toast.makeText(AddScheduleActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (db.insertDataSchedule(day, Integer.parseInt(addParaTxt.getText().toString()), Integer.parseInt(addRoomTxt.getText().toString()), course_id, teacher_id) == true){
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