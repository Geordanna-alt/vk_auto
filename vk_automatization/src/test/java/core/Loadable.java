package core;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.WaitUntilState;
import java.time.Duration;

/** Загружаемый компонент: страница умеет открываться и проверять загрузку */
public abstract class Loadable<T extends Loadable<T>> extends BasePage<T> {

  private final String url;
  private final Duration timeout;
  private boolean loaded;

  /** Создаёт загружаемую страницу */
  protected Loadable(Page page, String url, Duration timeout) {
    super(page);
    this.url = url;
    this.timeout = timeout;
  }

  /** Открывает страницу и проверяет загрузку */
  public T open() {
    if (loaded && isLoaded()) {
      return self();
    }

    load();

    if (!isLoaded()) {
      throw new IllegalStateException(getClass().getSimpleName() + " didn't load " + url);
    }

    loaded = true;
    return self();
  }

  /** Обратная совместимость со старыми тестами, которые вызывали переход */
  public T navigate() {
    return open();
  }

  /** Выполняет переход */
  protected void load() {
    try {
      navigateToUrl();
    } catch (PlaywrightException firstAttempt) {
      navigateToUrl();
    }
    page.waitForURL(
        currentUrl -> currentUrl.startsWith(url),
        new Page.WaitForURLOptions()
            .setWaitUntil(WaitUntilState.DOMCONTENTLOADED)
            .setTimeout(timeout.toMillis()));
  }

  private void navigateToUrl() {
    page.navigate(
        url,
        new Page.NavigateOptions()
            .setWaitUntil(WaitUntilState.DOMCONTENTLOADED)
            .setTimeout(timeout.toMillis()));
  }

  /** Проверяет, что страница загружена */
  protected abstract boolean isLoaded();
}
