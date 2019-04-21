package study.zdf.tour.GsonUtils;

import java.util.ArrayList;

/**
 * @author ZhengDeFeng
 * @description:
 * @date :2019/3/31 11:33
 */
public class indexJson {
    public ArrayList<ViewItem> data;//返回的首页数据
    public ArrayList<Integer> extend;

    public class ViewItem {
        public String ctrip;//携程评分
        public String mafengwo;//马蜂窝评分
        public String piclink;//图片链接

        @Override
        public String toString() {
            return "ViewItem{" + "ctrip='" + ctrip + '\'' + ", mafengwo='" + mafengwo + '\'' + ", piclink='" + piclink + '\'' + ", qunar='" + qunar + '\'' + ", recommend_score='" + recommend_score + '\'' + ", title='" + title + '\'' + '}';
        }
        public String qunar;//去哪评分
        public String recommend_score;//平均值
        public String title;//景点名称
        public String url;//评论url
    }
}
