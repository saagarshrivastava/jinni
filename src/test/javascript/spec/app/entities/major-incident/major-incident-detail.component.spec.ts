import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { MajorIncidentDetailComponent } from 'app/entities/major-incident/major-incident-detail.component';
import { MajorIncident } from 'app/shared/model/major-incident.model';

describe('Component Tests', () => {
  describe('MajorIncident Management Detail Component', () => {
    let comp: MajorIncidentDetailComponent;
    let fixture: ComponentFixture<MajorIncidentDetailComponent>;
    const route = ({ data: of({ majorIncident: new MajorIncident(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [MajorIncidentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MajorIncidentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MajorIncidentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load majorIncident on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.majorIncident).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
