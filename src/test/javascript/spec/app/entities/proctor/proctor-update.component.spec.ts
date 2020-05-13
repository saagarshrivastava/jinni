import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { ProctorUpdateComponent } from 'app/entities/proctor/proctor-update.component';
import { ProctorService } from 'app/entities/proctor/proctor.service';
import { Proctor } from 'app/shared/model/proctor.model';

describe('Component Tests', () => {
  describe('Proctor Management Update Component', () => {
    let comp: ProctorUpdateComponent;
    let fixture: ComponentFixture<ProctorUpdateComponent>;
    let service: ProctorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [ProctorUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProctorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProctorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProctorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Proctor(123);
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
        const entity = new Proctor();
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
