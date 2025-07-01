package Courier;

import Base.BaseMethod;
import io.restassured.response.ValidatableResponse;

public class CourierMethod extends BaseMethod {
    protected static final String COURIER_URI = API_PREFIX + "courier/";

    public ValidatableResponse create(Courier courier) {
        return spec()
                .body(courier)
                .when()
                .post(COURIER_URI)
                .then().log().all();
    }

    public ValidatableResponse login(Courier courier) {
        return spec()
                .body(courier)
                .when()
                .post(COURIER_URI + "login")
                .then().log().all();
    }

    public ValidatableResponse delete(int courierId) {
        return spec()
                .when()
                .delete(COURIER_URI + courierId)
                .then().log().all();
    }

    public int checkLogin(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(200)
                .extract()
                .path("id");
    }
}
