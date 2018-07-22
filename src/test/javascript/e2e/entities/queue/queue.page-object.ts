import { element, by, promise, ElementFinder } from 'protractor';

export class QueueComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-queue div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class QueueUpdatePage {
    pageTitle = element(by.id('jhi-queue-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameInput = element(by.id('field_name'));
    countInput = element(by.id('field_count'));
    descriptionInput = element(by.id('field_description'));
    hostSelect = element(by.id('field_host'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setNameInput(name): promise.Promise<void> {
        return this.nameInput.sendKeys(name);
    }

    getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    setCountInput(count): promise.Promise<void> {
        return this.countInput.sendKeys(count);
    }

    getCountInput() {
        return this.countInput.getAttribute('value');
    }

    setDescriptionInput(description): promise.Promise<void> {
        return this.descriptionInput.sendKeys(description);
    }

    getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
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
