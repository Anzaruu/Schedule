package com.example.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddCourseActivity extends AppCompatActivity {

    EditText setName, setDepartment;
    Button createBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_course);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setName = findViewById(R.id.setName);
        setDepartment = findViewById(R.id.setDepartment);
        createBtn = findViewById(R.id.createBtn);

        ConnectionToDB db = new ConnectionToDB(this);


        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = setName.getText().toString();
                String department = setDepartment.getText().toString();

                if (name.isEmpty()==true || department.isEmpty()== true){
                    Toast.makeText(AddCourseActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (db.insertDataCourse(name, department)==true){

                        Toast.makeText(AddCourseActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(AddCourseActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(AddCourseActivity.this, AddScheduleActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}