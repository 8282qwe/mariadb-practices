package bookmall.vo;

public class CartVo {
    private long userNo;
    private long bookNo;
    private String bookTitle;
    private int quantity;

    public CartVo(long userNo, long bookNo, String bookTitle, int quantity) {
        this.userNo = userNo;
        this.bookNo = bookNo;
        this.bookTitle = bookTitle;
        this.quantity = quantity;
    }

    public CartVo() {

    }

    public long getUserNo() {
        return userNo;
    }

    public void setUserNo(long userNo) {
        this.userNo = userNo;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public long getBookNo() {
        return bookNo;
    }

    public void setBookNo(long bookNo) {
        this.bookNo = bookNo;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
