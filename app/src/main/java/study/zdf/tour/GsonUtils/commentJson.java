package study.zdf.tour.GsonUtils;

import java.util.ArrayList;

/**
 * @author ZhengDeFeng
 * @description:
 * @date :2019/3/30 20:46
 */
public class commentJson {
    public ArrayList<comment> data;
    public class comment{

        public ArrayList<String> cat_comment_time;
        public ArrayList<String> cat_user_comment;
        public ArrayList<String> cat_user_name;
        public ArrayList<String> cat_user_pic;
    }
}
