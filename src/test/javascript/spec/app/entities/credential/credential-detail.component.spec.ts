/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RabbitmqMonitoringTestModule } from '../../../test.module';
import { CredentialDetailComponent } from 'app/entities/credential/credential-detail.component';
import { Credential } from 'app/shared/model/credential.model';

describe('Component Tests', () => {
    describe('Credential Management Detail Component', () => {
        let comp: CredentialDetailComponent;
        let fixture: ComponentFixture<CredentialDetailComponent>;
        const route = ({ data: of({ credential: new Credential(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RabbitmqMonitoringTestModule],
                declarations: [CredentialDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CredentialDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CredentialDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.credential).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
