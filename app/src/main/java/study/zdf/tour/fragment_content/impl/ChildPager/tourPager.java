package study.zdf.tour.fragment_content.impl.ChildPager;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import study.zdf.tour.CommentActivity;
import study.zdf.tour.GsonUtils.indexJson;
import study.zdf.tour.MainActivity;
import study.zdf.tour.R;
import study.zdf.tour.Utils.OKManager;
import study.zdf.tour.fragment_content.ContentFragment;

/**
 * @author ZhengDeFeng
 * @description:
 * @date :2019/3/31 9:13
 */
public class tourPager extends BasePager {

    private RecyclerView mRvDisplay;
    private String JSON_PATH = "http://39.96.187.58:8080/test.json";
    private study.zdf.tour.GsonUtils.indexJson indexJson;
    private SwipeRefreshLayout mRefresh;

    public tourPager(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public View initView() {
        return super.initView();
    }

    @Override
    public void initData() {
        View view = View.inflate(mActivity, R.layout.page_tour, null);
        mRvDisplay = view.findViewById(R.id.rv_main_content);
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvDisplay.setLayoutManager(manager);
        //请求数据
        getServiceData();
        //下拉刷新
        mRefresh = view.findViewById(R.id.refreshLayout_tourPager);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getServiceData();
                    }
                }).start();
            }
        });
        Flroot.addView(view);
    }

    //    设置旅游首页RecyclerView.Adapter
    class RvDisplayAdapter extends RecyclerView.Adapter<tourPager.Rvhold> {
        @NonNull
        @Override
        public Rvhold onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = View.inflate(mActivity, R.layout.item_tour, null);
            Rvhold rvhold = new Rvhold(view);
            return rvhold;
        }

        @Override
        public void onBindViewHolder(@NonNull Rvhold rvhold, int i) {
            study.zdf.tour.GsonUtils.indexJson.ViewItem item = indexJson.data.get(i);
            Picasso.with(mActivity).load(item.piclink).fit().into(rvhold.mIvTour);
            rvhold.mTvTitle.setText(item.title);
            rvhold.mTvMaFeng.setText(item.mafengwo);
            rvhold.mTvQunar.setText(item.qunar);
            rvhold.mTvRecommend.setText(item.recommend_score);
            rvhold.mTvXieChe.setText(item.ctrip);

            rvhold.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mActivity,CommentActivity.class);
                    intent.putExtra("urlString",item.url);
                    intent.putExtra("PicLink",item.piclink);
                    intent.putExtra("TourName",item.title);
                    mActivity.startActivity(intent);

                }
            });

        }

        @Override
        public int getItemCount() {
            return indexJson.data.size();
        }
    }


    //    设置旅游首页 RecyclerView.ViewHolder
    class Rvhold extends RecyclerView.ViewHolder {

        private ImageView mIvTour;
        private TextView mTvTitle;
        private CardView mCardView;
        private TextView mTvQunar;
        private TextView mTvMaFeng;
        private TextView mTvXieChe;
        private TextView mTvRecommend;

        public Rvhold(@NonNull View itemView) {
            super(itemView);
            initItemView();//初始化和设置所有的itemView

        }

        private void initItemView() {
            mIvTour = itemView.findViewById(R.id.iv_item_tour);
            mTvTitle = itemView.findViewById(R.id.tv_item_title);
            mCardView = itemView.findViewById(R.id.cv_item_tour);
            mTvQunar = itemView.findViewById(R.id.tv_item_qunar);
            mTvMaFeng = itemView.findViewById(R.id.tv_item_magengwo);
            mTvXieChe = itemView.findViewById(R.id.tv_item_xiecheng);
            mTvRecommend = itemView.findViewById(R.id.tv_item_Recommend);
            //CardView设置
            mCardView.setRadius(8);//设置图片圆角的半径大小
            mCardView.setCardElevation(8);//设置阴影部分大小
            mCardView.setContentPadding(5, 5, 5, 5);//设置图片距离阴影大小
            //设置TextView的背景透明度和字体
            mTvTitle.setBackgroundColor(0x66000000);
            mTvTitle.setTextColor(0xffffffff);
        }
    }

    private void getServiceData() {
        OKManager okManager = OKManager.getInstance();
        okManager.asyncJsonStringByURL(JSON_PATH, new OKManager.Func1() {

            @Override
            public void onResponse(String result) {
//                Log.i("11111",result);
//                解析Json数据
                Gson gson = new Gson();
                indexJson = gson.fromJson(result, indexJson.class);
                mRefresh.setRefreshing(false);
                mRvDisplay.setAdapter(new RvDisplayAdapter());

            }
        });
    }

}
