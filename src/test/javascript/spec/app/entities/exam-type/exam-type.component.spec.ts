import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JinniTestModule } from '../../../test.module';
import { ExamTypeComponent } from 'app/entities/exam-type/exam-type.component';
import { ExamTypeService } from 'app/entities/exam-type/exam-type.service';
import { ExamType } from 'app/shared/model/exam-type.model';

describe('Component Tests', () => {
  describe('ExamType Management Component', () => {
    let comp: ExamTypeComponent;
    let fixture: ComponentFixture<ExamTypeComponent>;
    let service: ExamTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [ExamTypeComponent]
      })
        .overrideTemplate(ExamTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExamTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExamTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ExamType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.examTypes && comp.examTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
