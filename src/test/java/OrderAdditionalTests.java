package order;

import Courier.Courier;
import Courier.CourierGenerator;
import Courier.CourierMethod;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class OrderAdditionalTests {
    private OrderMethod orderClient = new OrderMethod();
    private CourierMethod courierClient = new CourierMethod();
    private CourierGenerator generator = new CourierGenerator();

    private int courierId;
    private int orderTrack;

    @After
    public void cleanup() {
        if (courierId > 0) {
            courierClient.delete(courierId);
        }
    }

    @Test
    public void getOrderByTrackTest() {
        Order order = new Order("Test", "User", "Address", "1", "+79991112233",
                1, "2023-01-01", "Comment", new String[]{"BLACK"});
        orderTrack = orderClient.create(order)
                .assertThat()
                .statusCode(201)
                .extract()
                .path("track");

        orderClient.getOrderByTrack(orderTrack)
                .assertThat()
                .statusCode(200)
                .body("order", notNullValue());
    }

    @Test
    public void acceptOrderTest() {
        // Создаем курьера
        Courier courier = generator.random();
        courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(courier);
        courierId = courierClient.checkLogin(loginResponse);

        // Создаем заказ
        Order order = new Order("Test", "User", "Address", "1", "+79991112233",
                1, "2025-01-01", "Comment", new String[]{"BLACK"});
        orderTrack = orderClient.create(order)
                .assertThat()
                .statusCode(201)
                .extract()
                .path("track");

        // Получаем ID заказа
        int orderId = orderClient.getOrderByTrack(orderTrack)
                .extract()
                .path("order.id");

        // Принимаем заказ
        orderClient.acceptOrder(orderId, courierId)
                .assertThat()
                .statusCode(200)
                .body("ok", equalTo(true));
    }
}
