import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQueue } from 'app/shared/model/queue.model';

@Component({
    selector: 'jhi-queue-detail',
    templateUrl: './queue-detail.component.html'
})
export class QueueDetailComponent implements OnInit {
    queue: IQueue;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ queue }) => {
            this.queue = queue;
        });
    }

    previousState() {
        window.history.back();
    }
}
