import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IQueue } from 'app/shared/model/queue.model';
import { QueueService } from './queue.service';
import { IHost } from 'app/shared/model/host.model';
import { HostService } from 'app/entities/host';

@Component({
    selector: 'jhi-queue-update',
    templateUrl: './queue-update.component.html'
})
export class QueueUpdateComponent implements OnInit {
    private _queue: IQueue;
    isSaving: boolean;

    hosts: IHost[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private queueService: QueueService,
        private hostService: HostService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ queue }) => {
            this.queue = queue;
        });
        this.hostService.query().subscribe(
            (res: HttpResponse<IHost[]>) => {
                this.hosts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.queue.id !== undefined) {
            this.subscribeToSaveResponse(this.queueService.update(this.queue));
        } else {
            this.subscribeToSaveResponse(this.queueService.create(this.queue));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IQueue>>) {
        result.subscribe((res: HttpResponse<IQueue>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get queue() {
        return this._queue;
    }

    set queue(queue: IQueue) {
        this._queue = queue;
    }
}
