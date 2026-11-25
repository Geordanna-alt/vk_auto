package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import core.BasePage;
import data.Customer;
import elements.Element;

/** Объект оформления заказа */
public final class CheckoutPage extends BasePage<CheckoutPage> {

  private final Element firstNameInput;
  private final Element lastNameInput;
  private final Element postalCodeInput;
  private final Element continueButton;
  private final Element finishButton;
  private final Element cancelButton;

  /** Страница оформления заказа */
  public CheckoutPage(Page page) {
    super(page);
    this.firstNameInput = element(page.getByTestId("firstName"), "First name");
    this.lastNameInput = element(page.getByTestId("lastName"), "Last name");
    this.postalCodeInput = element(page.getByTestId("postalCode"), "Postal code");
    this.continueButton = element(page.getByTestId("continue"), "Continue");
    this.finishButton = element(page.getByTestId("finish"), "Finish");
    this.cancelButton = element(page.getByTestId("cancel"), "Cancel");
  }

  /** Заголовок */
  public Locator title() {
    return page.getByTestId("title");
  }

  /** Поле имени */
  public Locator firstNameInput() {
    return firstNameInput.locator();
  }

  /** Поле фамилии */
  public Locator lastNameInput() {
    return lastNameInput.locator();
  }

  /** Поле индекса */
  public Locator postalCodeInput() {
    return postalCodeInput.locator();
  }

  /** Кнопка продолжения */
  public Locator continueButton() {
    return continueButton.locator();
  }

  /** Кнопка отмены */
  public Locator cancelButton() {
    return cancelButton.locator();
  }

  /** Сообщение об ошибке */
  public Locator errorMessage() {
    return page.getByTestId("error");
  }

  /** Кнопка завершения */
  public Locator finishButton() {
    return finishButton.locator();
  }

  /** Итого */
  public Locator total() {
    return page.getByTestId("total-label");
  }

  /** Заголовок завершения */
  public Locator completeHeader() {
    return page.getByTestId("complete-header");
  }

  /** Текст завершения */
  public Locator completeText() {
    return page.getByTestId("complete-text");
  }

  /** Кнопка возврата к товарам */
  public Locator backHomeButton() {
    return page.getByTestId("back-to-products");
  }

  /** Заполняет данные заказа */
  public CheckoutPage fillInfo(Customer customer) {
    firstNameInput.fill(customer.firstName());
    lastNameInput.fill(customer.lastName());
    postalCodeInput.fill(customer.postalCode());
    return self();
  }

  /** Без ожидания адреса: при ошибке валидации остаёмся на той же странице */
  public CheckoutPage continueToOverview() {
    continueButton.click();
    return self();
  }

  /** Завершает заказ */
  public CheckoutPage finish() {
    finishButton.click();
    waitForUrl("**/checkout-complete.html");
    return self();
  }

  /** Отменяет оформление */
  public CartPage cancel() {
    cancelButton.click();
    waitForUrl("**/cart.html");
    return Pages.cart(page);
  }
}
