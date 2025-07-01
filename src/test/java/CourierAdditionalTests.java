import courier.*;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CourierAdditionalTests {
    private CourierGenerator generator = new CourierGenerator();
    private CourierMethod client = new CourierMethod();
    private int courierId;

    @After
    public void deleteCourier() {
        if (courierId > 0) {
            client.delete(courierId)
                    .assertThat()
                    .statusCode(200)
                    .body("ok", equalTo(true));
        }
    }

    @Test
    public void deleteNonExistentCourierTest() {
        client.delete(999999)
                .assertThat()
                .statusCode(404);
    }

    @Test
    public void loginWithoutPasswordTest() {
        Courier courier = new Courier(generator.random().getLogin(), null);
        client.login(courier)
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
}
