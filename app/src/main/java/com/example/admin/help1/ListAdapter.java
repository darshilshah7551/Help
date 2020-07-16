package com.example.admin.help1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ListAdapter extends BaseAdapter{


    private Context context;
    private ArrayList<ContactModel> rowItems;
    private ArrayList<String> conName;
    private ArrayList<String> conNumber;

    ListAdapter(Context context, ArrayList<ContactModel> items,ArrayList<String> conName,ArrayList<String> conNumber) {
        this.context = context;
        this.rowItems = items;
        this.conName= conName;
        this.conNumber = conNumber;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView txtTitle;
        TextView txtDesc;
        ImageButton delete;
    }

    @SuppressLint("InflateParams")
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder ;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            assert mInflater != null;
            convertView = mInflater.inflate(R.layout.added_contacts, null);
            holder = new ViewHolder();
            holder.txtDesc =  convertView.findViewById(R.id.added_name);
            holder.txtTitle = convertView.findViewById(R.id.added_number);
            holder.delete = convertView.findViewById(R.id.delete);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        ContactModel rowItem = (ContactModel) getItem(position);
        holder.txtDesc.setText(conName.get(position));
        Log.e("askfjhkjsdafkj","IN LIST"+DataClass.InnerData.getName().get(position));
        holder.txtTitle.setText(conNumber.get(position));

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"DELETE",Toast.LENGTH_SHORT).show();
                //arrayAdapter.notifyDataSetChanged();
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return  rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

}
