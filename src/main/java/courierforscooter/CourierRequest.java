package courierforscooter;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import urlforapi.Url;

import static io.restassured.RestAssured.given;

public class CourierRequest extends Url {
    private Courier courier;
    private final static String CREATING_A_COURIER = "/api/v1/courier"; //апи для создания курьера
    private final static String COURIER_LOGIN_IN_THE_SYSTEM ="/api/v1/courier/login"; //апи для логина курьера в системе

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    @Step("Создание курьера, проверка кода ответа")
    public Response createCourier(){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post(CREATING_A_COURIER);
        return response;
    }

    @Step("Авторизация курьера, получение его id и проверка кода ответа")
    public Response loginCourier(){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post(COURIER_LOGIN_IN_THE_SYSTEM);
        return response;
    }

    @Step("Удаление курьера")
    public void deleteCourier(){
        Integer id =
                given()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post(COURIER_LOGIN_IN_THE_SYSTEM)
                        .then().extract().body().path("id");
        if (id != null) {
            given()
                    .delete(CREATING_A_COURIER+ "/{id}", id.toString());}
    }
}
