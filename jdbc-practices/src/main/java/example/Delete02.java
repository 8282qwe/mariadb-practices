package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Delete02 {
    public static void main(String[] args) {
        System.out.println(delete(4L));
    }

    public static boolean delete(long id) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // 1. JDBC Driver 로딩
            Class.forName("org.mariadb.jdbc.Driver");

            // 2. 연결하기
            String url = "jdbc:mariadb://192.168.0.120:3306/webdb";
            conn = DriverManager.getConnection(url,"webdb","webdb");

            // 3. Statement 생성하기
            pstmt = conn.prepareStatement("delete from department where id=?;");

            // 4. Parameter Binding
            pstmt.setLong(1, id);

            // 5. SQL 실행
            result = pstmt.executeUpdate() == 1;

        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패: " + e.getMessage());
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

        return result;
    }
}
