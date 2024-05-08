package orderascooter;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import urlforapi.Url;

import static io.restassured.RestAssured.given;

public class RequestForAnOrder extends Url {
    private Order order;
    private final static String GETTING_A_LIST_OF_ORDERS = "/api/v1/orders"; //апи для получения списка заказов

    public void setOrder(Order order) {
        this.order = order;
    }

    @Step("Создание заказа, проверка кода ответа и номера заказа(track)")
    public Response createOrder (){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(order)
                        .when()
                        .post(GETTING_A_LIST_OF_ORDERS);
        return response;
    }

    @Step("Получение списка заказов, проверка что он не пустой, проверка кода ответа")
    public Response getOrders (){
        Response response =
                given()
                        .get(GETTING_A_LIST_OF_ORDERS);
        return  response;
    }
}
