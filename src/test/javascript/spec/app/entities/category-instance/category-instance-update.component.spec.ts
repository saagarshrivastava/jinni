import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { CategoryInstanceUpdateComponent } from 'app/entities/category-instance/category-instance-update.component';
import { CategoryInstanceService } from 'app/entities/category-instance/category-instance.service';
import { CategoryInstance } from 'app/shared/model/category-instance.model';

describe('Component Tests', () => {
  describe('CategoryInstance Management Update Component', () => {
    let comp: CategoryInstanceUpdateComponent;
    let fixture: ComponentFixture<CategoryInstanceUpdateComponent>;
    let service: CategoryInstanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [CategoryInstanceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CategoryInstanceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategoryInstanceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategoryInstanceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CategoryInstance(123);
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
        const entity = new CategoryInstance();
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
