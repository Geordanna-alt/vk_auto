package elements;

import com.microsoft.playwright.Locator;

/** Декоратор: подсвечивает элемент перед действием и пишет действие в лог */
public final class HighlightedElementDecorator implements Element {

  private final Element delegate;

  /** Декоратор подсветки элемента */
  public HighlightedElementDecorator(Element delegate) {
    this.delegate = delegate;
  }

  /** Имя */
  @Override
  public String name() {
    return delegate.name();
  }

  /** Локатор */
  @Override
  public Locator locator() {
    return delegate.locator();
  }

  /** Нажать */
  @Override
  public void click() {
    highlight();
    System.out.printf("[click] %s%n", name());
    delegate.click();
  }

  /** Заполнить */
  @Override
  public void fill(String value) {
    highlight();
    System.out.printf("[fill] %s = %s%n", name(), value);
    delegate.fill(value);
  }

  /** Нажать клавишу */
  @Override
  public void press(String key) {
    delegate.press(key);
  }

  /** Видим ли элемент */
  @Override
  public boolean isVisible() {
    return delegate.isVisible();
  }

  /** Проверяет, что элемент видим */
  @Override
  public void shouldBeVisible() {
    delegate.shouldBeVisible();
  }

  /** Текст */
  @Override
  public String text() {
    return delegate.text();
  }

  /** Получить атрибут */
  @Override
  public String getAttribute(String name) {
    return delegate.getAttribute(name);
  }

  /** Количество */
  @Override
  public int count() {
    return delegate.count();
  }

  private void highlight() {
    locator()
        .evaluate(
            "el => {"
                + " el.style.outline = '2px solid magenta';"
                + " setTimeout(() => el.style.outline = '', 300);"
                + "}");
  }
}
