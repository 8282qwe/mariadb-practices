package bookmall.dao;

import bookmall.vo.BookVo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookDao extends MyConnection{

    public void insert(BookVo vo) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();

            // 3. Statement 생성하기
            pstmt = conn.prepareStatement("insert into book values (null, ?, ?,?)", PreparedStatement.RETURN_GENERATED_KEYS);

            // 4. Parameter Binding
            pstmt.setString(1, vo.getTitle());
            pstmt.setInt(2, vo.getPrice());
            pstmt.setLong(3, vo.getCategoryNo());

            // 5. SQL 실행
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                vo.setNo(rs.getLong(1));
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
            }
        };
    }

    public String findByNo(long no) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();

            // 3. Statement 생성하기
            pstmt = conn.prepareStatement("select title from book where no = ?;");

            pstmt.setLong(1, no);

            // 5. SQL 실행
            rs = pstmt.executeQuery();

            while (rs.next()) {
                return rs.getString("title");
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
            }
        }
        return null;
    }

    public void deleteByNo(Long no) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();

            // 3. Statement 생성하기
            pstmt = conn.prepareStatement("delete from book where no = ?;");

            // 4. Parameter Binding
            pstmt.setLong(1, no);

            // 5. SQL 실행
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
            }
        }
    }
}
