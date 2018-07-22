/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RabbitmqMonitoringTestModule } from '../../../test.module';
import { CredentialUpdateComponent } from 'app/entities/credential/credential-update.component';
import { CredentialService } from 'app/entities/credential/credential.service';
import { Credential } from 'app/shared/model/credential.model';

describe('Component Tests', () => {
    describe('Credential Management Update Component', () => {
        let comp: CredentialUpdateComponent;
        let fixture: ComponentFixture<CredentialUpdateComponent>;
        let service: CredentialService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RabbitmqMonitoringTestModule],
                declarations: [CredentialUpdateComponent]
            })
                .overrideTemplate(CredentialUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CredentialUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CredentialService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Credential(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.credential = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Credential();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.credential = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
