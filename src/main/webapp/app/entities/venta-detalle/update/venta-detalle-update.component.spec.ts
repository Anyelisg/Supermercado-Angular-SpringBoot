import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { VentaDetalleService } from '../service/venta-detalle.service';
import { IVentaDetalle, VentaDetalle } from '../venta-detalle.model';
import { IVenta } from 'app/entities/venta/venta.model';
import { VentaService } from 'app/entities/venta/service/venta.service';

import { VentaDetalleUpdateComponent } from './venta-detalle-update.component';

describe('VentaDetalle Management Update Component', () => {
  let comp: VentaDetalleUpdateComponent;
  let fixture: ComponentFixture<VentaDetalleUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ventaDetalleService: VentaDetalleService;
  let ventaService: VentaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [VentaDetalleUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(VentaDetalleUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VentaDetalleUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ventaDetalleService = TestBed.inject(VentaDetalleService);
    ventaService = TestBed.inject(VentaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Venta query and add missing value', () => {
      const ventaDetalle: IVentaDetalle = { id: 456 };
      const venta: IVenta = { id: 29132 };
      ventaDetalle.venta = venta;

      const ventaCollection: IVenta[] = [{ id: 73184 }];
      jest.spyOn(ventaService, 'query').mockReturnValue(of(new HttpResponse({ body: ventaCollection })));
      const additionalVentas = [venta];
      const expectedCollection: IVenta[] = [...additionalVentas, ...ventaCollection];
      jest.spyOn(ventaService, 'addVentaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ ventaDetalle });
      comp.ngOnInit();

      expect(ventaService.query).toHaveBeenCalled();
      expect(ventaService.addVentaToCollectionIfMissing).toHaveBeenCalledWith(ventaCollection, ...additionalVentas);
      expect(comp.ventasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const ventaDetalle: IVentaDetalle = { id: 456 };
      const venta: IVenta = { id: 91927 };
      ventaDetalle.venta = venta;

      activatedRoute.data = of({ ventaDetalle });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(ventaDetalle));
      expect(comp.ventasSharedCollection).toContain(venta);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<VentaDetalle>>();
      const ventaDetalle = { id: 123 };
      jest.spyOn(ventaDetalleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ventaDetalle });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ventaDetalle }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(ventaDetalleService.update).toHaveBeenCalledWith(ventaDetalle);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<VentaDetalle>>();
      const ventaDetalle = new VentaDetalle();
      jest.spyOn(ventaDetalleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ventaDetalle });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ventaDetalle }));
      saveSubject.complete();

      // THEN
      expect(ventaDetalleService.create).toHaveBeenCalledWith(ventaDetalle);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<VentaDetalle>>();
      const ventaDetalle = { id: 123 };
      jest.spyOn(ventaDetalleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ventaDetalle });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ventaDetalleService.update).toHaveBeenCalledWith(ventaDetalle);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackVentaById', () => {
      it('Should return tracked Venta primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackVentaById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
