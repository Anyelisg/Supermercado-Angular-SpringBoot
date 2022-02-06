import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IVentaDetalle, VentaDetalle } from '../venta-detalle.model';

import { VentaDetalleService } from './venta-detalle.service';

describe('VentaDetalle Service', () => {
  let service: VentaDetalleService;
  let httpMock: HttpTestingController;
  let elemDefault: IVentaDetalle;
  let expectedResult: IVentaDetalle | IVentaDetalle[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(VentaDetalleService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      numeroFactura: 0,
      nombreProducto: 'AAAAAAA',
      precioProducto: 0,
      cantidadProducto: 0,
      totalProducto: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a VentaDetalle', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new VentaDetalle()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a VentaDetalle', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          numeroFactura: 1,
          nombreProducto: 'BBBBBB',
          precioProducto: 1,
          cantidadProducto: 1,
          totalProducto: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a VentaDetalle', () => {
      const patchObject = Object.assign(
        {
          numeroFactura: 1,
          nombreProducto: 'BBBBBB',
          precioProducto: 1,
          cantidadProducto: 1,
        },
        new VentaDetalle()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of VentaDetalle', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          numeroFactura: 1,
          nombreProducto: 'BBBBBB',
          precioProducto: 1,
          cantidadProducto: 1,
          totalProducto: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a VentaDetalle', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addVentaDetalleToCollectionIfMissing', () => {
      it('should add a VentaDetalle to an empty array', () => {
        const ventaDetalle: IVentaDetalle = { id: 123 };
        expectedResult = service.addVentaDetalleToCollectionIfMissing([], ventaDetalle);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ventaDetalle);
      });

      it('should not add a VentaDetalle to an array that contains it', () => {
        const ventaDetalle: IVentaDetalle = { id: 123 };
        const ventaDetalleCollection: IVentaDetalle[] = [
          {
            ...ventaDetalle,
          },
          { id: 456 },
        ];
        expectedResult = service.addVentaDetalleToCollectionIfMissing(ventaDetalleCollection, ventaDetalle);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a VentaDetalle to an array that doesn't contain it", () => {
        const ventaDetalle: IVentaDetalle = { id: 123 };
        const ventaDetalleCollection: IVentaDetalle[] = [{ id: 456 }];
        expectedResult = service.addVentaDetalleToCollectionIfMissing(ventaDetalleCollection, ventaDetalle);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ventaDetalle);
      });

      it('should add only unique VentaDetalle to an array', () => {
        const ventaDetalleArray: IVentaDetalle[] = [{ id: 123 }, { id: 456 }, { id: 51361 }];
        const ventaDetalleCollection: IVentaDetalle[] = [{ id: 123 }];
        expectedResult = service.addVentaDetalleToCollectionIfMissing(ventaDetalleCollection, ...ventaDetalleArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const ventaDetalle: IVentaDetalle = { id: 123 };
        const ventaDetalle2: IVentaDetalle = { id: 456 };
        expectedResult = service.addVentaDetalleToCollectionIfMissing([], ventaDetalle, ventaDetalle2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ventaDetalle);
        expect(expectedResult).toContain(ventaDetalle2);
      });

      it('should accept null and undefined values', () => {
        const ventaDetalle: IVentaDetalle = { id: 123 };
        expectedResult = service.addVentaDetalleToCollectionIfMissing([], null, ventaDetalle, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ventaDetalle);
      });

      it('should return initial array if no VentaDetalle is added', () => {
        const ventaDetalleCollection: IVentaDetalle[] = [{ id: 123 }];
        expectedResult = service.addVentaDetalleToCollectionIfMissing(ventaDetalleCollection, undefined, null);
        expect(expectedResult).toEqual(ventaDetalleCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
