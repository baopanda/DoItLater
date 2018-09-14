package dunght.example.com.doitlater.view;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import dunght.example.com.doitlater.R;
import dunght.example.com.doitlater.controller.RemindReceiver;
import dunght.example.com.doitlater.controller.Utils;
import dunght.example.com.doitlater.dao.DatabaseHelper;
import dunght.example.com.doitlater.model.Remind;

public class EditRemindActivity extends AppCompatActivity {

    TextView tvDate, tvTime;
    Button btnDone;
    EditText edtRemind;
    ImageButton ibBack;
    int day, month, year;
    int hour, minute;
    String hourFormat;
    String minuteFormat;
    Calendar calendar;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    DatabaseHelper db;
    ArrayList<Remind> listRemind;
    int positionEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_remind);

        btnDone = (Button) findViewById(R.id.btn_done_remind_edit);
        tvDate = (TextView) findViewById(R.id.tv_date_remind_edit);
        tvTime = (TextView) findViewById(R.id.tv_time_remind_edit);
        edtRemind = (EditText) findViewById(R.id.edt_remind_edit);
        ibBack = (ImageButton)findViewById(R.id.ib_back_remind_edit);
        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        db = new DatabaseHelper(this);

        selectecHourFormat(hour);
        selectecMinuteFormat(minute);

        listRemind = db.getAllRemind();
        positionEdit = getIntent().getExtras().getInt("Position");
        final Remind remind = listRemind.get(positionEdit);
        edtRemind.setText(remind.getContent());
        tvDate.setText(remind.getDate());
        tvTime.setText(remind.getTime());

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditRemindActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tvDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        calendar.set(Calendar.YEAR, view.getYear());
                        calendar.set(Calendar.MONTH, view.getMonth());
                        calendar.set(Calendar.DAY_OF_MONTH, view.getDayOfMonth());
                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(EditRemindActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectecHourFormat(hourOfDay);
                        selectecMinuteFormat(minute);
                        tvTime.setText(hourOfDay + ":" + minuteFormat + minute + " " + hourFormat);
                        calendar.set(Calendar.HOUR_OF_DAY, view.getCurrentHour());
                        calendar.set(Calendar.MINUTE, view.getCurrentMinute());
                        calendar.set(Calendar.SECOND, 0);
                    }
                }, hour, minute, true);

                timePickerDialog.show();
            }
        });

        final Intent intent = new Intent(EditRemindActivity.this, RemindReceiver.class);
        final Intent intentBack = new Intent(EditRemindActivity.this, MainActivity.class);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = edtRemind.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(EditRemindActivity.this, "Nội dung không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    String date = tvDate.getText().toString();
                    String time = tvTime.getText().toString();
                    remind.setContent(content);
                    remind.setDate(date);
                    remind.setTime(time);
                    db.updateRemind(remind);

                    intent.putExtra("contentRemind", content);
                    intent.putExtra("positionRemind", positionEdit);
                    pendingIntent = PendingIntent.getBroadcast(
                            EditRemindActivity.this, positionEdit, intent, PendingIntent.FLAG_UPDATE_CURRENT
                    );
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                    startActivity(intentBack);
                }
            }
        });

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditRemindActivity.this);
                builder.setMessage("Bạn muốn hủy bỏ thay đổi?");
                builder.setCancelable(false);
                builder.setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(EditRemindActivity.this, MainActivity.class));
                    }
                });
                builder.setNegativeButton("KO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    public void selectecHourFormat(int hour) {
        if (hour == 0) {
            hour += 12;
            hourFormat = "AM";
        } else if (hour == 12) {
            hourFormat = "PM";
        } else if (hour > 12) {
            hour -= 12;
            hourFormat = "PM";
        } else {
            hourFormat = "AM";
        }
    }

    public void selectecMinuteFormat(int minute) {
        if (minute < 10) {
            minuteFormat = "0";
        } else {
            minuteFormat = "";
        }
    }
}
