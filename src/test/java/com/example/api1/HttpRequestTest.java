package com.example.api1;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Ignore
public class HttpRequestTest {

    /*
    * kayanya test nya berat, karena panggil servic dan repo nya juga*/

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void greetingShouldReturnDefaultMessage() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/first",
                String.class)).contains("hello");
    }

    @Test
    public void greetingShouldReturnUnauthorized() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/sec",
                String.class)).contains("Unauthorized");
    }
}
