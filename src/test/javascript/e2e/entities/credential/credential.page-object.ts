import { element, by, promise, ElementFinder } from 'protractor';

export class CredentialComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-credential div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CredentialUpdatePage {
    pageTitle = element(by.id('jhi-credential-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    usernameInput = element(by.id('field_username'));
    passwordInput = element(by.id('field_password'));
    hostSelect = element(by.id('field_host'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setUsernameInput(username): promise.Promise<void> {
        return this.usernameInput.sendKeys(username);
    }

    getUsernameInput() {
        return this.usernameInput.getAttribute('value');
    }

    setPasswordInput(password): promise.Promise<void> {
        return this.passwordInput.sendKeys(password);
    }

    getPasswordInput() {
        return this.passwordInput.getAttribute('value');
    }

    hostSelectLastOption(): promise.Promise<void> {
        return this.hostSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    hostSelectOption(option): promise.Promise<void> {
        return this.hostSelect.sendKeys(option);
    }

    getHostSelect(): ElementFinder {
        return this.hostSelect;
    }

    getHostSelectedOption() {
        return this.hostSelect.element(by.css('option:checked')).getText();
    }

    save(): promise.Promise<void> {
        return this.saveButton.click();
    }

    cancel(): promise.Promise<void> {
        return this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}
