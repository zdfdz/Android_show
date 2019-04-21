package study.zdf.tour;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import study.zdf.tour.GsonUtils.commentJson;
import study.zdf.tour.Utils.OKManager;

public class CommentActivity extends AppCompatActivity {
    private RecyclerView mRvComtent;
    private LinearLayoutManager manager;
    private OKManager okManager;
    private commentJson fromJson;
    private String urlString;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ImageView mIvCommentPic;
    private String picLink;
    private String tourName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);

        mIvCommentPic = findViewById(R.id.iv_CommentPic);


        //下拉刷新
        SwipeRefreshLayout mRefresh = findViewById(R.id.refreshLayout);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "刷新成功", Toast.LENGTH_SHORT).show();
                                mRefresh.setRefreshing(false);
                            }
                        });
                    }
                }).start();
            }
        });

//        获取转过来的url
        Intent intent = getIntent();
//        获取评论URL
        urlString = intent.getStringExtra("urlString");
//        获取景点图片链接(json没有做好,直接传递也不麻烦,就不就该json了)
        picLink = intent.getStringExtra("PicLink");
//        获取景点名称(json没有做好,直接传递也不麻烦,就不就该json了)
        tourName = intent.getStringExtra("TourName");
        initCommentPicName();
        mRvComtent = findViewById(R.id.rv_showComment);
        //反面教材,能用getApplicationContext()就用getApplicationContext()
        manager = new LinearLayoutManager(CommentActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvComtent.setLayoutManager(manager);
        mRvComtent.addItemDecoration(new DeviderDecoration(getApplicationContext()));
        getJson();//请求数据
    }

    /**
     * 初始化评论页的图片和标题
     */
    private void initCommentPicName() {
        mCollapsingToolbarLayout.setTitle(tourName);
        Picasso.with(CommentActivity.this).load(picLink).into(mIvCommentPic);
    }

    /**
     * 请求评论json数据
     */
    private void getJson() {
        okManager = OKManager.getInstance();
        okManager.asyncJsonStringByURL(urlString, new OKManager.Func1() {
            @Override
            public void onResponse(String result) {
                Gson gson = new Gson();
                fromJson = gson.fromJson(result, commentJson.class);
                Log.i("111111111", fromJson.data.size() + "zz");
                mRvComtent.setAdapter(new CommentRvAdapter());
            }
        });
    }

    //设置CommentRecyclerView的Adapter
    class CommentRvAdapter extends RecyclerView.Adapter<commentViewHolder> {

        @Override
        public commentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = View.inflate(CommentActivity.this, R.layout.item_comment, null);
            commentViewHolder holder = new commentViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(commentViewHolder commentViewHolder, int i) {
            commentJson.comment comment = fromJson.data.get(i);
            commentViewHolder.mUserName.setText(comment.cat_user_name.get(0));
            commentViewHolder.mComment.setText(comment.cat_user_comment.get(0).trim());
            commentViewHolder.mSubTime.setText(comment.cat_comment_time.get(0).trim());
            Picasso.with(CommentActivity.this).load(comment.cat_user_pic.get(0)).into(commentViewHolder.mHeadPic);


//            点击显示详情信息
            commentViewHolder.mllItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //弹出详情页显示信息
                    View view = View.inflate(getApplicationContext(), R.layout.pop_detailed, null);
                    TextView mTvUserName = view.findViewById(R.id.tv_pop_userName);
                    TextView mTvComment = view.findViewById(R.id.tv_pop_comment);
                    TextView mTvTime = view.findViewById(R.id.tv_pop_time);
                    CircleImageView mCivShow = view.findViewById(R.id.rIv_pop_show);
                    //设置数据
                    mTvTime.setText(comment.cat_comment_time.get(0));
                    mTvUserName.setText(comment.cat_user_name.get(0));
                    mTvComment.setText(comment.cat_user_comment.get(0));
                    Picasso.with(CommentActivity.this).load(comment.cat_user_pic.get(0)).into(mCivShow);

                    DisplayMetrics dm = getResources().getDisplayMetrics();
                    PopupWindow pop = new PopupWindow(view, dm.widthPixels, dm.heightPixels / 2 + 100);
                    // 设置点击窗口外边窗口消失
                    //2、通过Resources获取
                    pop.setOutsideTouchable(true);

                    // 设置此参数获得焦点，否则无法点击

                    pop.setFocusable(true);
                    pop.showAtLocation(findViewById(R.id.ll_commentItem), Gravity.BOTTOM | Gravity.CENTER, 10, 10);
                }
            });

        }

        @Override
        public int getItemCount() {
            return fromJson.data.size();
        }
    }

    //设置ViewHolder
    class commentViewHolder extends RecyclerView.ViewHolder {

        private TextView mComment;
        private TextView mSubTime;
        private TextView mUserName;
        private CircleImageView mHeadPic;
        private LinearLayout mllItem;

        public commentViewHolder(View itemView) {
            super(itemView);
            mllItem = itemView.findViewById(R.id.ll_commentItem);
            mComment = itemView.findViewById(R.id.tv_comment);
            mSubTime = itemView.findViewById(R.id.tv_SubMtime);
            mUserName = itemView.findViewById(R.id.tv_userName);
            mHeadPic = itemView.findViewById(R.id.rIv_headPic);
        }
    }

//    设置item间距、分割线

    public class DeviderDecoration extends RecyclerView.ItemDecoration {

        private int deviderHeight;
        private Paint dividerPaint;

        public DeviderDecoration(Context context) {
            //设置画笔
            dividerPaint = new Paint();
            //设置分割线颜色
            dividerPaint.setColor(context.getResources().getColor(R.color.colorBlack));
            //设置分割线宽度
            deviderHeight = context.getResources().getDimensionPixelSize(R.dimen.divider_height);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            //改变宽度
            outRect.bottom = deviderHeight;
            outRect.left = 15;
            outRect.right = 15;
            outRect.bottom = 15;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildPosition(view) == 0) outRect.top = 15;
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            //得到列表所有的条目
            int childCount = parent.getChildCount();
            //得到条目的宽和高
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            for (int i = 0; i < childCount - 1; i++) {
                View view = parent.getChildAt(i);
                //计算每一个条目的顶点和底部 float值
                float top = view.getBottom();
                float bottom = view.getBottom() + deviderHeight;
                //重新绘制
                c.drawRect(left, top, right, bottom, dividerPaint);
            }
        }
    }


}
