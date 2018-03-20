package mujina.idp;

import io.restassured.filter.cookie.CookieFilter;
import mujina.AbstractIntegrationTest;
import org.junit.Test;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_MOVED_TEMPORARILY;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.core.StringContains.containsString;

@TestPropertySource(properties = {"idp.expires:" + (Integer.MAX_VALUE / 2 - 1), "idp.clock_skew: " + (Integer.MAX_VALUE / 2 - 1)})
public class UserControllerTest extends AbstractIntegrationTest {

  @Test
  public void userResourceForAdmin() throws Exception {
    CookieFilter cookieFilter = login("admin", "secret", SC_MOVED_TEMPORARILY);

    given()
      .filter(cookieFilter)
      .get("/user.html")
      .then()
      .statusCode(SC_OK)
      .body(
        containsString("<h1>LAA Portal Mock Identity Provider</h1>"),
        containsString("<form id=\"loginData\" method=\"post\" action=\"/login\">"));
  }

  @Test
  public void userResourceForUser() throws Exception {
    CookieFilter cookieFilter = login("user", "secret", SC_MOVED_TEMPORARILY);

    given()
      .filter(cookieFilter)
      .get("/user.html")
      .then()
      .statusCode(SC_OK)
      .body(
        containsString("<h1>LAA Portal Mock Identity Provider</h1>"),
        containsString("<form id=\"loginData\" method=\"post\" action=\"/login\">"));
  }

  @Test
  public void userResourceForTestUser() throws Exception {
    CookieFilter cookieFilter = login("testuser", "secret", SC_MOVED_TEMPORARILY);

    given()
      .filter(cookieFilter)
      .get("/user.html")
      .then()
      .statusCode(SC_OK)
      .body(containsString("testuser"));
  }

  @Test
  public void indexResourceForAdmin() throws Exception {
    CookieFilter cookieFilter = login("admin", "secret", SC_MOVED_TEMPORARILY);

    given()
      .filter(cookieFilter)
      .get("/")
      .then()
      .statusCode(SC_OK)
      .body(
        containsString("<h1>LAA Portal Mock Identity Provider</h1>"),
        containsString("<a href=\"/user.html\">Login</a>"));
  }

  @Test
  public void indexResourceForUser() throws Exception {
    CookieFilter cookieFilter = login("user", "secret", SC_MOVED_TEMPORARILY);

    given()
      .filter(cookieFilter)
      .get("/")
      .then()
      .statusCode(SC_OK)
      .body(
        containsString("<h1>LAA Portal Mock Identity Provider</h1>"),
        containsString("<a href=\"/user.html\">Login</a>"));
  }

  @Test
  public void indexResourceForTestUser() throws Exception {
    CookieFilter cookieFilter = login("testuser", "secret", SC_MOVED_TEMPORARILY);

    given()
      .filter(cookieFilter)
      .get("/")
      .then()
      .statusCode(SC_OK)
      .body(containsString("testuser"));
  }

  @Test
  public void indexResourceForNotLoggedIn() {
    given()
      .get("/")
      .then()
      .statusCode(SC_OK)
      .body(containsString("Login"));
  }
}
