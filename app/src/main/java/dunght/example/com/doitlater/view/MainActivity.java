package dunght.example.com.doitlater.view;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import dunght.example.com.doitlater.R;
import dunght.example.com.doitlater.controller.Utils;


public class MainActivity extends AppCompatActivity {

    ImageButton buttonCalendar;
    ImageButton buttonAdd, buttonCancel;
    Calendar mCurrentDate;
    int day,month,year;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(Utils.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        Utils.requestcode = sharedPreferences.getInt(Utils.REQUEST_CODE, 0);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        android.support.v7.app.ActionBar bar = getSupportActionBar();
        if(bar != null){
            TextView tv = new TextView(getApplicationContext());
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
            tv.setLayoutParams(lp);
            tv.setText(""+currentDate);
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            bar.setCustomView(tv);
        }

        mCurrentDate = Calendar.getInstance();
        day= mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month= mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);

        addControls();
        addEvents();
    }

    private void addEvents() {
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callFragment(new CategoryFragment());
                buttonAdd.setVisibility(View.INVISIBLE);
                buttonCancel.setVisibility(View.VISIBLE);
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callFragment(new ToDoFragment());
                buttonAdd.setVisibility(View.VISIBLE);
                buttonCancel.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void addControls() {
        callFragment(new ToDoFragment());
        buttonAdd= findViewById(R.id.buttonAdd);
        buttonCancel= findViewById(R.id.buttonCancel);
    }

    public void callFragment(android.support.v4.app.Fragment fragment) {
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameContent, fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.calender_menu:
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    }
                }, year,month,day);
                datePickerDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
