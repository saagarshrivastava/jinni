import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JinniTestModule } from '../../../test.module';
import { MajorIncidentComponent } from 'app/entities/major-incident/major-incident.component';
import { MajorIncidentService } from 'app/entities/major-incident/major-incident.service';
import { MajorIncident } from 'app/shared/model/major-incident.model';

describe('Component Tests', () => {
  describe('MajorIncident Management Component', () => {
    let comp: MajorIncidentComponent;
    let fixture: ComponentFixture<MajorIncidentComponent>;
    let service: MajorIncidentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [MajorIncidentComponent]
      })
        .overrideTemplate(MajorIncidentComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MajorIncidentComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MajorIncidentService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MajorIncident(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.majorIncidents && comp.majorIncidents[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
