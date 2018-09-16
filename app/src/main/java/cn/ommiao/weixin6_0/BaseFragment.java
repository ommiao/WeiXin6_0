package cn.ommiao.weixin6_0;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BaseFragment extends Fragment {

    private String title = "default title";

    public static final String TITLE = "title";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView tv = new TextView(getContext());
        if(getArguments() != null){
            title = getArguments().getString(TITLE);
        }
        tv.setText(title);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }
}
