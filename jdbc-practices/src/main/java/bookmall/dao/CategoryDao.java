package bookmall.dao;

import bookmall.vo.CategoryVo;
import bookmall.vo.UserVo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CategoryDao extends MyConnection {
    public void insert(CategoryVo vo) {
        {
            Connection conn = null;
            PreparedStatement pstmt = null;

            try {
                conn = getConnection();

                // 3. Statement 생성하기
                pstmt = conn.prepareStatement("insert into category values (null, ?)", PreparedStatement.RETURN_GENERATED_KEYS);

                // 4. Parameter Binding
                pstmt.setString(1, vo.getName());

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
    }

    public List<CategoryVo> findAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CategoryVo> result = new ArrayList<>();

        try {
            conn = getConnection();

            // 3. Statement 생성하기
            pstmt = conn.prepareStatement("select * from category;");

            // 5. SQL 실행
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CategoryVo vo = new CategoryVo(rs.getLong("no"),rs.getString("name"));
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

    public void deleteByNo(long no) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();

            // 3. Statement 생성하기
            pstmt = conn.prepareStatement("delete from category where no = ?;");

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
