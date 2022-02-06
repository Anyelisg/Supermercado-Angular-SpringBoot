import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VentaDetalleDetailComponent } from './venta-detalle-detail.component';

describe('VentaDetalle Management Detail Component', () => {
  let comp: VentaDetalleDetailComponent;
  let fixture: ComponentFixture<VentaDetalleDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VentaDetalleDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ ventaDetalle: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(VentaDetalleDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(VentaDetalleDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load ventaDetalle on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.ventaDetalle).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
