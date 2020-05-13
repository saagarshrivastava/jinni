import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { DeliveryStatusUpdateComponent } from 'app/entities/delivery-status/delivery-status-update.component';
import { DeliveryStatusService } from 'app/entities/delivery-status/delivery-status.service';
import { DeliveryStatus } from 'app/shared/model/delivery-status.model';

describe('Component Tests', () => {
  describe('DeliveryStatus Management Update Component', () => {
    let comp: DeliveryStatusUpdateComponent;
    let fixture: ComponentFixture<DeliveryStatusUpdateComponent>;
    let service: DeliveryStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [DeliveryStatusUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DeliveryStatusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DeliveryStatusUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DeliveryStatusService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DeliveryStatus(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new DeliveryStatus();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
