package bookshop.example;

public class Book {
    private int no;
    private String title;
    private String author;
    private int stateCode;

    public Book(int no, String title, String author) {
        this.no = no;
        this.title = title;
        this.author = author;
        this.stateCode = 1;
    }

    public void rent() {
        System.out.printf("%s이(가) 대여 됐습니다.\n",title);
        this.stateCode = 0;
    }

    public void print() {
        System.out.printf("책 제목:%s, 작가:%s, 대여유무:%s\n",title,author,stateCode==0?"대여중":"대여가능");
    }

    public int getNo() {
        return no;
    }
}
