import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICredential } from 'app/shared/model/credential.model';
import { CredentialService } from './credential.service';
import { IHost } from 'app/shared/model/host.model';
import { HostService } from 'app/entities/host';

@Component({
    selector: 'jhi-credential-update',
    templateUrl: './credential-update.component.html'
})
export class CredentialUpdateComponent implements OnInit {
    private _credential: ICredential;
    isSaving: boolean;

    hosts: IHost[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private credentialService: CredentialService,
        private hostService: HostService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ credential }) => {
            this.credential = credential;
        });
        this.hostService.query({ filter: 'credential-is-null' }).subscribe(
            (res: HttpResponse<IHost[]>) => {
                if (!this.credential.hostId) {
                    this.hosts = res.body;
                } else {
                    this.hostService.find(this.credential.hostId).subscribe(
                        (subRes: HttpResponse<IHost>) => {
                            this.hosts = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.credential.id !== undefined) {
            this.subscribeToSaveResponse(this.credentialService.update(this.credential));
        } else {
            this.subscribeToSaveResponse(this.credentialService.create(this.credential));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICredential>>) {
        result.subscribe((res: HttpResponse<ICredential>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackHostById(index: number, item: IHost) {
        return item.id;
    }
    get credential() {
        return this._credential;
    }

    set credential(credential: ICredential) {
        this._credential = credential;
    }
}
