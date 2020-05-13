import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { SubcategoryInstanceUpdateComponent } from 'app/entities/subcategory-instance/subcategory-instance-update.component';
import { SubcategoryInstanceService } from 'app/entities/subcategory-instance/subcategory-instance.service';
import { SubcategoryInstance } from 'app/shared/model/subcategory-instance.model';

describe('Component Tests', () => {
  describe('SubcategoryInstance Management Update Component', () => {
    let comp: SubcategoryInstanceUpdateComponent;
    let fixture: ComponentFixture<SubcategoryInstanceUpdateComponent>;
    let service: SubcategoryInstanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [SubcategoryInstanceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SubcategoryInstanceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SubcategoryInstanceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubcategoryInstanceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SubcategoryInstance(123);
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
        const entity = new SubcategoryInstance();
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
