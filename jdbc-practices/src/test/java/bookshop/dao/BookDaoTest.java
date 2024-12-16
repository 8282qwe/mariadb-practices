package bookshop.dao;


import bookshop.vo.AuthorVo;
import bookshop.vo.BookVo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BookDaoTest {
    private static AuthorDao authorDao = new AuthorDao();
    private static BookDao bookDao = new BookDao();
    private static AuthorVo mockAuthorVo = new AuthorVo();
    private static BookVo bookVo = new BookVo();

    @BeforeAll
    public static void setup() {
        mockAuthorVo.setName("칼세이건");
        authorDao.insert(mockAuthorVo);
    }

    @Test
    public void insertTest() {
        bookVo.setTitle("코스모스");
        bookVo.setAuthorId(mockAuthorVo.getId());

        bookDao.insert(bookVo);

        assertNotNull(bookVo.getId());
    }

    @AfterAll
    public static void cleanup() {
        bookDao.deleteAll();
        authorDao.deleteById(mockAuthorVo.getId());
    }
}
