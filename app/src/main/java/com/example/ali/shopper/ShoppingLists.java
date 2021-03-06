package com.example.ali.shopper;

import android.content.Context;
import android.database.Cursor;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class ShoppingLists  extends CursorAdapter {
    /**
     * Initializes ShoppingLists CursourAdapter.
     * @param context reference to activity that initalized the CursorAdapter
     * @param cursor reference to the Cursor that contains the data from the database
     * @param flags determines special behavior of the CursorAdapter. Will always be
     *              0 which  means the CursorAdapter shouldn't have any special behavior
     */
    public ShoppingLists (Context context, Cursor cursor, int flags){
        super(context, cursor, flags);
    }

    /**
     * Makes a new view to hold the data in the Cursor.
     * @param context reference to activity that initalized the CursorAdapter
     * @param cursor reference to the Cursor that contains the data from the database
     * @param viewGroup reference to the shoppingListView in the Main Activity,
     *
     * @return reference to the new View that will be desplaied in the Main Activity,
     * w.g. the one that contains the data
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.li_shopping_list, viewGroup, false);
    }

    /**
     * Binds the new View to the data in the Cursor
     * @param view new View just created
     * @param context reference to activity that initalized the CursorAdapter
     * @param cursor reference to the Cursor that contains the data from the database
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ((TextView)view.findViewById(R.id.nameTextView)).
                setText(cursor.getString(cursor.getColumnIndex("name")));

        ((TextView)view.findViewById(R.id.storeTextView)).
                setText(cursor.getString(cursor.getColumnIndex("store")));

        ((TextView)view.findViewById(R.id.dateTextView)).
                setText(cursor.getString(cursor.getColumnIndex("date")));


    }


}
