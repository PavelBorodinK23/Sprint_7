import courier.*;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CourierCreateTest {
    private CourierGenerator generator = new CourierGenerator();
    private CourierMethod client = new CourierMethod();
    private Courier courier;

    @After
    public void deleteCourier() {
        ValidatableResponse response = client.login(courier);
        int courierId = client.checkLogin(response);
        if (courierId > 0) {
            client.delete(courierId);
        }
    }

    @Test
    public void createdCourierTest() {
        courier = generator.random();
        client.create(courier)
                .assertThat()
                .statusCode(201)
                .body("ok", is(true));
    }

    @Test
    public void doubleCreatedTest() {
        courier = generator.random();
        client.create(courier);
        client.create(courier)
                .assertThat()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }
}
