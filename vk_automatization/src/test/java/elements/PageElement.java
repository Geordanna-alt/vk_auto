package elements;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.Locator;

/** Конкретный компонент: обёртка над локатором Playwright */
public final class PageElement implements Element {

  private final Locator locator;
  private final String name;

  /** Элемент страницы */
  public PageElement(Locator locator, String name) {
    this.locator = locator;
    this.name = name;
  }

  /** Имя */
  @Override
  public String name() {
    return name;
  }

  /** Локатор */
  @Override
  public Locator locator() {
    return locator;
  }

  /** Нажать */
  @Override
  public void click() {
    locator.click();
  }

  /** Заполнить */
  @Override
  public void fill(String value) {
    locator.fill(value);
  }

  /** Нажать клавишу */
  @Override
  public void press(String key) {
    locator.press(key);
  }

  /** Видим ли элемент */
  @Override
  public boolean isVisible() {
    return locator.isVisible();
  }

  /** Проверяет, что элемент видим */
  @Override
  public void shouldBeVisible() {
    assertThat(locator).isVisible();
  }

  /** Текст */
  @Override
  public String text() {
    return locator.innerText();
  }

  /** Получить атрибут */
  @Override
  public String getAttribute(String attributeName) {
    return locator.getAttribute(attributeName);
  }

  /** Количество */
  @Override
  public int count() {
    return locator.count();
  }
}
