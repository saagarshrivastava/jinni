import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JinniTestModule } from '../../../test.module';
import { ExamBackendComponent } from 'app/entities/exam-backend/exam-backend.component';
import { ExamBackendService } from 'app/entities/exam-backend/exam-backend.service';
import { ExamBackend } from 'app/shared/model/exam-backend.model';

describe('Component Tests', () => {
  describe('ExamBackend Management Component', () => {
    let comp: ExamBackendComponent;
    let fixture: ComponentFixture<ExamBackendComponent>;
    let service: ExamBackendService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [ExamBackendComponent]
      })
        .overrideTemplate(ExamBackendComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExamBackendComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExamBackendService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ExamBackend(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.examBackends && comp.examBackends[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
