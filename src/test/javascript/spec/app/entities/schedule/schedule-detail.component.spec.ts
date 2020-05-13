import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { ScheduleDetailComponent } from 'app/entities/schedule/schedule-detail.component';
import { Schedule } from 'app/shared/model/schedule.model';

describe('Component Tests', () => {
  describe('Schedule Management Detail Component', () => {
    let comp: ScheduleDetailComponent;
    let fixture: ComponentFixture<ScheduleDetailComponent>;
    const route = ({ data: of({ schedule: new Schedule(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [ScheduleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ScheduleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ScheduleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load schedule on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.schedule).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
