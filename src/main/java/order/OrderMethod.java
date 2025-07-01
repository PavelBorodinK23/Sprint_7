package order;

import base.BaseMethod;
import io.restassured.response.ValidatableResponse;

public class OrderMethod extends BaseMethod {
    protected static final String ORDER_URI = API_PREFIX + "orders";

    public ValidatableResponse create(Order order) {
        return spec()
                .body(order)
                .when()
                .post(ORDER_URI)
                .then().log().all();
    }

    public ValidatableResponse getOrderList() {
        return spec()
                .when()
                .get(ORDER_URI)
                .then().log().all();
    }

    public ValidatableResponse getOrderByTrack(int track) {
        return spec()
                .queryParam("t", track)
                .when()
                .get(ORDER_URI + "/track")
                .then().log().all();
    }

    public ValidatableResponse acceptOrder(int orderId, int courierId) {
        return spec()
                .queryParam("courierId", courierId)
                .when()
                .put(ORDER_URI + "/accept/" + orderId)
                .then().log().all();
    }
}
