import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { MajorIncidentUpdateComponent } from 'app/entities/major-incident/major-incident-update.component';
import { MajorIncidentService } from 'app/entities/major-incident/major-incident.service';
import { MajorIncident } from 'app/shared/model/major-incident.model';

describe('Component Tests', () => {
  describe('MajorIncident Management Update Component', () => {
    let comp: MajorIncidentUpdateComponent;
    let fixture: ComponentFixture<MajorIncidentUpdateComponent>;
    let service: MajorIncidentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [MajorIncidentUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MajorIncidentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MajorIncidentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MajorIncidentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MajorIncident(123);
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
        const entity = new MajorIncident();
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
