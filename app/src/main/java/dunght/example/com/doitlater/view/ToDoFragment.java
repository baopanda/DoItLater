package dunght.example.com.doitlater.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;

import java.util.ArrayList;

import dunght.example.com.doitlater.R;
import dunght.example.com.doitlater.adapter.AdapterRemind;
import dunght.example.com.doitlater.controller.Utils;
import dunght.example.com.doitlater.dao.DatabaseHelper;
import dunght.example.com.doitlater.model.Remind;

public class ToDoFragment extends Fragment {

    ArrayList<Remind> listRemind;
    DatabaseHelper db;
    ListView lvRemind;
    AdapterRemind adapterRemind;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_to_do, container, false);
        db = new DatabaseHelper(getActivity());
        listRemind =  db.getAllRemind();
        Utils.requestcode = listRemind.size();

        lvRemind = rootView.findViewById(R.id.lv_remind);
        adapterRemind = new AdapterRemind(listRemind, getActivity());
        lvRemind.setAdapter(adapterRemind);

        lvRemind.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int position = i;
                PopupMenu popupMenu = new PopupMenu(getActivity(), view, Gravity.RIGHT);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getTitle().equals("Sửa")) {
                            editRemind(position);
                        }else if(menuItem.getTitle().equals("Xóa")) {
                            deleteRemind(position);
                        }else if(menuItem.getTitle().equals("Chi tiết")) {
                            detailRemind(position);
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        return rootView;
    }

    public void editRemind(int position) {
        Remind remind = listRemind.get(position);
        if(remind.getTitle().equals("Nhắc nhở")) {
            Intent intent = new Intent(getActivity(), EditRemindActivity.class);
            intent.putExtra("Position",position);
            startActivity(intent);
        }else {
            Intent intent = new Intent(getActivity(), EditMessageActivity.class);
            intent.putExtra("Position",position);
            startActivity(intent);
        }
    }

    public void deleteRemind(int position) {
        Remind remind = listRemind.get(position);
        db.deleteRemind(remind);
        listRemind.clear();
        listRemind.addAll(db.getAllRemind());
        adapterRemind.notifyDataSetChanged();
    }

    public void detailRemind(int position) {
        Remind remind = listRemind.get(position);
        if(remind.getTitle().equals("Nhắc nhở")) {
            Intent intent = new Intent(getActivity(), DetailRemindActivity.class);
            intent.putExtra("Position",position);
            startActivity(intent);
        }else {
            Intent intent = new Intent(getActivity(), DetailMessageActivity.class);
            intent.putExtra("Position",position);
            startActivity(intent);
        }
    }
}
