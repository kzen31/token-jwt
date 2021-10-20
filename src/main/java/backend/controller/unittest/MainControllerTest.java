package backend.controller.unittest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MainControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

//    @Test
//    public void test() {
//        assertEquals("u", "a-i-u-e-o".split("-")[2]);
//    }

    @Test
    public void testRegister() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Content-Type", "application/json");

        ResponseEntity<Object> exchange = testRestTemplate.exchange(
                "http://localhost:" + port + "/register",
                HttpMethod.POST,
                new HttpEntity<>("{ \"username\": \"zain@mail.com\", \"fullname\": \"zain\", \"password\": \"password\" }", headers),
                Object.class
        );

        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        assertEquals("application/json", Objects.requireNonNull(exchange.getHeaders().get("Content-Type")).get(0));
    }

}