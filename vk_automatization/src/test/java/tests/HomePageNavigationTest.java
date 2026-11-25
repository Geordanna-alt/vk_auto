package tests;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import com.microsoft.playwright.Locator;
import data.TestData;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.Timeout;
import pages.Pages;

/** Тесты навигации по главной странице */
@DisplayName("Главная страница: навигация")
@Tag("navigation")
@Timeout(120)
class HomePageNavigationTest extends BaseTest {

  @TestFactory
  @DisplayName("Ссылки меню ведут на правильные URL")
  Stream<DynamicTest> menuLinks() {
    var homePage = Pages.home(page).open();

    return TestData.NAV_LINKS.entrySet().stream()
        .map(entry ->
            dynamicTest(
                entry.getKey(),
                () -> {
                  Locator link = homePage.navLink(entry.getKey());
                  assertAll(
                      () -> assertThat(link).isVisible(),
                      () -> assertEquals(entry.getValue(), link.getAttribute("href")));
                }));
  }

  @Test
  @DisplayName("Log In и Sign up ведут на страницы аккаунта")
  void accountLinks() {
    var homePage = Pages.home(page).open();

    assertAll(
        () -> assertEquals("/account/login", homePage.loginLink().getAttribute("href")),
        () -> assertEquals("/account/register", homePage.navLink("Sign up").getAttribute("href")));
  }

  @Test
  @DisplayName("Check Out ведёт в корзину")
  void checkoutLink() {
    var homePage = Pages.home(page).open();

    assertEquals("/cart", homePage.checkoutLink().getAttribute("href"));
  }
}
