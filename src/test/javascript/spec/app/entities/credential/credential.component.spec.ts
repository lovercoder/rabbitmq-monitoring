/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RabbitmqMonitoringTestModule } from '../../../test.module';
import { CredentialComponent } from 'app/entities/credential/credential.component';
import { CredentialService } from 'app/entities/credential/credential.service';
import { Credential } from 'app/shared/model/credential.model';

describe('Component Tests', () => {
    describe('Credential Management Component', () => {
        let comp: CredentialComponent;
        let fixture: ComponentFixture<CredentialComponent>;
        let service: CredentialService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RabbitmqMonitoringTestModule],
                declarations: [CredentialComponent],
                providers: []
            })
                .overrideTemplate(CredentialComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CredentialComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CredentialService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Credential(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.credentials[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
