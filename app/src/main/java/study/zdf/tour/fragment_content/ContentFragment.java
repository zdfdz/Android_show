package study.zdf.tour.fragment_content;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import java.util.ArrayList;

import study.zdf.tour.R;
import study.zdf.tour.fragment_content.impl.ChildPager.BasePager;
import study.zdf.tour.fragment_content.impl.ChildPager.LibPager;
import study.zdf.tour.fragment_content.impl.ChildPager.tourPager;
import study.zdf.tour.fragment_content.impl.VpFragmentContent;

/**
 * @author ZhengDeFeng
 * @description:
 * @date :2019/3/30 21:51
 */
public class ContentFragment extends RootMainFragment {
    private View view;
    private RadioGroup mRgBottomTab;
    private FrameLayout mFlRoot;
    private ViewPager mVpContent;
    private ArrayList<BasePager> mPagerList;

    @Override
    public View initView() {
        view = View.inflate(mActivity, R.layout.fragment_main_content, null);
        initData();
        return view;
    }

    @Override
    public void initData() {
        mRgBottomTab = view.findViewById(R.id.rg_bottomTab);
//        mFlRoot = view.findViewById(R.id.fl_content_display);
        mVpContent = view.findViewById(R.id.vp_main_content);
        initLayout();//初始化布局
        mRgBottomTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbtn_tour: {
                        mVpContent.setCurrentItem(0,false);
                        break;
                    }
                    case R.id.rbtn_lib: {
                        mVpContent.setCurrentItem(1,false);
                        break;
                    }

                }
            }
        });
    }

    private void initLayout() {
        mPagerList = new ArrayList<BasePager>();
        mPagerList.add(new tourPager(mActivity));
        mPagerList.add(new LibPager(mActivity));
        mVpContent.setAdapter(new AdapterVpContent());
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
