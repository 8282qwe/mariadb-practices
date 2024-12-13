package bookmall.vo;

public class CategoryVo {
    private long no;
    private String name;

    public CategoryVo(String name) {
        this.name = name;
    }

    public CategoryVo(Long no, String name) {
        this(name);
        this.no = no;
    }

    public long getNo() {
        return no;
    }

    public void setNo(long no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
