package bookmall.vo;

public class UserVo {
    private long no;
    private String name;
    private String email;
    private String password;

    public UserVo(String name, String mail, String password, String phone) {
        this.name = name;
        this.email = mail;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
