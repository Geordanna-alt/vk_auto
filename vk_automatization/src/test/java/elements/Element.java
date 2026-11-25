package elements;

import com.microsoft.playwright.Locator;

/** Компонент-роль для паттерна декоратора */
public interface Element {

  /** Имя */
  String name();

  /** Локатор */
  Locator locator();

  /** Нажать */
  void click();

  /** Заполнить */
  void fill(String value);

  /** Нажать клавишу */
  void press(String key);

  /** Видим ли элемент */
  boolean isVisible();

  /** Проверяет, что элемент видим */
  void shouldBeVisible();

  /** Текст */
  String text();

  /** Получить атрибут */
  String getAttribute(String name);

  /** Количество */
  int count();
}
