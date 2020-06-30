package com.example.gaojichonci01_3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.gaojichonci01_3.R;
import com.example.gaojichonci01_3.bean.DatasBean;

import java.util.ArrayList;
import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
  public   List<DatasBean> list = new ArrayList<>();
    private int ITEM_ONE = 1;
    private int ITEM_TWO = 2;
        private onClickSelect onClickSelect;

    public void setOnClickSelect(RvAdapter.onClickSelect onClickSelect) {
        this.onClickSelect = onClickSelect;
    }

    public RvAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<DatasBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_ONE) {
            final View view = LayoutInflater.from(context).inflate(R.layout.item_one, parent, false);
            return new ViewHolderOne(view);
        } else {
            final View view = LayoutInflater.from(context).inflate(R.layout.item_two, parent, false);
            return new ViewHolderTwo(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final int viewType = holder.getItemViewType();
        if (viewType == ITEM_ONE){
            ViewHolderOne viewHolderOne = (ViewHolderOne) holder;
            final DatasBean datasBean = list.get(position);
            viewHolderOne.one_tv.setText(datasBean.getTitle());
            final RoundedCorners roundedCorners = new RoundedCorners(20);
            final RequestOptions override = RequestOptions.bitmapTransform(roundedCorners)
                    .override(300, 300);
            final RequestOptions requestOptions = RequestOptions.circleCropTransform();
            Glide.with(context).load(datasBean.getEnvelopePic()).apply(override).apply(requestOptions)
                    .into(viewHolderOne.one_img);


        }else {
                ViewHolderTwo viewHolderTwo = (ViewHolderTwo) holder;
            final DatasBean datasBean = list.get(position+1);
            viewHolderTwo.two_tv.setText(datasBean.getTitle());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickSelect!=null){
                    onClickSelect.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (position%2 == 0){
            return ITEM_ONE;
        }else {
            return ITEM_TWO;
        }
    }

    @Override
    public int getItemCount() {
        return list.size()-1;
    }


    class ViewHolderOne extends RecyclerView.ViewHolder {
        public View rootView;
        public ImageView one_img;
        public TextView one_tv;

        public ViewHolderOne(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.one_img = (ImageView) rootView.findViewById(R.id.one_img);
            this.one_tv = (TextView) rootView.findViewById(R.id.one_tv);
        }

    }

    class ViewHolderTwo extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView two_tv;

        public ViewHolderTwo(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.two_tv = (TextView) rootView.findViewById(R.id.two_tv);
        }

    }
    public interface onClickSelect{
        void onClick(int position);
    }
}
