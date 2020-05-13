import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { MajorIncidentSourceDetailComponent } from 'app/entities/major-incident-source/major-incident-source-detail.component';
import { MajorIncidentSource } from 'app/shared/model/major-incident-source.model';

describe('Component Tests', () => {
  describe('MajorIncidentSource Management Detail Component', () => {
    let comp: MajorIncidentSourceDetailComponent;
    let fixture: ComponentFixture<MajorIncidentSourceDetailComponent>;
    const route = ({ data: of({ majorIncidentSource: new MajorIncidentSource(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [MajorIncidentSourceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MajorIncidentSourceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MajorIncidentSourceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load majorIncidentSource on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.majorIncidentSource).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
