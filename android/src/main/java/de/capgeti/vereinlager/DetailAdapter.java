package de.capgeti.vereinlager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import de.capgeti.vereinlager.model.Detail;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: capgeti
 * Date:   22.11.13 05:36
 */
public class DetailAdapter extends BaseAdapter {
    public List<Detail> detailList = new ArrayList<Detail>();
    private LayoutInflater inflater;

    public DetailAdapter(LayoutInflater inflater, List<Detail> details) {
        this.inflater = inflater;
        detailList = details;
        notifyDataSetChanged();
    }

    public int getCount() {
        return detailList.size();
    }

    public Detail getItem(int position) {
        return detailList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.detail_list_item, parent, false);
            holder.name = (EditText) convertView.findViewById(R.id.detail_name);
            holder.value = (EditText) convertView.findViewById(R.id.detail_value);
            holder.deleteButton = (ImageButton) convertView.findViewById(R.id.deleteButton);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Detail detail = detailList.get(position);
        holder.name.setText(detail.getName());
        holder.name.setId(position);
        holder.value.setText(detail.getValue());
        holder.value.setId(position);

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                detailList.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    final int position = v.getId();
                    final EditText caption = (EditText) v;
                    detailList.get(position).setName(caption.getText().toString());
                }
            }
        });
        holder.value.setOnFocusChangeListener(
                new View.OnFocusChangeListener() {
                            public void onFocusChange(View v, boolean hasFocus) {
                                if (!hasFocus) {
                                    final int position = v.getId();
                                    final EditText Caption = (EditText) v;
                                    detailList.get(position).setValue(Caption.getText().toString());
                                }
                            }
                        });

        return convertView;
    }

    class ViewHolder {
        EditText name;
        EditText value;
        ImageButton deleteButton;
    }
}
