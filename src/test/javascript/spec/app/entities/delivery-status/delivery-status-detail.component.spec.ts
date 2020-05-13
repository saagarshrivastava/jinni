import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { DeliveryStatusDetailComponent } from 'app/entities/delivery-status/delivery-status-detail.component';
import { DeliveryStatus } from 'app/shared/model/delivery-status.model';

describe('Component Tests', () => {
  describe('DeliveryStatus Management Detail Component', () => {
    let comp: DeliveryStatusDetailComponent;
    let fixture: ComponentFixture<DeliveryStatusDetailComponent>;
    const route = ({ data: of({ deliveryStatus: new DeliveryStatus(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [DeliveryStatusDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DeliveryStatusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DeliveryStatusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load deliveryStatus on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.deliveryStatus).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
