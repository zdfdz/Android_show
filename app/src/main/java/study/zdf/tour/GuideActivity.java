package study.zdf.tour;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import study.zdf.tour.Utils.SpUtils;

public class GuideActivity extends AppCompatActivity {
    private ViewPager mVpContent;
    private Button mBtnStart;
    private int[] mPicSrc;
    private ArrayList<ImageView> mImgList; //存放ImageView
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        //初始化控件
        init();
        //初始化数据
        initData();
        //设置Viewpage适配器
        mVpContent.setAdapter(new myPagerAdapter());
        //设置viewPager滑动监听
        mVpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                Toast.makeText(GuideActivity.this,"i = "+i+"sss mimage"+mImgList.size(),Toast.LENGTH_SHORT);
            }

            @Override
            public void onPageSelected(int i) {
                if (i == mImgList.size()-1) {
                    mBtnStart.setVisibility(View.VISIBLE);
//                    Log.i("guideActivity",i+""+"mImgList.size()="+mImgList.size()+"");
//                    Button事件监听
                    mBtnStart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                            startActivity(intent);
                            SpUtils.setBoolean(GuideActivity.this,"if_enter",false);
                            finish();
                        }
                    });
                } else {
                    mBtnStart.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initData() {
        mImgList = new ArrayList<ImageView>();
        mPicSrc = new int[]{R.drawable.ic_guide_bg_01, R.drawable.ic_guide_bg_02, R.drawable.ic_guide_bg_03};
        for (int i = 0; i < mPicSrc.length; i++) {
            ImageView view = new ImageView(GuideActivity.this);
            Picasso.with(getApplicationContext()).load(mPicSrc[i]).fit().into(view);
//            view.setBackgroundResource(mPicSrc[i]);
            mImgList.add(view);
        }
        //将button设置为隐藏
        mBtnStart.setVisibility(View.INVISIBLE);
    }

    //设置PagerAdapter适配器
    class myPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mImgList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView view = mImgList.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    //初始化View
    private void init() {
        mVpContent = findViewById(R.id.vp_guide);
        mBtnStart = findViewById(R.id.btn_start);
    }

}
