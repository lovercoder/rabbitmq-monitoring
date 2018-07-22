import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { QueueComponentsPage, QueueUpdatePage } from './queue.page-object';

describe('Queue e2e test', () => {
    let navBarPage: NavBarPage;
    let queueUpdatePage: QueueUpdatePage;
    let queueComponentsPage: QueueComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Queues', () => {
        navBarPage.goToEntity('queue');
        queueComponentsPage = new QueueComponentsPage();
        expect(queueComponentsPage.getTitle()).toMatch(/rabbitmqMonitoringApp.queue.home.title/);
    });

    it('should load create Queue page', () => {
        queueComponentsPage.clickOnCreateButton();
        queueUpdatePage = new QueueUpdatePage();
        expect(queueUpdatePage.getPageTitle()).toMatch(/rabbitmqMonitoringApp.queue.home.createOrEditLabel/);
        queueUpdatePage.cancel();
    });

    it('should create and save Queues', () => {
        queueComponentsPage.clickOnCreateButton();
        queueUpdatePage.setNameInput('name');
        expect(queueUpdatePage.getNameInput()).toMatch('name');
        queueUpdatePage.setCountInput('5');
        expect(queueUpdatePage.getCountInput()).toMatch('5');
        queueUpdatePage.setDescriptionInput('description');
        expect(queueUpdatePage.getDescriptionInput()).toMatch('description');
        queueUpdatePage.hostSelectLastOption();
        queueUpdatePage.save();
        expect(queueUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
