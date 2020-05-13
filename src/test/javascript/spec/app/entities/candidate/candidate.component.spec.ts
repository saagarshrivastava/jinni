import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JinniTestModule } from '../../../test.module';
import { CandidateComponent } from 'app/entities/candidate/candidate.component';
import { CandidateService } from 'app/entities/candidate/candidate.service';
import { Candidate } from 'app/shared/model/candidate.model';

describe('Component Tests', () => {
  describe('Candidate Management Component', () => {
    let comp: CandidateComponent;
    let fixture: ComponentFixture<CandidateComponent>;
    let service: CandidateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [CandidateComponent]
      })
        .overrideTemplate(CandidateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CandidateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CandidateService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Candidate(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.candidates && comp.candidates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
