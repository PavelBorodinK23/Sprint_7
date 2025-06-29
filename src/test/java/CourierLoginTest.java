import Courier.*;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CourierLoginTest {
    private CourierGenerator generator = new CourierGenerator();
    private CourierMethod client = new CourierMethod();
    private int courierId;

    @After
    public void deleteCourier() {
        if (courierId > 0) {
            client.delete(courierId);
        }
    }

    @Test
    public void successLoginTest() {
        Courier courier = generator.random();
        client.create(courier);
        ValidatableResponse response = client.login(courier);
        courierId = client.checkLogin(response);
        assert courierId != 0;
    }

    @Test
    public void incorrectLogin() {
        Courier courier = generator.random();
        client.login(courier)
                .assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
