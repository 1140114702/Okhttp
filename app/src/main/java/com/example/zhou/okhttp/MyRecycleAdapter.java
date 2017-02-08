package com.example.zhou.okhttp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhou.okhttp.RecyclerBean.ResultBean.*;

import java.util.List;

/**
 * Created by zhou on 2016/12/9.
 */

public class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleAdapter.MyViewHolder> {

    private Context mContext;
    private List<DataBean> list;

    public MyRecycleAdapter(Context mContext, List<DataBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    private OnRVItemClickListener onRVItemClickListener;

    public void setOnRVItemClickListener(OnRVItemClickListener onRVItemClickListener) {
        this.onRVItemClickListener = onRVItemClickListener;
    }

    public interface OnRVItemClickListener {
        void setOnItemClick(View view, int position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item, null));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        DataBean bean = list.get(position);
        holder.setDate(bean);
        if (onRVItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    onRVItemClickListener.setOnItemClick(holder.itemView, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeData (int i) {
        list.remove(i);
        notifyItemRemoved(i);
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView textView;

        MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.recycler_image);
            textView = (TextView) itemView.findViewById(R.id.recycler_text);
        }

        public void setDate(DataBean bean) {
            textView.setText(bean.getTitle());
            Glide.with(mContext).load(bean.getThumbnail_pic_s()).into(imageView);
        }
    }
}
