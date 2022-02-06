import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IVentaDetalle, VentaDetalle } from '../venta-detalle.model';
import { VentaDetalleService } from '../service/venta-detalle.service';
import { IVenta } from 'app/entities/venta/venta.model';
import { VentaService } from 'app/entities/venta/service/venta.service';
import { ProductoService } from 'app/entities/producto/service/producto.service';
import { IProducto } from 'app/entities/producto/producto.model';

@Component({
  selector: 'jhi-venta-detalle-update',
  templateUrl: './venta-detalle-update.component.html',
})
export class VentaDetalleUpdateComponent implements OnInit {
  isSaving = false;
  codigoSearch = '';
  cantidadProducto = 1;
  numeroFacturas = 0;
  ventaDetalleSharedCollection?: IVentaDetalle[];

  ventasSharedCollection: IVenta[] = [];
  productosSharedCollection: IProducto[] = [];

  editForm = this.fb.group({
    id: [],
    numeroFactura: [{ value: '' }, [Validators.required, Validators.max(99999999)]],
    nombreProducto: [{ value: '' }],
    precioProducto: [{ value: '' }],
    cantidadProducto: [],
    totalProducto: [],
    venta: [],
  });

  constructor(
    protected ventaDetalleService: VentaDetalleService,
    protected ventaService: VentaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder,
    protected productoService: ProductoService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ventaDetalle }) => {
      this.updateForm(ventaDetalle);

      this.loadRelationshipsOptions();
    });
  }

  searchingByCodigo(): void {
    if (this.codigoSearch !== '') {
      this.productoService.searchByCodigo(this.codigoSearch).subscribe({
        next: (res: HttpResponse<IProducto[]>) => {
          this.productosSharedCollection = res.body ?? [];
        },
      });
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ventaDetalle = this.createFromForm();
    if (ventaDetalle.id !== undefined) {
      this.ventaDetalleService.createByVenta(ventaDetalle);
      // if (this.numeroFacturas !== 0) {
      //   this.ventaDetalleService.byNumeroFactura(this.numeroFacturas).subscribe({
      //     next: (res: HttpResponse<IVentaDetalle[]>) => {
      //       this.ventaDetalleSharedCollection = res.body ?? [];
      //     },
      //   });
      // }

      // this.ventaDetalleService.byNumeroFactura(this.numeroFacturas)
      // .pipe(map((res: HttpResponse<IVentaDetalle[]>) => res.body ?? []))
      // .pipe(map((ventaDetalles: IVentaDetalle[]) => this.ventaDetalleService.addVentaDetalleToCollectionIfMissing(ventaDetalles, this.editForm.get('venta')!.value)))
      // .subscribe((ventaDetalles: IVentaDetalle[]) => (this.ventaDetalleSharedCollection = ventaDetalles));
    }
  }

  trackById(index: number, item: IVentaDetalle): number {
    return item.id!;
  }

  trackVentaById(index: number, item: IVenta): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVentaDetalle>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    // Api for inheritance.
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(ventaDetalle: IVentaDetalle): void {
    this.editForm.patchValue({
      id: ventaDetalle.id,
      numeroFactura: ventaDetalle.numeroFactura,
      nombreProducto: ventaDetalle.nombreProducto,
      precioProducto: ventaDetalle.precioProducto,
      cantidadProducto: ventaDetalle.cantidadProducto,
      totalProducto: ventaDetalle.totalProducto,
      venta: ventaDetalle.venta,
    });

    this.ventasSharedCollection = this.ventaService.addVentaToCollectionIfMissing(this.ventasSharedCollection, ventaDetalle.venta);
  }

  protected loadRelationshipsOptions(): void {
    this.ventaService
      .query()
      .pipe(map((res: HttpResponse<IVenta[]>) => res.body ?? []))
      .pipe(map((ventas: IVenta[]) => this.ventaService.addVentaToCollectionIfMissing(ventas, this.editForm.get('venta')!.value)))
      .subscribe((ventas: IVenta[]) => (this.ventasSharedCollection = ventas));
  }

  protected createFromForm(): IVentaDetalle {
    return {
      ...new VentaDetalle(),
      id: this.editForm.get(['id'])!.value,
      numeroFactura: this.editForm.get(['numeroFactura'])!.value,
      nombreProducto: this.editForm.get(['nombreProducto'])!.value,
      precioProducto: this.editForm.get(['precioProducto'])!.value,
      cantidadProducto: this.editForm.get(['cantidadProducto'])!.value,
      totalProducto: this.editForm.get(['totalProducto'])!.value,
      venta: this.editForm.get(['venta'])!.value,
    };
  }
}
