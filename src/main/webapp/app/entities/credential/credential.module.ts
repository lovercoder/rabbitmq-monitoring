import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RabbitmqMonitoringSharedModule } from 'app/shared';
import {
    CredentialComponent,
    CredentialDetailComponent,
    CredentialUpdateComponent,
    CredentialDeletePopupComponent,
    CredentialDeleteDialogComponent,
    credentialRoute,
    credentialPopupRoute
} from './';

const ENTITY_STATES = [...credentialRoute, ...credentialPopupRoute];

@NgModule({
    imports: [RabbitmqMonitoringSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CredentialComponent,
        CredentialDetailComponent,
        CredentialUpdateComponent,
        CredentialDeleteDialogComponent,
        CredentialDeletePopupComponent
    ],
    entryComponents: [CredentialComponent, CredentialUpdateComponent, CredentialDeleteDialogComponent, CredentialDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RabbitmqMonitoringCredentialModule {}
