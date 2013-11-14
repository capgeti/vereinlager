package de.capgeti.vereinlager.util;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

/**
 * Author: capgeti
 * Date:   10.09.13 23:56
 */
public abstract class CustomCursorAdapter extends SimpleCursorAdapter {
    private Context context;
    private int mListItemLayoutResId;

    public CustomCursorAdapter(Context context, int listItemLayoutResourceId, Cursor ts) {
        super(context, listItemLayoutResourceId, ts, new String[]{}, new int[]{}, 0);
        this.context = context;
        mListItemLayoutResId = listItemLayoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listItemView = convertView;
        if (null == convertView) {
            listItemView = inflater.inflate(mListItemLayoutResId, parent, false);
        }

        final Cursor cursor = getCursor();
        cursor.moveToPosition(position);
        fillView(listItemView, cursor);

        return listItemView;
    }

    protected abstract void fillView(View listItemView, Cursor position);
}
