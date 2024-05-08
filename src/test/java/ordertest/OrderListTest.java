package ordertest;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import orderascooter.RequestForAnOrder;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class OrderListTest {
    RequestForAnOrder requestForAnOrder = new RequestForAnOrder();
    @Before
    public void setUp(){
        requestForAnOrder.setUp();}
    @Test
    @DisplayName("Получение списка заказов") //позитивный тест
    @Description("Проверка успешного получения списка заказов")
    public void checkCreateOrder(){
        requestForAnOrder.getOrders()
                .then().statusCode(200)
                .and()
                .assertThat().body("orders", notNullValue());
    }
}
