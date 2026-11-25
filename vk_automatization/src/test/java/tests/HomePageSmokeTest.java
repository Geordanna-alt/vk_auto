package tests;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import pages.Pages;

/** Быстрые тесты главной страницы */
@DisplayName("Главная страница: отображение")
@Tag("smoke")
@Timeout(120)
class HomePageSmokeTest extends BaseTest {

  @Test
  @DisplayName("Страница открывается, есть поиск и основные ссылки")
  void pageOpens() {
    var homePage = Pages.home(page).open();

    assertAll(
        () -> assertEquals("Sauce Demo", page.title()),
        () -> assertThat(homePage.searchInput()).isVisible(),
        () -> assertThat(homePage.searchButton()).isVisible(),
        () -> assertThat(homePage.loginLink()).isVisible());

    homePage.searchInputElement().shouldBeVisible();
    homePage.cartLinkElement().shouldBeVisible();
  }
}
