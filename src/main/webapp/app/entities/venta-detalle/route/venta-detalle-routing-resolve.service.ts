import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVentaDetalle, VentaDetalle } from '../venta-detalle.model';
import { VentaDetalleService } from '../service/venta-detalle.service';

@Injectable({ providedIn: 'root' })
export class VentaDetalleRoutingResolveService implements Resolve<IVentaDetalle> {
  constructor(protected service: VentaDetalleService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVentaDetalle> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((ventaDetalle: HttpResponse<VentaDetalle>) => {
          if (ventaDetalle.body) {
            return of(ventaDetalle.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new VentaDetalle());
  }
}
