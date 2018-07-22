import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { RabbitmqMonitoringHostModule } from './host/host.module';
import { RabbitmqMonitoringQueueModule } from './queue/queue.module';
import { RabbitmqMonitoringCredentialModule } from './credential/credential.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        RabbitmqMonitoringHostModule,
        RabbitmqMonitoringQueueModule,
        RabbitmqMonitoringCredentialModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RabbitmqMonitoringEntityModule {}
