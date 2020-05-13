import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { SupportPersonUpdateComponent } from 'app/entities/support-person/support-person-update.component';
import { SupportPersonService } from 'app/entities/support-person/support-person.service';
import { SupportPerson } from 'app/shared/model/support-person.model';

describe('Component Tests', () => {
  describe('SupportPerson Management Update Component', () => {
    let comp: SupportPersonUpdateComponent;
    let fixture: ComponentFixture<SupportPersonUpdateComponent>;
    let service: SupportPersonService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [SupportPersonUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SupportPersonUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SupportPersonUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SupportPersonService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SupportPerson(123);
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
        const entity = new SupportPerson();
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
