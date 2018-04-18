package net.zxw.bookcity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author zxw
 * @Email 18316275391@163.com
 * @describe TODO
 */

public class BookListResult {


    /**
     * status : 1
     * data : [{"bookname":"幻兽少年","bookfile":"http://www.imooc.com/data/teacher/down/幻兽少年.txt"},{"bookname":"魔界的女婿","bookfile":"http://www.imooc.com/data/teacher/down/魔界的女婿.txt"},{"bookname":"盘龙","bookfile":"http://www.imooc.com/data/teacher/down/盘龙.txt"},{"bookname":"庆余年","bookfile":"http://www.imooc.com/data/teacher/down/庆余年.txt"},{"bookname":"武神空间","bookfile":"http://www.imooc.com/data/teacher/down/武神空间.txt"}]
     * msg : 成功
     */

    @SerializedName("status")
    private int mStatus;
    @SerializedName("msg")
    private String mMessage;
    @SerializedName("data")
    private List<Book> mData;

    public int getMStatus() {
        return mStatus;
    }

    public void setMStatus(int mStatus) {
        this.mStatus = mStatus;
    }

    public String getMMsg() {
        return mMessage;
    }

    public void setMMsg(String mMsg) {
        this.mMessage = mMsg;
    }

    public List<Book> getMData() {
        return mData;
    }

    public void setMData(List<Book> mData) {
        this.mData = mData;
    }

    public static class Book {
        /**
         * bookname : 幻兽少年
         * bookfile : http://www.imooc.com/data/teacher/down/幻兽少年.txt
         */

        private String bookname;
        private String bookfile;

        public String getBookname() {
            return bookname;
        }

        public void setBookname(String bookname) {
            this.bookname = bookname;
        }

        public String getBookfile() {
            return bookfile;
        }

        public void setBookfile(String bookfile) {
            this.bookfile = bookfile;
        }
    }
}
