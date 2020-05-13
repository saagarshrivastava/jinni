import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { DeliveryTypeUpdateComponent } from 'app/entities/delivery-type/delivery-type-update.component';
import { DeliveryTypeService } from 'app/entities/delivery-type/delivery-type.service';
import { DeliveryType } from 'app/shared/model/delivery-type.model';

describe('Component Tests', () => {
  describe('DeliveryType Management Update Component', () => {
    let comp: DeliveryTypeUpdateComponent;
    let fixture: ComponentFixture<DeliveryTypeUpdateComponent>;
    let service: DeliveryTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [DeliveryTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DeliveryTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DeliveryTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DeliveryTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DeliveryType(123);
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
        const entity = new DeliveryType();
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
