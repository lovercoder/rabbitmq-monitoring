import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Credential } from 'app/shared/model/credential.model';
import { CredentialService } from './credential.service';
import { CredentialComponent } from './credential.component';
import { CredentialDetailComponent } from './credential-detail.component';
import { CredentialUpdateComponent } from './credential-update.component';
import { CredentialDeletePopupComponent } from './credential-delete-dialog.component';
import { ICredential } from 'app/shared/model/credential.model';

@Injectable({ providedIn: 'root' })
export class CredentialResolve implements Resolve<ICredential> {
    constructor(private service: CredentialService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((credential: HttpResponse<Credential>) => credential.body));
        }
        return of(new Credential());
    }
}

export const credentialRoute: Routes = [
    {
        path: 'credential',
        component: CredentialComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rabbitmqMonitoringApp.credential.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'credential/:id/view',
        component: CredentialDetailComponent,
        resolve: {
            credential: CredentialResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rabbitmqMonitoringApp.credential.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'credential/new',
        component: CredentialUpdateComponent,
        resolve: {
            credential: CredentialResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rabbitmqMonitoringApp.credential.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'credential/:id/edit',
        component: CredentialUpdateComponent,
        resolve: {
            credential: CredentialResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rabbitmqMonitoringApp.credential.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const credentialPopupRoute: Routes = [
    {
        path: 'credential/:id/delete',
        component: CredentialDeletePopupComponent,
        resolve: {
            credential: CredentialResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rabbitmqMonitoringApp.credential.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
