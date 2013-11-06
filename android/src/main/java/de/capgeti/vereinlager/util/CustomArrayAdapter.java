package de.capgeti.vereinlager.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Author: capgeti
 * Date:   10.09.13 23:56
 */
public abstract class CustomArrayAdapter<T> extends ArrayAdapter<T> {
    private int mListItemLayoutResId;

    public CustomArrayAdapter(Context context, List<T> ts) {
        this(context, android.R.layout.two_line_list_item, ts);
    }

    public CustomArrayAdapter(
            Context context,
            int listItemLayoutResourceId,
            List<T> ts) {
        super(context, listItemLayoutResourceId, ts);
        mListItemLayoutResId = listItemLayoutResourceId;
    }

    @Override
    public android.view.View getView(
            int position,
            View convertView,
            ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listItemView = convertView;
        if (null == convertView) {
            listItemView = inflater.inflate(
                    mListItemLayoutResId,
                    parent,
                    false);
        }

        // The ListItemLayout must use the standard text item IDs.
        fillView(listItemView, position);

        return listItemView;
    }

    protected abstract void fillView(View listItemView, int position);
}
