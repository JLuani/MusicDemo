package com.example.admin.musicdemo.activitys;

import android.os.Bundle;


import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.musicdemo.R;
import com.example.admin.musicdemo.adapters.MusicGridAdapter;
import com.example.admin.musicdemo.adapters.MusicListAdapter;
import com.example.admin.musicdemo.helps.RealmHelp;
import com.example.admin.musicdemo.models.MusicSourceModel;
import com.example.admin.musicdemo.views.GridSpaceItemDecoration;

public class MainActivity extends BaseActivity {

    private RecyclerView mRvGrid,mRvList;
    private MusicGridAdapter mGridAdapter;
    private MusicListAdapter mListAdapter;

    private RealmHelp mRealHelp;
    private MusicSourceModel mMusicSourceModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    private void initData(){
        mRealHelp = new RealmHelp();
        mMusicSourceModel = mRealHelp.getMusicSource();
    }
    private void initView() {
        initNavBar(false,"网易云音乐",true);

        mRvGrid=fd(R.id.rv_grid);
        mRvGrid.setLayoutManager(new GridLayoutManager(this,3));
        //RecyclerView自带分割线
//        mRvGrid.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        mRvGrid.addItemDecoration(new GridSpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.albumMarginSize),mRvGrid));
//        禁止RecycleView自己滑动
        mRvGrid.setNestedScrollingEnabled(false);
        mGridAdapter=new MusicGridAdapter(this,mMusicSourceModel.getAlbum());
        mRvGrid.setAdapter(mGridAdapter);


        mRvList=fd(R.id.rv_list);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mRvList.setNestedScrollingEnabled(false);
        mListAdapter=new MusicListAdapter(this,mRvList,mMusicSourceModel.getHot());
        mRvList.setAdapter(mListAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealHelp.close();
    }
}
