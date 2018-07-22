import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { HostComponentsPage, HostUpdatePage } from './host.page-object';

describe('Host e2e test', () => {
    let navBarPage: NavBarPage;
    let hostUpdatePage: HostUpdatePage;
    let hostComponentsPage: HostComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Hosts', () => {
        navBarPage.goToEntity('host');
        hostComponentsPage = new HostComponentsPage();
        expect(hostComponentsPage.getTitle()).toMatch(/rabbitmqMonitoringApp.host.home.title/);
    });

    it('should load create Host page', () => {
        hostComponentsPage.clickOnCreateButton();
        hostUpdatePage = new HostUpdatePage();
        expect(hostUpdatePage.getPageTitle()).toMatch(/rabbitmqMonitoringApp.host.home.createOrEditLabel/);
        hostUpdatePage.cancel();
    });

    it('should create and save Hosts', () => {
        hostComponentsPage.clickOnCreateButton();
        hostUpdatePage.setNameInput('name');
        expect(hostUpdatePage.getNameInput()).toMatch('name');
        hostUpdatePage.setDescriptionInput('description');
        expect(hostUpdatePage.getDescriptionInput()).toMatch('description');
        hostUpdatePage.setDnsInput('dns');
        expect(hostUpdatePage.getDnsInput()).toMatch('dns');
        hostUpdatePage.setPortInput('5');
        expect(hostUpdatePage.getPortInput()).toMatch('5');
        hostUpdatePage.stateSelectLastOption();
        hostUpdatePage.save();
        expect(hostUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
