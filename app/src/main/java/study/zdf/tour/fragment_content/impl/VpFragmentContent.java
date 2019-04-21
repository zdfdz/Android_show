package study.zdf.tour.fragment_content.impl;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;

import study.zdf.tour.R;
import study.zdf.tour.fragment_content.impl.ChildPager.BasePager;
import study.zdf.tour.fragment_content.impl.ChildPager.LibPager;
import study.zdf.tour.fragment_content.impl.ChildPager.tourPager;

/**
 * @author ZhengDeFeng
 * @description:显示主页的ViewPage
 * @date :2019/3/30 22:28
 */
public class VpFragmentContent {
    private Activity mActivity;
    private ViewPager mVpContent;
    private ArrayList<BasePager> mPagerList;

    public VpFragmentContent(Activity activity) {
        mActivity = activity;
    }

    public View getView() {
        View view = View.inflate(mActivity, R.layout.viewpage_main_content, null);
        mVpContent = view.findViewById(R.id.vp_main_content);
        initLayout();//初始化布局
        return view;
    }

    private void initLayout() {
        mPagerList = new ArrayList<BasePager>();
        mPagerList.add(new tourPager(mActivity));
        mPagerList.add(new LibPager(mActivity));
        mVpContent.setAdapter(new AdapterVpContent());
//        SetCurrentPager();//设置当前页方法
    }

    public void SetCurrentPager(int i) {
        mVpContent.setCurrentItem(i,false);
    }


    class AdapterVpContent extends PagerAdapter {
        @Override
        public int getCount() {
            return mPagerList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            BasePager page = mPagerList.get(position);
            View view = page.initView();
            page.initData();
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
