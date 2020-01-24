package com.example.pratice;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class contactDetailAdapter<list> extends ArrayAdapter<employeePOJO> {

    private ArrayList<employeePOJO> data;
    private Context context;


    public contactDetailAdapter(Context context, ArrayList<employeePOJO> data) {
        super(context, R.layout.row_layout,data);


        this.data=data;
        this.context=context;


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View getView(int position, View convertView, ViewGroup parent) {

        // First let's verify the convertView is not null
        if (convertView == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_layout, parent, false);
        }
        // Now we can fill the layout with the right values
        TextView tv1 = (TextView) convertView.findViewById(R.id.employee_name);
        TextView tv2 = (TextView) convertView.findViewById(R.id.employee_age);
        TextView tv3 = (TextView) convertView.findViewById(R.id.employee_salary);
      //  ImageView iv = convertView.findViewById(R.id.imageView);
//        iv.setImageDrawable(context.getDrawable(R.drawable.ic_menu_camera));


        employeePOJO p = data.get(position);


        tv1.setText(p.getEmployee_name());
        tv2.setText(p.getEmployee_age());
        tv3.setText(p.getEmployee_salary());

        return convertView;
    }



}
