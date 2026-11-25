package data;

import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

/** Реестр данных и поставщик данных для тестов */
public final class TestData {

  public static final String SHOP_URL = "https://sauce-demo.myshopify.com/";
  public static final String SAUCEDEMO_URL = "https://www.saucedemo.com/";

  public static final Credentials STANDARD_USER = new Credentials("standard_user", "secret_sauce");
  public static final Credentials LOCKED_USER = new Credentials("locked_out_user", "secret_sauce");
  public static final Customer CUSTOMER = new Customer("John", "Doe", "12345");

  public static final Map<String, String> NAV_LINKS =
      Map.of(
          "Home", "/",
          "Catalog", "/collections/all",
          "Blog", "/blogs/news",
          "About Us", "/pages/about-us");

  private TestData() {}

  /** Неверные данные входа */
  public static Stream<Arguments> invalidCredentials() {
    return Stream.of(
        Arguments.of(
            new Credentials("standard_user", "wrong_password"),
            "Epic sadface: Username and password do not match any user in this service"),
        Arguments.of(LOCKED_USER, "Epic sadface: Sorry, this user has been locked out."));
  }

  /** Результаты поиска */
  public static Stream<Arguments> searchResults() {
    return Stream.of(
        Arguments.of("Noir", "Noir jacket"),
        Arguments.of("Grey", "Grey jacket"),
        Arguments.of("Striped", "Striped top"));
  }
}
