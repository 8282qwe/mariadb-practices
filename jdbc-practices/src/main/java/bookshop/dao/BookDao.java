package bookshop.dao;

import bookshop.vo.BookVo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

    private static Connection getConnection() throws SQLException {
        Connection conn = null;

        try {
            // 1. JDBC Driver 로딩
            Class.forName("org.mariadb.jdbc.Driver");

            // 2. 연결하기
            String url = "jdbc:mariadb://192.168.0.120:3306/webdb";
            conn = DriverManager.getConnection(url, "webdb", "webdb");

        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패: " + e.getMessage());
        }
        return conn;
    }

    public int insert(BookVo vo) {
        int count = 0;

        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement("insert into book (title, author_id) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
        ) {

            // 4. Parameter Binding
            pstmt.setString(1, vo.getTitle());
            pstmt.setLong(2, vo.getAuthorId());
            // 5. SQL 실행
            count = pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            vo.setId(rs.next() ? rs.getLong(1) : null);
            rs.close();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return count;
    }

    public int deleteAll() {
        int count = 0;

        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement("delete from book;");
        ) {
            // 5. SQL 실행
            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return count;
    }

    public int update(Long no, String status) {
        int count = 0;

        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement("update book set status = ? where id=?;");
        ) {
            pstmt.setString(1, status);
            pstmt.setLong(2, no);

            // 5. SQL 실행
            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return count;
    }

    public List<BookVo> findAll() {
        List<BookVo> books = new ArrayList<>();

        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement("select * from book a join author as b where a.author_id = b.id;");
        ) {
            // 5. SQL 실행
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                BookVo vo = new BookVo();
                vo.setId(rs.getLong("a.id"));
                vo.setTitle(rs.getString("a.title"));
                vo.setAuthorId(rs.getLong("a.author_id"));
                vo.setAuthorName(rs.getString("b.name"));
                vo.setStatus(rs.getString("a.status"));
                books.add(vo);
            }

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return books;
    }
}
