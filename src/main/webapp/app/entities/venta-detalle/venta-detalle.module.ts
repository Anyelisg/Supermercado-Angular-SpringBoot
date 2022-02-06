import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { VentaDetalleDetailComponent } from './detail/venta-detalle-detail.component';
import { VentaDetalleDeleteDialogComponent } from './delete/venta-detalle-delete-dialog.component';
import { VentaDetalleRoutingModule } from './route/venta-detalle-routing.module';
import { VentaDetalleComponent } from './list/venta-detalle.component';

@NgModule({
  imports: [SharedModule, VentaDetalleRoutingModule],
  declarations: [VentaDetalleDetailComponent, VentaDetalleDeleteDialogComponent, VentaDetalleComponent],
  entryComponents: [VentaDetalleDeleteDialogComponent],
})
export class VentaDetalleModule {}
