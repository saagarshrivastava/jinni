import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { ProctoringInstanceUpdateComponent } from 'app/entities/proctoring-instance/proctoring-instance-update.component';
import { ProctoringInstanceService } from 'app/entities/proctoring-instance/proctoring-instance.service';
import { ProctoringInstance } from 'app/shared/model/proctoring-instance.model';

describe('Component Tests', () => {
  describe('ProctoringInstance Management Update Component', () => {
    let comp: ProctoringInstanceUpdateComponent;
    let fixture: ComponentFixture<ProctoringInstanceUpdateComponent>;
    let service: ProctoringInstanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [ProctoringInstanceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProctoringInstanceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProctoringInstanceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProctoringInstanceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProctoringInstance(123);
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
        const entity = new ProctoringInstance();
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
