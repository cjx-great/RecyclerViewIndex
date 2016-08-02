package com.cjx.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cjx.R;
import com.cjx.adapter.RecyclerAdapter;
import com.cjx.adapter.DividerItemDecoration;
import com.cjx.data.Constant;
import com.cjx.view.IndexLinearLayout;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView = null;
    private IndexLinearLayout indexLinearLayout = null;

    private LinearLayoutManager linearLayoutManager = null;
    private RecyclerAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        recyclerView = (RecyclerView) findViewById(R.id.content);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        adapter = new RecyclerAdapter(this, Constant.CONTACTS);
        recyclerView.setAdapter(adapter);

        indexLinearLayout = (IndexLinearLayout) findViewById(R.id.index_layout);
        //索引点击事件
        indexLinearLayout.setOnIndexClickListener(new IndexLinearLayout.OnIndexClickListener() {
            @Override
            public void onLetter(String index) {
                linearLayoutManager.scrollToPositionWithOffset(adapter.getScrollPosition(index), 0);
            }

            @Override
            public void onArrow() {
                linearLayoutManager.scrollToPositionWithOffset(0,0);
            }
        });
    }
}
