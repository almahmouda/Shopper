package com.example.ali.shopper;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateList extends AppCompatActivity {
    // declare an intent
    Intent intent;
    // declare EditTexts - used to reference  the EditText in the resource file
    EditText nameEditText;
    EditText storeEditText;
    EditText dateEditText;


    // declare Calendar - used to map the data selected in the DatePickerDialog
    Calendar calendar;

    // declare DBHandler
    DBHandler dbHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialize EditTexts
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        storeEditText = (EditText) findViewById(R.id.storeEditText);
        dateEditText = (EditText) findViewById(R.id.dateEditText);

        // initialize Calender
        calendar = Calendar.getInstance();

        // create and initialize a DatePickerDialog and register and OnDateListener to it

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            /**
             * This method get called when a date is set in the DatePickerDialog
             * @param datePicker DatePicker object
             * @param year year that set
             * @param monthOfYear month that set
             * @param dayOfMonth day that set
             */
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                // set the Calender yeat, month, day to the year, month, day
                // set in the DatePicker
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                // call a method that updates the date EditText with the date that was set
                // in the DatePicker
                updateDueDate();
            }
        };

        // register an OnClickListener to the date EditText
        dateEditText.setOnClickListener(new View.OnClickListener(){
            /**
             * This method get called when the date EditText is clicked
             * @param view because the date EditText that calls this method is techanically considered a
             *             view , we must pass the method a View.
             */
            @Override
            public void onClick(View view) {

                // display the DatePickerDialog with current date selected
                new DatePickerDialog(CreateList.this,
                        date,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                ) .show();


            }
        });

        // initialize DBHandler
        dbHandler = new DBHandler(this,null);

    }
    /**
     * This method sets the Action Bar of the CreateList to whatever
     * is defined in the menu create list menu resource.
     * @param menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // set the Action Bar of the CreateList to whatever  is defined
        // in the menu main menu resource
        getMenuInflater().inflate(R.menu.menu_create_list, menu);
        return true;
    }

    /**
     * this method gets called when an item in the overflow menu is selected.
     * @param item Menuitem object that contains information about the item
     *             selected  in the overflow : for ecxample , its id
     * @return true if an item is selected, else false
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // get the id of the item selected
        switch (item.getItemId()){
            case R.id.action_home :
                // initalize an intent for the CreateList, start intent,
                // return true if the id in the itme selected is for the Main
                // Activity
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_create_list:
                // initalize an intent for the CreateList, start intent,
                // return true if the id in the itme selected is for the Main
                // Activity
                intent = new Intent( this, CreateList.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }
    }

    /**
     * This method get called when the add_list menu item get pushed.
     * @param menuItem because the add_list item that calls this method is
     *                 a menu item, we must pass the method a MenuItem.
     */
    public void  createList(MenuItem menuItem){
        //get data in EditText and store it in Strings
        String name = nameEditText.getText().toString();
        String store = storeEditText.getText().toString();
        String date = dateEditText.getText().toString();

        if(name.trim().equals("") || store.trim().equals("") || date.trim().equals("") ){
            // required date hasn't been input, so display Toast
            Toast.makeText(this, "Please enter a name , store, date ", Toast.LENGTH_SHORT).show();
        }else{
            // required data has been input, update the database and display a different Toast
            dbHandler.addShoppingList(name,store,date);
            Toast.makeText(this, "Shopping List added", Toast.LENGTH_SHORT).show();
        }


    }

    /**
     * This method gets called when a date is set to the datePickerDialog
     */
    public void updateDueDate(){
        // create a SimpleDateFormat

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                // apply the SimpleDateFormat to the date set in the DatePickerDialog  and
                // set the formatted
        dateEditText.setText(simpleDateFormat.format(calendar.getTime()));

    }
}
