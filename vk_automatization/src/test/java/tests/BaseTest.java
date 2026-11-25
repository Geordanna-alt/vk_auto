package tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/** Базовый тестовый класс */
public abstract class BaseTest {

  protected Playwright playwright;
  protected Browser browser;
  protected BrowserContext context;
  protected Page page;

  @BeforeEach
  void setUp() {
    playwright = Playwright.create();
    playwright.selectors().setTestIdAttribute("data-test");
    browser = playwright.chromium().launch();
    context = browser.newContext();
    page = context.newPage();
  }

  @AfterEach
  void tearDown() {
    if (context != null) {
      context.close();
    }
    if (browser != null) {
      browser.close();
    }
    if (playwright != null) {
      playwright.close();
    }
  }
}
