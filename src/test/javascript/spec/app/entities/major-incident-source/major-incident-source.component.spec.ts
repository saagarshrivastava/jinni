import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JinniTestModule } from '../../../test.module';
import { MajorIncidentSourceComponent } from 'app/entities/major-incident-source/major-incident-source.component';
import { MajorIncidentSourceService } from 'app/entities/major-incident-source/major-incident-source.service';
import { MajorIncidentSource } from 'app/shared/model/major-incident-source.model';

describe('Component Tests', () => {
  describe('MajorIncidentSource Management Component', () => {
    let comp: MajorIncidentSourceComponent;
    let fixture: ComponentFixture<MajorIncidentSourceComponent>;
    let service: MajorIncidentSourceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [MajorIncidentSourceComponent]
      })
        .overrideTemplate(MajorIncidentSourceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MajorIncidentSourceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MajorIncidentSourceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MajorIncidentSource(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.majorIncidentSources && comp.majorIncidentSources[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
