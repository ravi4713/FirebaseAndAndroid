package com.example.fire_base_crud;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowDetailsAadaptor extends ArrayAdapter<ShowDetailsModel> {
    public ShowDetailsAadaptor(Activity context, ArrayList<ShowDetailsModel> items){
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_items, parent, false);
        }

        ShowDetailsModel currentItem = getItem(position);

        TextView title = (TextView) listItemView.findViewById(R.id.item_title);
        TextView description = (TextView) listItemView.findViewById(R.id.item_description);

        title.setText(currentItem.getmTilte());
        description.setText(currentItem.getmDescription());

        return listItemView;
    }


}
