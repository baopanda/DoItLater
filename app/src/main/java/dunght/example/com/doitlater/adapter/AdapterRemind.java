package dunght.example.com.doitlater.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dunght.example.com.doitlater.R;
import dunght.example.com.doitlater.model.Remind;

public class AdapterRemind extends BaseAdapter{

    private ArrayList<Remind> listRemind;
    private Activity activity;

    public AdapterRemind(ArrayList<Remind> listRemind, Activity activity) {
        this.listRemind = listRemind;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listRemind.size();
    }

    @Override
    public Object getItem(int i) {
        return listRemind.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = activity.getLayoutInflater().inflate(R.layout.item_remind, null);
        ImageView imgRemind = (ImageView)view.findViewById(R.id.iv_remind);
        TextView tvDate = (TextView)view.findViewById(R.id.tv_date);
        TextView tvTime = (TextView)view.findViewById(R.id.tv_time);
        TextView tvState = (TextView)view.findViewById(R.id.tv_state);
        TextView tvTitile = (TextView)view.findViewById(R.id.tv_title);
        TextView tvContent = (TextView)view.findViewById(R.id.tv_content);

        Remind remind = listRemind.get(i);
        imgRemind.setImageResource(remind.getImg());
        tvDate.setText(remind.getDate());
        tvTime.setText(remind.getTime());
        tvState.setText(remind.getState());
        tvTitile.setText(remind.getTitle());
        tvContent.setText(remind.getContent());

        return view;
    }
}
