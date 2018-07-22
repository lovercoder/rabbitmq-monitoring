import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHost } from 'app/shared/model/host.model';
import { HostService } from './host.service';

@Component({
    selector: 'jhi-host-delete-dialog',
    templateUrl: './host-delete-dialog.component.html'
})
export class HostDeleteDialogComponent {
    host: IHost;

    constructor(private hostService: HostService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.hostService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'hostListModification',
                content: 'Deleted an host'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-host-delete-popup',
    template: ''
})
export class HostDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ host }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(HostDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.host = host;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
