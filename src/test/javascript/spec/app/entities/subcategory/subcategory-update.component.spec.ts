import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { SubcategoryUpdateComponent } from 'app/entities/subcategory/subcategory-update.component';
import { SubcategoryService } from 'app/entities/subcategory/subcategory.service';
import { Subcategory } from 'app/shared/model/subcategory.model';

describe('Component Tests', () => {
  describe('Subcategory Management Update Component', () => {
    let comp: SubcategoryUpdateComponent;
    let fixture: ComponentFixture<SubcategoryUpdateComponent>;
    let service: SubcategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [SubcategoryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SubcategoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SubcategoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubcategoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Subcategory(123);
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
        const entity = new Subcategory();
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
