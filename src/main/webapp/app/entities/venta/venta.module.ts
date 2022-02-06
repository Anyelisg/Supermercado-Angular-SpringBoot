import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { VentaComponent } from './list/venta.component';
import { VentaDetailComponent } from './detail/venta-detail.component';
import { VentaUpdateComponent } from './update/venta-update.component';
import { VentaDeleteDialogComponent } from './delete/venta-delete-dialog.component';
import { VentaRoutingModule } from './route/venta-routing.module';
import { VentaDetalleUpdateComponent } from '../venta-detalle/update/venta-detalle-update.component';

@NgModule({
  imports: [SharedModule, VentaRoutingModule],
  declarations: [VentaComponent, VentaDetailComponent, VentaUpdateComponent, VentaDeleteDialogComponent, VentaDetalleUpdateComponent],
  entryComponents: [VentaDeleteDialogComponent],
})
export class VentaModule {}
