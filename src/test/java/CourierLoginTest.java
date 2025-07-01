import courier.*;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CourierLoginTest {
    private CourierGenerator generator = new CourierGenerator();
    private CourierMethod client = new CourierMethod();
    private Courier courier;
    private int courierId;

    @Before
    public void setup() {
        // Создаем курьера перед каждым тестом
        courier = generator.random();
        client.create(courier);
    }

    @After
    public void deleteCourier() {
        // Удаляем курьера после каждого теста
        if (courier != null) {
            ValidatableResponse response = client.login(courier);
            courierId = client.checkLogin(response);
            if (courierId > 0) {
                client.delete(courierId);
            }
        }
    }

    @Test
    public void successLoginTest() {
        ValidatableResponse response = client.login(courier);
        courierId = client.checkLogin(response);
        assert courierId != 0;
    }

    @Test
    public void incorrectLoginTest() {
        Courier invalidCourier = generator.random(); // Создаем нового курьера, который не был зарегистрирован
        client.login(invalidCourier)
                .assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
