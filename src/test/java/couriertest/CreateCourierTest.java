package couriertest;

import courierforscooter.Courier;
import courierforscooter.CourierRequest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CreateCourierTest {
    Courier courier = new Courier("maha", "1234", "mariya");
    CourierRequest courierRequest = new CourierRequest();

    @Before
    public void setUp(){
        courierRequest.setUp();
    }

    @Test
    @DisplayName("Создание курьера") //позитивный тест
    @Description("Успешное создание курьера")
    public void checkCreateCourier(){
        courierRequest.setCourier(courier);
        courierRequest.createCourier()
                .then().assertThat().body("ok", is(true))
                .and()
                .statusCode(201);
    }

    @Test
    @DisplayName("Создание уже существующего курьера") //негативный тест
    @Description("Отсутствие возможности на создание дубликата курьера")
    public void checkCreateDuplicateCourier(){
        courierRequest.setCourier(courier);
        courierRequest.createCourier();
        courierRequest.createCourier()
                .then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
    }

    @Test
    @DisplayName("Создание курьера без указания логина") //негативный тест
    @Description("Отсутствие возможности создать курьера без указания логин")
    public void checkCreateCourierWithoutLogin(){
        courierRequest.setCourier(new Courier("","1234","mariya"));
        courierRequest.createCourier()
                .then().statusCode(400)
                .and()
                .assertThat().body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }
    @Test
    @DisplayName("Создание курьера без указания пароля") //негативный тест
    @Description("Отсутствие возможности создать курьера без указания пароля")
    public void checkCreateCourierWithoutPassword(){
        courierRequest.setCourier(new Courier("maha","","mariya"));
        courierRequest.createCourier()
                .then().statusCode(400)
                .and()
                .assertThat().body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без указания имени") //позитивный тест
    @Description("Возможность создать курьера без указания имени")
    public void checkCreateCourierWithoutName() {
        courierRequest.setCourier(new Courier("maha", "1234", ""));
        courierRequest.createCourier()
                .then().assertThat().body("ok", is(true))
                .and()
                .statusCode(201);
    }

    @After
    public void cleanData(){
        courierRequest.deleteCourier();
    }
}
