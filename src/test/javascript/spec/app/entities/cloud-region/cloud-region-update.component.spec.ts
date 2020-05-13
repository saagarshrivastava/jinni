import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { CloudRegionUpdateComponent } from 'app/entities/cloud-region/cloud-region-update.component';
import { CloudRegionService } from 'app/entities/cloud-region/cloud-region.service';
import { CloudRegion } from 'app/shared/model/cloud-region.model';

describe('Component Tests', () => {
  describe('CloudRegion Management Update Component', () => {
    let comp: CloudRegionUpdateComponent;
    let fixture: ComponentFixture<CloudRegionUpdateComponent>;
    let service: CloudRegionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [CloudRegionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CloudRegionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CloudRegionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CloudRegionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CloudRegion(123);
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
        const entity = new CloudRegion();
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
