package dunght.example.com.doitlater.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import dunght.example.com.doitlater.R;

public class CategoryFragment extends Fragment {
    ImageButton buttonSms, buttonFacebook, buttonTwitter, buttonEmail,
                buttonRemain, buttonCalendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        buttonSms = view.findViewById(R.id.buttonSms);
        buttonEmail = view.findViewById(R.id.buttonEmail);
        buttonFacebook = view.findViewById(R.id.buttonFacebook);
        buttonRemain = view.findViewById(R.id.buttonRemain);
        buttonTwitter = view.findViewById(R.id.buttonTwitter);
        addEvents();
        return view;
    }

    private void addEvents() {
        buttonSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent= new Intent(getContext(), MesseageActivity.class);
                startActivity(intent);
            }
        });
//        buttonEmail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= new Intent(getContext(), EmailRemainActivity.class);
//                startActivity(intent);
//            }
//        });
//        buttonTwitter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= new Intent(getContext(), TwitterRemainActivity.class);
//                startActivity(intent);
//            }
//        });
        buttonFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), FacebookRemindActivity.class);
                startActivity(intent);
            }
        });
        buttonRemain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), RemindActivity.class);
                startActivity(intent);
            }
        });

    }

}
