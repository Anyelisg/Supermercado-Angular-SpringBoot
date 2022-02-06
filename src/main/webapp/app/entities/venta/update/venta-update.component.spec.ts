import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { VentaService } from '../service/venta.service';
import { IVenta, Venta } from '../venta.model';
import { ICliente } from 'app/entities/cliente/cliente.model';
import { ClienteService } from 'app/entities/cliente/service/cliente.service';
import { IEmpleado } from 'app/entities/empleado/empleado.model';
import { EmpleadoService } from 'app/entities/empleado/service/empleado.service';
import { ICaja } from 'app/entities/caja/caja.model';
import { CajaService } from 'app/entities/caja/service/caja.service';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';

import { VentaUpdateComponent } from './venta-update.component';

describe('Venta Management Update Component', () => {
  let comp: VentaUpdateComponent;
  let fixture: ComponentFixture<VentaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ventaService: VentaService;
  let clienteService: ClienteService;
  let empleadoService: EmpleadoService;
  let cajaService: CajaService;
  let empresaService: EmpresaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [VentaUpdateComponent],
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
      .overrideTemplate(VentaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VentaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ventaService = TestBed.inject(VentaService);
    clienteService = TestBed.inject(ClienteService);
    empleadoService = TestBed.inject(EmpleadoService);
    cajaService = TestBed.inject(CajaService);
    empresaService = TestBed.inject(EmpresaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Cliente query and add missing value', () => {
      const venta: IVenta = { id: 456 };
      const cliente: ICliente = { id: 97527 };
      venta.cliente = cliente;

      const clienteCollection: ICliente[] = [{ id: 58807 }];
      jest.spyOn(clienteService, 'query').mockReturnValue(of(new HttpResponse({ body: clienteCollection })));
      const additionalClientes = [cliente];
      const expectedCollection: ICliente[] = [...additionalClientes, ...clienteCollection];
      jest.spyOn(clienteService, 'addClienteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ venta });
      comp.ngOnInit();

      expect(clienteService.query).toHaveBeenCalled();
      expect(clienteService.addClienteToCollectionIfMissing).toHaveBeenCalledWith(clienteCollection, ...additionalClientes);
      expect(comp.clientesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Empleado query and add missing value', () => {
      const venta: IVenta = { id: 456 };
      const empleado: IEmpleado = { id: 11859 };
      venta.empleado = empleado;

      const empleadoCollection: IEmpleado[] = [{ id: 10221 }];
      jest.spyOn(empleadoService, 'query').mockReturnValue(of(new HttpResponse({ body: empleadoCollection })));
      const additionalEmpleados = [empleado];
      const expectedCollection: IEmpleado[] = [...additionalEmpleados, ...empleadoCollection];
      jest.spyOn(empleadoService, 'addEmpleadoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ venta });
      comp.ngOnInit();

      expect(empleadoService.query).toHaveBeenCalled();
      expect(empleadoService.addEmpleadoToCollectionIfMissing).toHaveBeenCalledWith(empleadoCollection, ...additionalEmpleados);
      expect(comp.empleadosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Caja query and add missing value', () => {
      const venta: IVenta = { id: 456 };
      const caja: ICaja = { id: 32535 };
      venta.caja = caja;

      const cajaCollection: ICaja[] = [{ id: 37632 }];
      jest.spyOn(cajaService, 'query').mockReturnValue(of(new HttpResponse({ body: cajaCollection })));
      const additionalCajas = [caja];
      const expectedCollection: ICaja[] = [...additionalCajas, ...cajaCollection];
      jest.spyOn(cajaService, 'addCajaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ venta });
      comp.ngOnInit();

      expect(cajaService.query).toHaveBeenCalled();
      expect(cajaService.addCajaToCollectionIfMissing).toHaveBeenCalledWith(cajaCollection, ...additionalCajas);
      expect(comp.cajasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Empresa query and add missing value', () => {
      const venta: IVenta = { id: 456 };
      const empresa: IEmpresa = { id: 54097 };
      venta.empresa = empresa;

      const empresaCollection: IEmpresa[] = [{ id: 545 }];
      jest.spyOn(empresaService, 'query').mockReturnValue(of(new HttpResponse({ body: empresaCollection })));
      const additionalEmpresas = [empresa];
      const expectedCollection: IEmpresa[] = [...additionalEmpresas, ...empresaCollection];
      jest.spyOn(empresaService, 'addEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ venta });
      comp.ngOnInit();

      expect(empresaService.query).toHaveBeenCalled();
      expect(empresaService.addEmpresaToCollectionIfMissing).toHaveBeenCalledWith(empresaCollection, ...additionalEmpresas);
      expect(comp.empresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const venta: IVenta = { id: 456 };
      const cliente: ICliente = { id: 11625 };
      venta.cliente = cliente;
      const empleado: IEmpleado = { id: 17057 };
      venta.empleado = empleado;
      const caja: ICaja = { id: 12875 };
      venta.caja = caja;
      const empresa: IEmpresa = { id: 29758 };
      venta.empresa = empresa;

      activatedRoute.data = of({ venta });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(venta));
      expect(comp.clientesSharedCollection).toContain(cliente);
      expect(comp.empleadosSharedCollection).toContain(empleado);
      expect(comp.cajasSharedCollection).toContain(caja);
      expect(comp.empresasSharedCollection).toContain(empresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Venta>>();
      const venta = { id: 123 };
      jest.spyOn(ventaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ venta });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: venta }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(ventaService.update).toHaveBeenCalledWith(venta);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Venta>>();
      const venta = new Venta();
      jest.spyOn(ventaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ venta });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: venta }));
      saveSubject.complete();

      // THEN
      expect(ventaService.create).toHaveBeenCalledWith(venta);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Venta>>();
      const venta = { id: 123 };
      jest.spyOn(ventaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ venta });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ventaService.update).toHaveBeenCalledWith(venta);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackClienteById', () => {
      it('Should return tracked Cliente primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackClienteById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackEmpleadoById', () => {
      it('Should return tracked Empleado primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEmpleadoById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackCajaById', () => {
      it('Should return tracked Caja primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCajaById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackEmpresaById', () => {
      it('Should return tracked Empresa primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEmpresaById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
