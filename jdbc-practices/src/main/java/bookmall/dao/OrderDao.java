package bookmall.dao;

import bookmall.vo.BookVo;
import bookmall.vo.OrderBookVo;
import bookmall.vo.OrderVo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDao extends MyConnection {
    public void insert(OrderVo vo) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();

            // 3. Statement 생성하기
            pstmt = conn.prepareStatement("insert into orders values (null, ?, ?, ?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);

            // 4. Parameter Binding
            pstmt.setLong(1, vo.getUserNo());
            pstmt.setString(2, vo.getNumber());
            pstmt.setInt(3, vo.getPayment());
            pstmt.setString(4, vo.getShipping());
            pstmt.setString(5, vo.getStatus());
            pstmt.setString(6, new UserDao().findByNo(vo.getUserNo()));

            // 5. SQL 실행
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                vo.setNo(rs.getLong(1));
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e);
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
        ;
    }

    public void insertBook(OrderBookVo vo) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();

            // 3. Statement 생성하기
            pstmt = conn.prepareStatement("insert into orders_book values (?, ?, ?,?,?)");

            // 4. Parameter Binding
            pstmt.setLong(1, vo.getBookNo());
            pstmt.setLong(2, vo.getOrderNo());
            pstmt.setInt(3, vo.getQuantity());
            pstmt.setInt(4, vo.getPrice());
            pstmt.setString(5,new BookDao().findByNo(vo.getBookNo()));

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
        ;
    }

    public void deleteBooksByNo(long no) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();

            // 3. Statement 생성하기
            pstmt = conn.prepareStatement("delete from orders_book where orders_no = ?;");

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

    public void deleteByNo(long no) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();

            // 3. Statement 생성하기
            pstmt = conn.prepareStatement("delete from orders where no = ?;");

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

    public OrderVo findByNoAndUserNo(long No, long userNo) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();

            // 3. Statement 생성하기
            pstmt = conn.prepareStatement("select * from orders where no = ? and user_no = ?;");

            pstmt.setLong(1, No);
            pstmt.setLong(2, userNo);

            // 5. SQL 실행
            rs = pstmt.executeQuery();

            while (rs.next()) {
                return new OrderVo(rs.getLong("no"),rs.getInt("user_no"),rs.getString("number"),rs.getInt("payment"),rs.getString("shipping"),rs.getString("status"),rs.getString("user_name"));
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

    public List<OrderBookVo> findBooksByNoAndUserNo(long orderNo, long userNo) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<OrderBookVo> result = new ArrayList<>();

        try {
            conn = getConnection();

            // 3. Statement 생성하기
            pstmt = conn.prepareStatement("select ob.book_no,ob.orders_no,ob.quantity,ob.price,b.title from orders_book ob join book b on b.no = ob.book_no join orders o on ob.orders_no = o.no join user u on o.user_no = u.no where ob.orders_no = ? and u.no = ?;");

            pstmt.setLong(1, orderNo);
            pstmt.setLong(2, userNo);

            // 5. SQL 실행
            rs = pstmt.executeQuery();

            while (rs.next()) {
                OrderBookVo vo = new OrderBookVo();
                vo.setBookNo(rs.getLong(1));
                vo.setOrderNo(rs.getLong(2));
                vo.setQuantity(rs.getInt(3));
                vo.setPrice(rs.getInt(4));
                vo.setBookTitle(rs.getString(5));
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
}
