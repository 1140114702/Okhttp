package com.example.zhou.okhttp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhou on 2016/12/9.
 */

public class MyRecyclerView extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<RecyclerBean.ResultBean.DataBean> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_recyclerview);
        requestData();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);

    }

    private void requestData() {
        String url = "http://v.juhe.cn/toutiao/index?type=top&key=f73e78527001c8928b0788683852ac96";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                final RecyclerBean bean = gson.fromJson(string, RecyclerBean.class);

                if (bean.getError_code() == 0) {
                    list.addAll(bean.getResult().getData());
                    handler.sendEmptyMessage(0);
                } else if (bean.getError_code() == 10001) {
                    handler.sendEmptyMessage(1);
                }


            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0){
                MyRecycleAdapter adapter = new MyRecycleAdapter(MyRecyclerView.this, list);
                mRecyclerView.setAdapter(adapter);
                adapter.setOnRVItemClickListener(new MyRecycleAdapter.OnRVItemClickListener() {
                    @Override
                    public void setOnItemClick(View view, int position) {
                        Toast.makeText(MyRecyclerView.this, ""+position, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            else Toast.makeText(MyRecyclerView.this, "keybudui", Toast.LENGTH_LONG).show();
        }
    };

}
