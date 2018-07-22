import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICredential } from 'app/shared/model/credential.model';

@Component({
    selector: 'jhi-credential-detail',
    templateUrl: './credential-detail.component.html'
})
export class CredentialDetailComponent implements OnInit {
    credential: ICredential;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ credential }) => {
            this.credential = credential;
        });
    }

    previousState() {
        window.history.back();
    }
}
