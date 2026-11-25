package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import core.Loadable;
import data.Registration;
import data.TestData;
import elements.Element;

/** Объект страницы регистрации магазина sauce-demo.myshopify.com */
public final class SignUpPage extends Loadable<SignUpPage> {

  private static final String URL = TestData.SHOP_URL + "account/register";

  private final Element heading;
  private final Element firstNameInput;
  private final Element lastNameInput;
  private final Element emailInput;
  private final Element passwordInput;
  private final Element createButton;

  /** Страница регистрации */
  public SignUpPage(Page page) {
    super(page, URL, DEFAULT_TIMEOUT);
    this.heading =
        element(
            page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Create Account")),
            "Create Account heading");
    this.firstNameInput =
        element(page.locator("#create_customer input[name='customer[first_name]']"), "First name");
    this.lastNameInput =
        element(page.locator("#create_customer input[name='customer[last_name]']"), "Last name");
    this.emailInput =
        element(page.locator("#create_customer input[name='customer[email]']"), "Email");
    this.passwordInput =
        element(page.locator("#create_customer input[name='customer[password]']"), "Password");
    this.createButton =
        element(
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create")),
            "Create button");
  }

  /** Проверяет загрузку страницы */
  @Override
  protected boolean isLoaded() {
    return page.url().startsWith(URL) && firstNameInput.isVisible();
  }

  /** Заголовок */
  public Locator heading() {
    return heading.locator();
  }

  /** Поле имени */
  public Locator firstNameInput() {
    return firstNameInput.locator();
  }

  /** Поле фамилии */
  public Locator lastNameInput() {
    return lastNameInput.locator();
  }

  /** Поле почты */
  public Locator emailInput() {
    return emailInput.locator();
  }

  /** Поле пароля */
  public Locator passwordInput() {
    return passwordInput.locator();
  }

  /** Кнопка создания */
  public Locator createButton() {
    return createButton.locator();
  }

  /** Заполняет форму */
  public SignUpPage fillForm(Registration registration) {
    firstNameInput.fill(registration.firstName());
    lastNameInput.fill(registration.lastName());
    emailInput.fill(registration.email());
    passwordInput.fill(registration.password());
    return self();
  }

  /** Отправляет форму */
  public void submit() {
    createButton.click();
  }
}
