package study.zdf.tour.fragment_content.impl.ChildPager;

import android.app.Activity;
import android.view.View;

import study.zdf.tour.R;

/**
 * @author ZhengDeFeng
 * @description:
 * @date :2019/3/31 9:13
 */
public class LibPager extends BasePager {
    public LibPager(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public View initView() {
        return super.initView();
    }

    @Override
    public void initData() {
        View view = View.inflate(mActivity, R.layout.page_lib,null);

        Flroot.addView(view);
    }
}
