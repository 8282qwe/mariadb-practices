package example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Select02 {
    public static void main(String[] args) {
        List<DepartmentVo> result = search("팀");
        for (DepartmentVo departmentVo : result) {
            System.out.println(departmentVo);
        }
    }

    public static List<DepartmentVo> search(String keyword) {
        List<DepartmentVo> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 1. JDBC Driver 로딩
            Class.forName("org.mariadb.jdbc.Driver");

            // 2. 연결하기
            String url = "jdbc:mariadb://192.168.0.120:3306/webdb";
            conn = DriverManager.getConnection(url, "webdb", "webdb");

            // 3. Statement 생성하기
            pstmt = conn.prepareStatement("select id,name from department where name like ?;");

            // 4. Parameter Binding
            pstmt.setString(1, "%" + keyword + "%");

            // 5. SQL 실행
            rs = pstmt.executeQuery();

            // 6. 결과 처리
            while (rs.next()) {
                DepartmentVo vo = new DepartmentVo(rs.getLong(1), rs.getString(2));
                result.add(vo);
            }

            return result;
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패: " + e.getMessage());
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
