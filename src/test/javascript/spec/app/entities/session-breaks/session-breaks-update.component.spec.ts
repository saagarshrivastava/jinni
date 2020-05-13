import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { SessionBreaksUpdateComponent } from 'app/entities/session-breaks/session-breaks-update.component';
import { SessionBreaksService } from 'app/entities/session-breaks/session-breaks.service';
import { SessionBreaks } from 'app/shared/model/session-breaks.model';

describe('Component Tests', () => {
  describe('SessionBreaks Management Update Component', () => {
    let comp: SessionBreaksUpdateComponent;
    let fixture: ComponentFixture<SessionBreaksUpdateComponent>;
    let service: SessionBreaksService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [SessionBreaksUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SessionBreaksUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SessionBreaksUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SessionBreaksService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SessionBreaks(123);
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
        const entity = new SessionBreaks();
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
