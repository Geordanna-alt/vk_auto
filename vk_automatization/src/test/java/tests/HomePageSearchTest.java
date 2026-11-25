package tests;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitUntilState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.Pages;

/** Тесты поиска на главной странице */
@DisplayName("Главная страница: поиск")
@Tag("search")
@Timeout(120)
class HomePageSearchTest extends BaseTest {

  /** Поиск с результатами */
  @Nested
  @DisplayName("Поиск с результатами")
  class WithResults {

    @ParameterizedTest(name = "поиск \"{0}\" находит {1}")
    @MethodSource("data.TestData#searchResults")
    @DisplayName("Поиск находит нужный товар")
    void searchFindsProduct(String query, String expectedProduct) {
      var homePage = Pages.home(page).open();

      homePage.search(query);
      page.waitForURL(
          "**/search**",
          new Page.WaitForURLOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));

      assertThat(homePage.productCard(expectedProduct)).isVisible();
    }
  }

  /** Поиск без результатов */
  @Nested
  @DisplayName("Поиск без результатов")
  class WithoutResults {

    @Test
    @DisplayName("Показывается сообщение, что ничего не найдено")
    void searchWithoutResults() {
      var homePage = Pages.home(page).open();

      homePage.search("zzzz");
      page.waitForURL(
          "**/search**",
          new Page.WaitForURLOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));

      assertThat(page.getByText("No results found for zzzz")).isVisible();
    }
  }
}
