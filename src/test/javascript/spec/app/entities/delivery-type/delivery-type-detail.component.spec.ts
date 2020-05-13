import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { DeliveryTypeDetailComponent } from 'app/entities/delivery-type/delivery-type-detail.component';
import { DeliveryType } from 'app/shared/model/delivery-type.model';

describe('Component Tests', () => {
  describe('DeliveryType Management Detail Component', () => {
    let comp: DeliveryTypeDetailComponent;
    let fixture: ComponentFixture<DeliveryTypeDetailComponent>;
    const route = ({ data: of({ deliveryType: new DeliveryType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [DeliveryTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DeliveryTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DeliveryTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load deliveryType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.deliveryType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
