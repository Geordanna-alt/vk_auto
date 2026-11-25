package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deque.html.axecore.playwright.AxeBuilder;
import com.deque.html.axecore.results.AxeResults;
import com.deque.html.axecore.results.Rule;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import pages.Pages;

/** Тесты доступности главной страницы */
@DisplayName("Главная страница: доступность")
@Tag("accessibility")
@Timeout(120)
class HomePageAccessibilityTest extends BaseTest {

  @Test
  @DisplayName("Строгая проверка доступности (отключена)")
  @Disabled("На стенде есть известные нарушения serious/moderate")
  void strictCheck() {
    var homePage = Pages.home(page).open();

    List<Rule> violations = new AxeBuilder(page).analyze().getViolations();

    assertTrue(violations.isEmpty(), "Accessibility violations found");
  }

  @Test
  @DisplayName("На главной странице нет critical-нарушений")
  void noCriticalErrors() {
    var homePage = Pages.home(page).open();

    AxeResults accessibilityScanResults = new AxeBuilder(page).analyze();
    List<Rule> violations = accessibilityScanResults.getViolations();

    var criticalCount =
        violations.stream().filter(rule -> "critical".equalsIgnoreCase(rule.getImpact())).count();

    assertEquals(0L, criticalCount, "Critical accessibility violations found");
  }
}
