import { element, by, promise, ElementFinder } from 'protractor';

export class HostComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-host div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class HostUpdatePage {
    pageTitle = element(by.id('jhi-host-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameInput = element(by.id('field_name'));
    descriptionInput = element(by.id('field_description'));
    dnsInput = element(by.id('field_dns'));
    portInput = element(by.id('field_port'));
    stateSelect = element(by.id('field_state'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setNameInput(name): promise.Promise<void> {
        return this.nameInput.sendKeys(name);
    }

    getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    setDescriptionInput(description): promise.Promise<void> {
        return this.descriptionInput.sendKeys(description);
    }

    getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
    }

    setDnsInput(dns): promise.Promise<void> {
        return this.dnsInput.sendKeys(dns);
    }

    getDnsInput() {
        return this.dnsInput.getAttribute('value');
    }

    setPortInput(port): promise.Promise<void> {
        return this.portInput.sendKeys(port);
    }

    getPortInput() {
        return this.portInput.getAttribute('value');
    }

    setStateSelect(state): promise.Promise<void> {
        return this.stateSelect.sendKeys(state);
    }

    getStateSelect() {
        return this.stateSelect.element(by.css('option:checked')).getText();
    }

    stateSelectLastOption(): promise.Promise<void> {
        return this.stateSelect
            .all(by.tagName('option'))
            .last()
            .click();
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
