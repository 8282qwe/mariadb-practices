package bookshop.dao;

import bookshop.vo.AuthorVo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuthorDaoTest {

    private static AuthorDao dao = new AuthorDao();
    private static AuthorVo vo = new AuthorVo();

    @Test
    public void insertTest() {
        vo.setName("칼세이건");

        dao.insert(vo);

        assertNotNull(vo.getId());
    }

    @AfterAll
    public static void cleanup() {
        dao.deleteById(vo.getId());
    }

}
