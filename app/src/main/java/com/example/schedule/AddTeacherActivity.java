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

public class AddTeacherActivity extends AppCompatActivity {

    EditText setName, setSurname, setLastname, setDepartment;
    Button createBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_teacher);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setName = findViewById(R.id.setName);
        setSurname = findViewById(R.id.setSurname);
        setLastname = findViewById(R.id.setLastname);
        setDepartment = findViewById(R.id.setDepartment);
        createBtn = findViewById(R.id.createBtn);

        ConnectionToDB db = new ConnectionToDB(this);


        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = setName.getText().toString();
                String surname = setSurname.getText().toString();
                String lastname = setLastname.getText().toString();
                String department = setDepartment.getText().toString();

                if (name.equals("") || surname.equals("") || lastname.equals("") || department.equals("")){
                    Toast.makeText(AddTeacherActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (db.insertDataTeacher(name, surname, lastname, department)==true){

                        Toast.makeText(AddTeacherActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(AddTeacherActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(AddTeacherActivity.this, AddScheduleActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}