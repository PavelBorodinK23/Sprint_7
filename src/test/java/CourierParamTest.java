import courier.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class CourierParamTest {
    @Parameterized.Parameter()
    public Courier courier;

    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][]{
                {new Courier(null, "pass")},
                {new Courier(RandomStringUtils.randomAlphanumeric(5), null)}
        };
    }

    private CourierMethod client = new CourierMethod();

    @Test
    public void failCreatedTest() {
        client.create(courier)
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void failLoginTest() {
        client.login(courier)
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
}
