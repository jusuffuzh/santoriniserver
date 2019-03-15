package ch.uzh.ifi.seal.soprafs19.integration;

import ch.uzh.ifi.seal.soprafs19.TestUser;
import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.controller.UserController;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTest {

    private static String testUsername = "testUsername";
    private static String testUsername2 = "testUsername2";
    private static String testName = "testName";
    private static String testName2 = "testName2";
    private static String testPassword = "1234";
    private static String testPassword2 = "asdf";
    private static LocalDate testBirthday = LocalDate.parse("1992-03-28");
    private static LocalDate testBirthday2 = LocalDate.parse("2001-10-02");

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserController userController;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void Test1() throws Exception {
        this.setup();
        User testUser = createTestUser();

        String body = this.composeBody(testUser);

        this.mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(body))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void Test2() throws Exception {
        this.setup();
        User testUser = createTestUser();
        this.userService.createUser(testUser);
        Long id = this.userRepository.findByUsername(testUser.getUsername()).getId();

        this.mockMvc.perform(get("/users/" + id.toString())
                .header("Content-Type", "application/json")
                .header("token", testUser.getToken()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.username").value(testUser.getUsername()))
                .andExpect(jsonPath("$.token").value(testUser.getToken()));
    }

    @Test
    public void Test3() throws Exception {
        this.setup();
        User testUser = createTestUser();
        userService.createUser(testUser);
        Long id = this.userRepository.findByUsername(testUser.getUsername()).getId();
        testUser.setId(id);
        testUser.setUsername("asdf");
        testUser.setBirthday(LocalDate.parse("2010-02-03"));
        String body = this.composeBody(testUser);

        this.mockMvc.perform(put("/users/" + id.toString())
                .header("token", testUser.getToken())
                .header("Content-Type", "application/json;charset=UTF-8")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(body))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    private void setup() {
        this.userRepository.deleteAll();
        Assert.assertNotNull(this.userRepository);
        Assert.assertNotNull(this.userController);
        Assert.assertNotNull(this.userService);
        Assert.assertThat(userRepository.findByUsername("testUsername"), anyOf(is(nullValue()), is(Optional.empty())));
        Assert.assertThat(userRepository.findByUsername("testUsername2"), anyOf(is(nullValue()), is(Optional.empty())));
    }

    private User createTestUser() {
        User testUser = new User();
        testUser.setId(2L);
        testUser.setName(testName);
        testUser.setUsername(testUsername);
        testUser.setPassword(testPassword);
        testUser.setBirthday(testBirthday);
        testUser.setToken("testToken");
        testUser.setStatus(UserStatus.OFFLINE);
        testUser.setRegistrationDate(LocalDate.now());

        return testUser;
    }

    private String composeBody(User user) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        TestUser testUser = new TestUser(user);
        String body = mapper.writeValueAsString(testUser);
        return body;
    }
}
