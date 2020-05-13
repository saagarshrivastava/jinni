import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JinniTestModule } from '../../../test.module';
import { ProctoringInstanceComponent } from 'app/entities/proctoring-instance/proctoring-instance.component';
import { ProctoringInstanceService } from 'app/entities/proctoring-instance/proctoring-instance.service';
import { ProctoringInstance } from 'app/shared/model/proctoring-instance.model';

describe('Component Tests', () => {
  describe('ProctoringInstance Management Component', () => {
    let comp: ProctoringInstanceComponent;
    let fixture: ComponentFixture<ProctoringInstanceComponent>;
    let service: ProctoringInstanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [ProctoringInstanceComponent]
      })
        .overrideTemplate(ProctoringInstanceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProctoringInstanceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProctoringInstanceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProctoringInstance(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.proctoringInstances && comp.proctoringInstances[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
