package com.example.admin.musicdemo.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.admin.musicdemo.R;
import com.example.admin.musicdemo.activitys.PlayMusicActivity;
import com.example.admin.musicdemo.models.MusicModel;

import java.util.List;

/**
 * Created by yjl on 2020/2/4.
 */

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder> {

    private Context mContext;
    private  View mItemView;
    private RecyclerView mRv;
    private boolean isCalcaulationRvHeight;
    private List<MusicModel> mDataSource;
    public MusicListAdapter(Context context,RecyclerView recyclerView,List<MusicModel> dataSource)
    {
        mContext=context;
        mRv=recyclerView;
        this.mDataSource=dataSource;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mItemView=LayoutInflater.from(mContext).inflate(R.layout.item_list_music,viewGroup,false);
        return new ViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        setRecyclerViewHeight();

        final MusicModel musicModel = mDataSource.get(i);
        Glide.with(mContext)
                .load(musicModel.getPoster())
                .into(viewHolder.ivIcon);

        viewHolder.tvName.setText(musicModel.getName());
        viewHolder.tvAuthor.setText(musicModel.getAuthor());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PlayMusicActivity.class);
                intent.putExtra(PlayMusicActivity.MUSIC_ID,musicModel.getMusicId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }

    /*
    设置RecyclerView的高度，只在于ScrollView连用的时候需要计算
    * 1、获取ItemView的高度
    * 2、itemView的数量
    * 3、使用itemViewHeight * itemViewNum = RecyclerView的高度*/
    private void setRecyclerViewHeight()
    {
        if (isCalcaulationRvHeight || mRv ==null)
        {
            return;
        }
        isCalcaulationRvHeight = true;
//        获取ItemView的高度
        RecyclerView.LayoutParams itemViewlp=(RecyclerView.LayoutParams)mItemView.getLayoutParams();
//        itemView的数量
        int itemCount = getItemCount();
//        使用itemViewHeight * itemViewNum = RecyclerView的高度
        int recyclerViewHeight = itemViewlp.height * itemCount;
//        设置RecycleView高度
        LinearLayout.LayoutParams rvlp=(LinearLayout.LayoutParams)mRv.getLayoutParams();
        rvlp.height = recyclerViewHeight;
        mRv.setLayoutParams(rvlp);
    }
    class ViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        ImageView ivIcon;
        TextView tvName,tvAuthor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView=itemView;
            ivIcon=itemView.findViewById(R.id.iv_icon);
            tvName=itemView.findViewById(R.id.tv_name);
            tvAuthor=itemView.findViewById(R.id.tv_author);
        }
    }
}
