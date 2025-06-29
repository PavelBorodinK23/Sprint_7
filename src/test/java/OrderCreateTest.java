import order.Order;
import order.OrderMethod;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreateTest {
    private OrderMethod order = new OrderMethod();

    @Parameterized.Parameter()
    public String[] color;

    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK","GREY"}},
                {new String[]{}}
        };
    }

    @Test
    public void orderCreateTest() {
        Order info = new Order("Name","Last","address","metro","phone",5,"2025-06-06","comment",color);
        order.create(info)
                .assertThat()
                .statusCode(201)
                .body("track", notNullValue());
    }
}
