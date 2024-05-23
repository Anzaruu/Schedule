package com.example.schedule;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GridBaseAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context ctx;
    private ArrayList<ScheduleModel> scheduleModelArrayList;
    private TextView para, course, teacher, room;
    public GridBaseAdapter(Context ctx, ArrayList<ScheduleModel> scheduleModelArrayList) {
        this.ctx = ctx;
        this.scheduleModelArrayList = scheduleModelArrayList;
    }

    @Override
    public int getCount() {
        return scheduleModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return scheduleModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.card_item, parent, false);

        para = itemView.findViewById(R.id.para);
        course = itemView.findViewById(R.id.course);
        teacher = itemView.findViewById(R.id.teacher);
        room = itemView.findViewById(R.id.room);

        para.setText(Integer.toString(scheduleModelArrayList.get(position).getPara()));
        course.setText(scheduleModelArrayList.get(position).getCourse_id());
        teacher.setText(scheduleModelArrayList.get(position).getTeacher_id());
        room.setText(Integer.toString(scheduleModelArrayList.get(position).getRoom()));

        return itemView;
    }
}
