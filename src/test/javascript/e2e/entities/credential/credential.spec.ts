import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { CredentialComponentsPage, CredentialUpdatePage } from './credential.page-object';

describe('Credential e2e test', () => {
    let navBarPage: NavBarPage;
    let credentialUpdatePage: CredentialUpdatePage;
    let credentialComponentsPage: CredentialComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Credentials', () => {
        navBarPage.goToEntity('credential');
        credentialComponentsPage = new CredentialComponentsPage();
        expect(credentialComponentsPage.getTitle()).toMatch(/rabbitmqMonitoringApp.credential.home.title/);
    });

    it('should load create Credential page', () => {
        credentialComponentsPage.clickOnCreateButton();
        credentialUpdatePage = new CredentialUpdatePage();
        expect(credentialUpdatePage.getPageTitle()).toMatch(/rabbitmqMonitoringApp.credential.home.createOrEditLabel/);
        credentialUpdatePage.cancel();
    });

    it('should create and save Credentials', () => {
        credentialComponentsPage.clickOnCreateButton();
        credentialUpdatePage.setUsernameInput('username');
        expect(credentialUpdatePage.getUsernameInput()).toMatch('username');
        credentialUpdatePage.setPasswordInput('password');
        expect(credentialUpdatePage.getPasswordInput()).toMatch('password');
        credentialUpdatePage.hostSelectLastOption();
        credentialUpdatePage.save();
        expect(credentialUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
