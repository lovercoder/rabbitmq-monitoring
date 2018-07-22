/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RabbitmqMonitoringTestModule } from '../../../test.module';
import { CredentialDeleteDialogComponent } from 'app/entities/credential/credential-delete-dialog.component';
import { CredentialService } from 'app/entities/credential/credential.service';

describe('Component Tests', () => {
    describe('Credential Management Delete Component', () => {
        let comp: CredentialDeleteDialogComponent;
        let fixture: ComponentFixture<CredentialDeleteDialogComponent>;
        let service: CredentialService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RabbitmqMonitoringTestModule],
                declarations: [CredentialDeleteDialogComponent]
            })
                .overrideTemplate(CredentialDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CredentialDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CredentialService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
