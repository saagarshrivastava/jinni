import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { MajorIncidentSourceUpdateComponent } from 'app/entities/major-incident-source/major-incident-source-update.component';
import { MajorIncidentSourceService } from 'app/entities/major-incident-source/major-incident-source.service';
import { MajorIncidentSource } from 'app/shared/model/major-incident-source.model';

describe('Component Tests', () => {
  describe('MajorIncidentSource Management Update Component', () => {
    let comp: MajorIncidentSourceUpdateComponent;
    let fixture: ComponentFixture<MajorIncidentSourceUpdateComponent>;
    let service: MajorIncidentSourceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [MajorIncidentSourceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MajorIncidentSourceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MajorIncidentSourceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MajorIncidentSourceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MajorIncidentSource(123);
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
        const entity = new MajorIncidentSource();
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
