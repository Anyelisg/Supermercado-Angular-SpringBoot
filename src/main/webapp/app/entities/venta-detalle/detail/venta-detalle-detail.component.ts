import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVentaDetalle } from '../venta-detalle.model';

@Component({
  selector: 'jhi-venta-detalle-detail',
  templateUrl: './venta-detalle-detail.component.html',
})
export class VentaDetalleDetailComponent implements OnInit {
  ventaDetalle: IVentaDetalle | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ventaDetalle }) => {
      this.ventaDetalle = ventaDetalle;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
