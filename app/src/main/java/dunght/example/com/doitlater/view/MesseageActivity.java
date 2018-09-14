package dunght.example.com.doitlater.view;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Message;
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

import java.util.Calendar;

import dunght.example.com.doitlater.R;
import dunght.example.com.doitlater.controller.MessageReceiver;
import dunght.example.com.doitlater.controller.RemindReceiver;
import dunght.example.com.doitlater.controller.Utils;
import dunght.example.com.doitlater.dao.DatabaseHelper;
import dunght.example.com.doitlater.model.Remind;

public class MesseageActivity extends AppCompatActivity {

    TextView tvDate, tvTime;
    Button btnDone;
    EditText edtMessage, edtPhone;
    ImageButton ibBack;
    int day, month, year;
    int hour, minute;
    String hourFormat;
    String minuteFormat;
    Calendar calendar;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    DatabaseHelper databaseHelper;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messeage);

        btnDone = (Button) findViewById(R.id.btn_done_message);
        tvDate = (TextView) findViewById(R.id.tv_date_message);
        tvTime = (TextView) findViewById(R.id.tv_time_message);
        edtMessage = (EditText) findViewById(R.id.edt_sms_message);
        edtPhone = (EditText) findViewById(R.id.edt_phone_message);
        ibBack = (ImageButton)findViewById(R.id.ib_back_message);
        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        databaseHelper = new DatabaseHelper(this);
        sharedPreferences = getSharedPreferences(Utils.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);

        selectecHourFormat(hour);
        selectecMinuteFormat(minute);

        tvDate.setText(day + "/" + (month + 1) + "/" + year);
        tvTime.setText(hour + ":" + minuteFormat + minute + " " + hourFormat);

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MesseageActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(MesseageActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

        final Intent intent = new Intent(MesseageActivity.this, MessageReceiver.class);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = edtMessage.getText().toString();
                String phoneNumber = edtPhone.getText().toString();
                if (TextUtils.isEmpty(content) || TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(MesseageActivity.this, "Nội dung không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    int img = R.drawable.message;
                    String date = tvDate.getText().toString();
                    String time = tvTime.getText().toString();
                    String state = "Đang đợi";
                    String title = "Message";
                    Remind remind = new Remind(date, time, state, title, content, img, phoneNumber);
                    databaseHelper.addRemind(remind);

                    intent.putExtra("smsRemind", content);
                    intent.putExtra("phoneRemind", phoneNumber);
                    intent.putExtra("positionRemind", databaseHelper.getAllRemind().size()-1);
                    pendingIntent = PendingIntent.getBroadcast(
                            MesseageActivity.this, Utils.requestcode, intent, PendingIntent.FLAG_UPDATE_CURRENT
                    );
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    Utils.requestcode += 1;

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(Utils.REQUEST_CODE, Utils.requestcode);
                    editor.apply();

                    startActivity(new Intent(MesseageActivity.this, MainActivity.class));
                }
            }
        });

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MesseageActivity.this);
                builder.setMessage("Bạn muốn hủy bỏ thay đổi?");
                builder.setCancelable(false);
                builder.setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(MesseageActivity.this, MainActivity.class));
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
