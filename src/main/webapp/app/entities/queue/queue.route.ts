import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Queue } from 'app/shared/model/queue.model';
import { QueueService } from './queue.service';
import { QueueComponent } from './queue.component';
import { QueueDetailComponent } from './queue-detail.component';
import { QueueUpdateComponent } from './queue-update.component';
import { QueueDeletePopupComponent } from './queue-delete-dialog.component';
import { IQueue } from 'app/shared/model/queue.model';

@Injectable({ providedIn: 'root' })
export class QueueResolve implements Resolve<IQueue> {
    constructor(private service: QueueService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((queue: HttpResponse<Queue>) => queue.body));
        }
        return of(new Queue());
    }
}

export const queueRoute: Routes = [
    {
        path: 'queue',
        component: QueueComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rabbitmqMonitoringApp.queue.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'queue/:id/view',
        component: QueueDetailComponent,
        resolve: {
            queue: QueueResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rabbitmqMonitoringApp.queue.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'queue/new',
        component: QueueUpdateComponent,
        resolve: {
            queue: QueueResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rabbitmqMonitoringApp.queue.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'queue/:id/edit',
        component: QueueUpdateComponent,
        resolve: {
            queue: QueueResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rabbitmqMonitoringApp.queue.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const queuePopupRoute: Routes = [
    {
        path: 'queue/:id/delete',
        component: QueueDeletePopupComponent,
        resolve: {
            queue: QueueResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rabbitmqMonitoringApp.queue.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
