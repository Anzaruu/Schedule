package com.example.schedule;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConnectionToDB {

    private static String ip = "192.168.1.53";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "university_schedule";
    private static String username = "sa";
    private static String password = "1234";
    //private static String url = "jdbc:jtds:sqlserver://192.168.1.53:1433/university_schedule";
    private static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;
    private Connection connection = null;
    public ConnectionToDB(Activity context) {
        ActivityCompat.requestPermissions(context,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(Classes);
            connection = DriverManager.getConnection(url, username,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e){
            e.printStackTrace();
        }
    }

    public Boolean checkusernamepassword(String username, String password){
        if (connection!=null){
            Statement statement = null;
            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("Select * from users where users_username = '"+username+"' and users_passw='"+password+"'");
                if (resultSet.next()==true){
                    return true;
                }
            } catch (SQLException | java.sql.SQLException e) {
                e.printStackTrace();
                return false;
            }
            return false;
        }
        else {
            return false;
        }
    }

    public String[] getCourse(){
        List<String> list = new ArrayList<>();
        String[] courses = new String[0];
        if (connection!=null){
            Statement statement = null;
            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("Select * from course;");
                while (resultSet.next()){
                    list.add(resultSet.getString(2));
                }

                if(list==null) {
                    String[] course2 = new String[0];
                    return courses;
                }
                else{
                    String[] courses2 = new String[list.size()];
                    for (int i =0; i< list.size(); i++){
                        courses2[i] = list.get(i);
                    }
                    return courses2;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return courses;
            } catch (java.sql.SQLException e){
                e.printStackTrace();
                return courses;
            }
        }
        else {
            return courses;
        }
    }

    public String[] getTeacher(){
        List<String> list = new ArrayList<>();
        String[] teachers = new String[0];
        if (connection!=null){
            Statement statement = null;
            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("Select * from teacher ");
                while (resultSet.next()){
                    list.add(resultSet.getString(2) + " " + resultSet.getString(3) + " " + resultSet.getString(4));
                }

                if (list==null){
                    String[] teachers2 = new String[0];
                    return teachers2;
                }
                else{
                    String[] teachers2 = new String[list.size()];
                    for (int i =0; i< list.size(); i++){
                        teachers2[i] = list.get(i);
                    }
                    return teachers2;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return teachers;
            } catch (java.sql.SQLException e){
                e.printStackTrace();
                return teachers;
            }
        }
        else {
            return teachers;
        }
    }

    public ArrayList<ScheduleModel> getArrayList(String day) {
        ArrayList<ScheduleModel> scheduleList = null;

        Statement statement = null;
        String[] teachers = getTeacher();
        String[] courses = getCourse();
        String teacher = null;
        String course = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from schedule where schedule_day='"+day+"'");
            if (resultSet != null) {
                scheduleList = new ArrayList<ScheduleModel>();
                while (resultSet.next()){
                    ScheduleModel scheduleModel = new ScheduleModel();
                    for (int i = 0; i< teachers.length; i++){
                        if (i+1 == resultSet.getInt(3)){
                            teacher = teachers[i];
                        }
                    }
                    for (int i = 0; i< courses.length; i++){
                        if (i+1 == resultSet.getInt(3)){
                            course = courses[i];
                        }
                    }
                    scheduleModel.setRoom(resultSet.getInt(4));
                    scheduleModel.setPara(resultSet.getInt(5));
                    scheduleModel.setCourse_id(course);
                    scheduleModel.setTeacher_id(teacher);
                    scheduleModel.setDay(resultSet.getString(6));
                    scheduleList.add(scheduleModel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            scheduleList = null;
        }
        return scheduleList;
    }

    public Boolean insertDataSchedule(String day, int room, int para, int course_id, int teacher_id){
        Statement statement = null;
        try {
            statement = connection.createStatement();
            Boolean result = statement.execute("insert into schedule (course_id, teacher_id, schedule_room, schedule_para, schedule_day) values " + "(" +(course_id)+ "," +(teacher_id)+","+room+","+para+",'"+day+"')");
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean deleteDataSchedule(String day, int room, int para, int course_id, int teacher_id){
        Statement statement = null;
        try {
            statement = connection.createStatement();
            Boolean result = statement.execute("delete from schedule where " + "(course_id=" +(course_id)+ "and teacher_id=" +(teacher_id)+"and schedule_room="+room+"and schedule_para="+para +"and schedule_day='"+day+"')");
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean insertDataTeacher(String name, String surname, String lastname, String department){
        Statement statement = null;
        try {
            statement = connection.createStatement();
            Boolean result = statement.execute("insert into teacher (teacher_name, teacher_surname, teacher_lastname, teacher_department) values " + "('" + name + "','" +surname+"','"+lastname+"','"+department+"')");
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean insertDataCourse(String name, String department){
        Statement statement = null;
        try {
            statement = connection.createStatement();
            Boolean result = statement.execute("insert into course (course_name, course_department) values " + "('" + name + "','" +department+"')");
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}


