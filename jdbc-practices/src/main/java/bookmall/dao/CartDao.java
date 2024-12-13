package bookmall.dao;

import bookmall.vo.CartVo;
import bookmall.vo.UserVo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDao extends MyConnection {

    public void insert(CartVo vo) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();

            // 3. Statement 생성하기
            pstmt = conn.prepareStatement("insert into cart values (?,?,?)");

            // 4. Parameter Binding
            pstmt.setLong(1, vo.getUserNo());
            pstmt.setLong(2, vo.getBookNo());
            pstmt.setInt(3, vo.getQuantity());

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
        };
    }

    public List<CartVo> findByUserNo(long no) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CartVo> result = new ArrayList<>();

        try {
            conn = getConnection();

            // 3. Statement 생성하기
            pstmt = conn.prepareStatement("select user_no,book_no,quantity,title from cart join book b on b.no = cart.book_no where user_no = ?;");

            pstmt.setLong(1, no);

            // 5. SQL 실행
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CartVo vo = new CartVo(rs.getInt("user_no"),rs.getInt("book_no"),rs.getString("title"),rs.getInt("quantity"));
                result.add(vo);
            }
            return result;
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
        return result;
    }

    public void deleteByUserNoAndBookNo(long userNo, Long bookNo) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();

            // 3. Statement 생성하기
            pstmt = conn.prepareStatement("delete from cart where user_no = ? and book_no = ?;");

            // 4. Parameter Binding
            pstmt.setLong(1, userNo);
            pstmt.setLong(2, bookNo);

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
