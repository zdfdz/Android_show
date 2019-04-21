package study.zdf.tour.fragment_content.impl.ChildPager;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;

import study.zdf.tour.R;

/**
 * @author ZhengDeFeng
 * @description:
 * @date :2019/3/31 9:07
 */
public class BasePager {
    //    调用时需传入Activity
    public Activity mActivity;
    //    标签页的root
    public FrameLayout Flroot;

    public BasePager(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_tab_base, null);
        Flroot = view.findViewById(R.id.fl_root);
        return view;
    }
    //    初始化数据
    public void initData(){

    }

}
