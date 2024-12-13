package bookmall.dao;

import bookmall.vo.UserVo;
import emaillist.vo.EmailListVo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDao extends MyConnection{

    public void insert(UserVo vo) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();

            // 3. Statement 생성하기
            pstmt = conn.prepareStatement("insert into user values (null, ?, ?, password(?))", PreparedStatement.RETURN_GENERATED_KEYS);

            // 4. Parameter Binding
            pstmt.setString(1, vo.getName());
            pstmt.setString(2, vo.getEmail());
            pstmt.setString(3, vo.getPassword());

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

    public List<UserVo> findAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<UserVo> result = new ArrayList<>();

        try {
            conn = getConnection();

            // 3. Statement 생성하기
            pstmt = conn.prepareStatement("select * from user;");

            // 5. SQL 실행
            rs = pstmt.executeQuery();

            while (rs.next()) {
                UserVo vo = new UserVo(rs.getString("no"), rs.getString("name"), rs.getString("email"), rs.getString("password"));
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

    public String findByNo(Long no) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();

            // 3. Statement 생성하기
            pstmt = conn.prepareStatement("select name from user where no = ?;");

            pstmt.setLong(1, no);

            // 5. SQL 실행
            rs = pstmt.executeQuery();

            while (rs.next()) {
                return rs.getString("name");
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

    public void deleteByNo(long no) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();

            // 3. Statement 생성하기
            pstmt = conn.prepareStatement("delete from user where no = ?;");

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
