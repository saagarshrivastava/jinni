import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JinniTestModule } from '../../../test.module';
import { DeliveryStatusComponent } from 'app/entities/delivery-status/delivery-status.component';
import { DeliveryStatusService } from 'app/entities/delivery-status/delivery-status.service';
import { DeliveryStatus } from 'app/shared/model/delivery-status.model';

describe('Component Tests', () => {
  describe('DeliveryStatus Management Component', () => {
    let comp: DeliveryStatusComponent;
    let fixture: ComponentFixture<DeliveryStatusComponent>;
    let service: DeliveryStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [DeliveryStatusComponent]
      })
        .overrideTemplate(DeliveryStatusComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DeliveryStatusComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DeliveryStatusService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DeliveryStatus(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.deliveryStatuses && comp.deliveryStatuses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
