package core;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitUntilState;
import elements.Element;
import elements.HighlightedElementDecorator;
import elements.PageElement;
import java.time.Duration;

/** Базовый объект страницы: хранит страницу, создаёт обёрнутые элементы и ждёт адрес */
public abstract class BasePage<T extends BasePage<T>> {

  protected static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(30);

  protected final Page page;

  /** Базовый конструктор */
  protected BasePage(Page page) {
    this.page = page;
  }

  /** Возвращает себя для цепочки вызовов */
  @SuppressWarnings("unchecked")
  public T self() {
    return (T) this;
  }

  /** Единая точка создания элемента страницы и декоратора */
  protected Element element(Locator locator, String name) {
    return new HighlightedElementDecorator(new PageElement(locator, name));
  }

  /** Ждёт нужный адрес */
  protected void waitForUrl(String glob) {
    page.waitForURL(
        glob,
        new Page.WaitForURLOptions()
            .setWaitUntil(WaitUntilState.DOMCONTENTLOADED)
            .setTimeout(DEFAULT_TIMEOUT.toMillis()));
  }
}
