package bookshop.dao;

import bookshop.vo.AuthorVo;

import java.sql.*;

public class AuthorDao {

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

    public int insert(AuthorVo vo) {
        int count = 0;

        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement("insert into author values (null, ?)",Statement.RETURN_GENERATED_KEYS);
                ){

            // 4. Parameter Binding
            pstmt.setString(1, vo.getName());
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

    public int deleteById(Long id) {
        int count = 0;

        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement("delete from author where id = ?;");
        ){

            // 4. Parameter Binding
            pstmt.setLong(1, id);
            // 5. SQL 실행
            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return count;
    }

    public int deleteAll() {
        int count = 0;

        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement("delete from author;");
        ){
            // 5. SQL 실행
            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return count;
    }
}
