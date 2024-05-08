package couriertest;

import courierforscooter.Courier;
import courierforscooter.CourierRequest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest {
    CourierRequest courierRequest = new CourierRequest();
    Courier courier = new Courier ("maha","4321");

    @Before
    public void setUp(){
        courierRequest.setUp();
    }
    @Test
    @DisplayName("Авторизация курьера в системе") //позитивный тест
    @Description("Возможность авторизации с корректными логином и паролем")
    public void checkLoginCourier(){
        courierRequest.setCourier(courier);
        courierRequest.createCourier();
        courierRequest.loginCourier()
                .then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Авторизация без логина") //негативный тест
    @Description("Отсутствие возможности авторизоваться без логина")
    public void checkLoginWithoutLogin(){
        courierRequest.setCourier(new Courier("","4321"));
        courierRequest.loginCourier()
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Авторизация без пароля") //негативный тест
    @Description("Отсутствие возможности авторизоваться без пароля")
    public void checkLoginWithoutPassword(){
        courierRequest.setCourier(new Courier("maha",""));
        courierRequest.loginCourier()
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Авторизация несуществующим курьером") //негативный тест
    @Description("Отсутствие возможности авторизации несуществующего курьра")
    public void checkLoginNonExisting(){
        courierRequest.setCourier(courier);
        courierRequest.loginCourier()
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    @DisplayName("Авторизация с указанием неверного логина") //негативный тест
    @Description("Отсутствие возможности авторизации с вводом неверного логина")
    public void checkLoginIncorrectLogin() {
        courierRequest.setCourier(courier);
        courierRequest.createCourier();
        courierRequest.setCourier(new Courier("sobaka","4321"));
        courierRequest.loginCourier()
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
        courierRequest.setCourier(courier);
    }

    @Test
    @DisplayName("Авторизация с указанием неверного пароля") //негативный тест
    @Description("Отсутствие возможности авторизации с вводом неверного пароля")
    public void checkLoginIncorrectPassword() {
        courierRequest.setCourier(courier);
        courierRequest.createCourier();
        courierRequest.setCourier(new Courier("maha","9876"));
        courierRequest.loginCourier()
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
        courierRequest.setCourier(courier);
    }
    @After
    public void cleanData(){
        courierRequest.deleteCourier();
    }

}
