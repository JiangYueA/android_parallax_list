package com.parallax.list.activtiy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.parallax.list.R;
import com.parallax.list.adapter.ParallaxListViewAdapter;
import com.parallax.list.widget.listview.ParallaxListView;

/**
 * Created by jiangyue on 16/4/27.
 */
public class MainActivity extends Activity {

    private ParallaxListView listView;

    private ParallaxListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        listView = (ParallaxListView) this.findViewById(R.id.parallax_list_view);

        listView.setTransformItem(new ParallaxListView.ListTransformer() {
            @Override
            public void transformItem(View view, float deltaY) {
                adapter.parallaxView(view, deltaY, listView.getHeight());
            }
        });

        adapter = new ParallaxListViewAdapter(this);
        listView.setAdapter(adapter);
    }
}