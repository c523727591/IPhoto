package com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iphoto.R;

/**
 * Created by duke on 16-6-25.
 */
public class TestFragment extends BaseFragment {
    private String mContent = "default";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootLayout = inflater.inflate(R.layout.test_fragment_layout, null);
        TextView textView = (TextView)rootLayout.findViewById(R.id.test_fragment_text_view_id);
        textView.setText(mContent);
        return rootLayout;
    }

    public void setContent(String content) {
        mContent = content;
    }
}
