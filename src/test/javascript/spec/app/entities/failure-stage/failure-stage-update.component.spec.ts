import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { FailureStageUpdateComponent } from 'app/entities/failure-stage/failure-stage-update.component';
import { FailureStageService } from 'app/entities/failure-stage/failure-stage.service';
import { FailureStage } from 'app/shared/model/failure-stage.model';

describe('Component Tests', () => {
  describe('FailureStage Management Update Component', () => {
    let comp: FailureStageUpdateComponent;
    let fixture: ComponentFixture<FailureStageUpdateComponent>;
    let service: FailureStageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [FailureStageUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FailureStageUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FailureStageUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FailureStageService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FailureStage(123);
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
        const entity = new FailureStage();
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
