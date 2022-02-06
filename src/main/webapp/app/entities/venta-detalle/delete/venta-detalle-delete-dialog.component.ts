import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IVentaDetalle } from '../venta-detalle.model';
import { VentaDetalleService } from '../service/venta-detalle.service';

@Component({
  templateUrl: './venta-detalle-delete-dialog.component.html',
})
export class VentaDetalleDeleteDialogComponent {
  ventaDetalle?: IVentaDetalle;

  constructor(protected ventaDetalleService: VentaDetalleService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ventaDetalleService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
