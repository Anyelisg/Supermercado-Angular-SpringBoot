import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVentaDetalle, getVentaDetalleIdentifier } from '../venta-detalle.model';

export type EntityResponseType = HttpResponse<IVentaDetalle>;
export type EntityArrayResponseType = HttpResponse<IVentaDetalle[]>;

@Injectable({ providedIn: 'root' })
export class VentaDetalleService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/venta-detalles');
  protected resourceUrL = this.applicationConfigService.getEndpointFor('api/venta/new');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(ventaDetalle: IVentaDetalle): Observable<EntityResponseType> {
    return this.http.post<IVentaDetalle>(this.resourceUrl, ventaDetalle, { observe: 'response' });
  }

  createByVenta(ventaDetalle: IVentaDetalle): Observable<EntityResponseType> {
    return this.http.post<IVentaDetalle>(this.resourceUrl, ventaDetalle, { observe: 'response' });
  }

  update(ventaDetalle: IVentaDetalle): Observable<EntityResponseType> {
    return this.http.put<IVentaDetalle>(`${this.resourceUrl}/${getVentaDetalleIdentifier(ventaDetalle) as number}`, ventaDetalle, {
      observe: 'response',
    });
  }

  partialUpdate(ventaDetalle: IVentaDetalle): Observable<EntityResponseType> {
    return this.http.patch<IVentaDetalle>(`${this.resourceUrl}/${getVentaDetalleIdentifier(ventaDetalle) as number}`, ventaDetalle, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVentaDetalle>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVentaDetalle[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  byNumeroFactura(numeroFactura: number, pageable?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(pageable);
    return this.http.get<IVentaDetalle[]>(`${this.resourceUrl}/by-numerofactura/${numeroFactura}`, {
      params: options,
      observe: 'response',
    });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addVentaDetalleToCollectionIfMissing(
    ventaDetalleCollection: IVentaDetalle[],
    ...ventaDetallesToCheck: (IVentaDetalle | null | undefined)[]
  ): IVentaDetalle[] {
    const ventaDetalles: IVentaDetalle[] = ventaDetallesToCheck.filter(isPresent);
    if (ventaDetalles.length > 0) {
      const ventaDetalleCollectionIdentifiers = ventaDetalleCollection.map(
        ventaDetalleItem => getVentaDetalleIdentifier(ventaDetalleItem)!
      );
      const ventaDetallesToAdd = ventaDetalles.filter(ventaDetalleItem => {
        const ventaDetalleIdentifier = getVentaDetalleIdentifier(ventaDetalleItem);
        if (ventaDetalleIdentifier == null || ventaDetalleCollectionIdentifiers.includes(ventaDetalleIdentifier)) {
          return false;
        }
        ventaDetalleCollectionIdentifiers.push(ventaDetalleIdentifier);
        return true;
      });
      return [...ventaDetallesToAdd, ...ventaDetalleCollection];
    }
    return ventaDetalleCollection;
  }
}
