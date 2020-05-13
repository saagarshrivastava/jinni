import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { ExamBackendUpdateComponent } from 'app/entities/exam-backend/exam-backend-update.component';
import { ExamBackendService } from 'app/entities/exam-backend/exam-backend.service';
import { ExamBackend } from 'app/shared/model/exam-backend.model';

describe('Component Tests', () => {
  describe('ExamBackend Management Update Component', () => {
    let comp: ExamBackendUpdateComponent;
    let fixture: ComponentFixture<ExamBackendUpdateComponent>;
    let service: ExamBackendService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [ExamBackendUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ExamBackendUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExamBackendUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExamBackendService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ExamBackend(123);
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
        const entity = new ExamBackend();
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
