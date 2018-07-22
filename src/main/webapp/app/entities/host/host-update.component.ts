import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IHost } from 'app/shared/model/host.model';
import { HostService } from './host.service';

@Component({
    selector: 'jhi-host-update',
    templateUrl: './host-update.component.html'
})
export class HostUpdateComponent implements OnInit {
    private _host: IHost;
    isSaving: boolean;

    constructor(private hostService: HostService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ host }) => {
            this.host = host;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.host.id !== undefined) {
            this.subscribeToSaveResponse(this.hostService.update(this.host));
        } else {
            this.subscribeToSaveResponse(this.hostService.create(this.host));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IHost>>) {
        result.subscribe((res: HttpResponse<IHost>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get host() {
        return this._host;
    }

    set host(host: IHost) {
        this._host = host;
    }
}
