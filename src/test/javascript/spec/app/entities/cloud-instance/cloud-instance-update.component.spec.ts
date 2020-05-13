import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { CloudInstanceUpdateComponent } from 'app/entities/cloud-instance/cloud-instance-update.component';
import { CloudInstanceService } from 'app/entities/cloud-instance/cloud-instance.service';
import { CloudInstance } from 'app/shared/model/cloud-instance.model';

describe('Component Tests', () => {
  describe('CloudInstance Management Update Component', () => {
    let comp: CloudInstanceUpdateComponent;
    let fixture: ComponentFixture<CloudInstanceUpdateComponent>;
    let service: CloudInstanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [CloudInstanceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CloudInstanceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CloudInstanceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CloudInstanceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CloudInstance(123);
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
        const entity = new CloudInstance();
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
