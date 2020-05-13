import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { SupportInstanceUpdateComponent } from 'app/entities/support-instance/support-instance-update.component';
import { SupportInstanceService } from 'app/entities/support-instance/support-instance.service';
import { SupportInstance } from 'app/shared/model/support-instance.model';

describe('Component Tests', () => {
  describe('SupportInstance Management Update Component', () => {
    let comp: SupportInstanceUpdateComponent;
    let fixture: ComponentFixture<SupportInstanceUpdateComponent>;
    let service: SupportInstanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [SupportInstanceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SupportInstanceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SupportInstanceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SupportInstanceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SupportInstance(123);
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
        const entity = new SupportInstance();
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
