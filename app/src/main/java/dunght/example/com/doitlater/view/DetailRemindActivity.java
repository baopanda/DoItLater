package dunght.example.com.doitlater.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import dunght.example.com.doitlater.R;
import dunght.example.com.doitlater.dao.DatabaseHelper;
import dunght.example.com.doitlater.model.Remind;

public class DetailRemindActivity extends AppCompatActivity {

    TextView tvContent, tvDate, tvState;
    Button btnBack;
    ArrayList<Remind> listRemind;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_remind);
        tvContent = (TextView)findViewById(R.id.tv_content_detail_remind);
        tvDate = (TextView)findViewById(R.id.tv_date_detail_remind);
        tvState = (TextView)findViewById(R.id.tv_state_detail_remind);
        btnBack = (Button)findViewById(R.id.btn_back_remind);

        db = new DatabaseHelper(this);
        listRemind = db.getAllRemind();
        int position = getIntent().getExtras().getInt("Position");
        Remind remind = listRemind.get(position);
        tvContent.setText(remind.getContent());
        tvDate.setText(remind.getDate() +" • " + remind.getTime());
        tvState.setText(remind.getState());

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailRemindActivity.this, MainActivity.class));
            }
        });

    }
}
