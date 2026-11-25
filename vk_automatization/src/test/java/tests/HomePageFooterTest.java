package tests;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import java.util.regex.Pattern;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import pages.Pages;

/** Тесты футера главной страницы */
@DisplayName("Главная страница: футер")
@Tag("footer")
@Timeout(120)
class HomePageFooterTest extends BaseTest {

  @Test
  @DisplayName("Есть About Us, копирайт и иконки оплаты")
  void footerContent() {
    var homePage = Pages.home(page).open();

    assertAll(
        () ->
            assertThat(
                    homePage
                        .footer()
                        .getByRole(
                            AriaRole.HEADING, new Locator.GetByRoleOptions().setName("About Us")))
                .isVisible(),
        () ->
            assertThat(
                    homePage
                        .footer()
                        .getByText(Pattern.compile("Copyright © \\d{4} Sauce Demo"))
                        .first())
                .isVisible(),
        () -> assertThat(homePage.footer().getByAltText("We accept Visa")).isVisible());
  }
}
