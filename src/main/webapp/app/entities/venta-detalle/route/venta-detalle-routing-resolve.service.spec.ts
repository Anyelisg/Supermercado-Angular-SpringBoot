import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IVentaDetalle, VentaDetalle } from '../venta-detalle.model';
import { VentaDetalleService } from '../service/venta-detalle.service';

import { VentaDetalleRoutingResolveService } from './venta-detalle-routing-resolve.service';

describe('VentaDetalle routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: VentaDetalleRoutingResolveService;
  let service: VentaDetalleService;
  let resultVentaDetalle: IVentaDetalle | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(VentaDetalleRoutingResolveService);
    service = TestBed.inject(VentaDetalleService);
    resultVentaDetalle = undefined;
  });

  describe('resolve', () => {
    it('should return IVentaDetalle returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultVentaDetalle = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultVentaDetalle).toEqual({ id: 123 });
    });

    it('should return new IVentaDetalle if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultVentaDetalle = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultVentaDetalle).toEqual(new VentaDetalle());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as VentaDetalle })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultVentaDetalle = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultVentaDetalle).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
