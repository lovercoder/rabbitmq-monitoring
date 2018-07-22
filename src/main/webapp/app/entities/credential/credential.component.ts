import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICredential } from 'app/shared/model/credential.model';
import { Principal } from 'app/core';
import { CredentialService } from './credential.service';

@Component({
    selector: 'jhi-credential',
    templateUrl: './credential.component.html'
})
export class CredentialComponent implements OnInit, OnDestroy {
    credentials: ICredential[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private credentialService: CredentialService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.credentialService.query().subscribe(
            (res: HttpResponse<ICredential[]>) => {
                this.credentials = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCredentials();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICredential) {
        return item.id;
    }

    registerChangeInCredentials() {
        this.eventSubscriber = this.eventManager.subscribe('credentialListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
