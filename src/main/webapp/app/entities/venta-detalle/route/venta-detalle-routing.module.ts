import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { VentaDetalleComponent } from '../list/venta-detalle.component';
import { VentaDetalleDetailComponent } from '../detail/venta-detalle-detail.component';
import { VentaDetalleUpdateComponent } from '../update/venta-detalle-update.component';
import { VentaDetalleRoutingResolveService } from './venta-detalle-routing-resolve.service';

const ventaDetalleRoute: Routes = [
  {
    path: '',
    component: VentaDetalleComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VentaDetalleDetailComponent,
    resolve: {
      ventaDetalle: VentaDetalleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VentaDetalleUpdateComponent,
    resolve: {
      ventaDetalle: VentaDetalleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VentaDetalleUpdateComponent,
    resolve: {
      ventaDetalle: VentaDetalleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ventaDetalleRoute)],
  exports: [RouterModule],
})
export class VentaDetalleRoutingModule {}
