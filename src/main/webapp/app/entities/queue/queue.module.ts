import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RabbitmqMonitoringSharedModule } from 'app/shared';
import {
    QueueComponent,
    QueueDetailComponent,
    QueueUpdateComponent,
    QueueDeletePopupComponent,
    QueueDeleteDialogComponent,
    queueRoute,
    queuePopupRoute
} from './';

const ENTITY_STATES = [...queueRoute, ...queuePopupRoute];

@NgModule({
    imports: [RabbitmqMonitoringSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [QueueComponent, QueueDetailComponent, QueueUpdateComponent, QueueDeleteDialogComponent, QueueDeletePopupComponent],
    entryComponents: [QueueComponent, QueueUpdateComponent, QueueDeleteDialogComponent, QueueDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RabbitmqMonitoringQueueModule {}
