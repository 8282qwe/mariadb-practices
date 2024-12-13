package emaillist.dao;

import emaillist.vo.EmailListVo;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmailListDaoTest {
    private static long count = 0L;

    @BeforeAll
    public static void setUp() {
        count = new EmailListDao().count();
    }

    @Test
    @Order(1)
    public void insertTest() {
        EmailListVo vo = new EmailListVo();
        vo.setFirstName("둘");
        vo.setLastName("리");
        vo.setEmail("dooly@gmail.com");

        Boolean result = new EmailListDao().insert(vo);

        assertTrue(result);
    }

    @Test
    @Order(2)
    public void findAllTest() {
        List<EmailListVo> list = new EmailListDao().findAll();

        assertEquals(count+1, list.size());
    }

    @Test
    @Order(3)
    void deleteByEmailTest() {
        Boolean result = new EmailListDao().deleteByEmail("dooly@gmail.com");
        assertTrue(result);
    }

    @AfterAll
    public static void tearDown() {

    }
}