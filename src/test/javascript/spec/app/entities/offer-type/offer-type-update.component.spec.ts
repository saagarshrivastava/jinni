import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { OfferTypeUpdateComponent } from 'app/entities/offer-type/offer-type-update.component';
import { OfferTypeService } from 'app/entities/offer-type/offer-type.service';
import { OfferType } from 'app/shared/model/offer-type.model';

describe('Component Tests', () => {
  describe('OfferType Management Update Component', () => {
    let comp: OfferTypeUpdateComponent;
    let fixture: ComponentFixture<OfferTypeUpdateComponent>;
    let service: OfferTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [OfferTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OfferTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OfferTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OfferTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OfferType(123);
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
        const entity = new OfferType();
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
