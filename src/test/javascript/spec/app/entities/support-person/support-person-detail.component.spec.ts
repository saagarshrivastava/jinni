import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { SupportPersonDetailComponent } from 'app/entities/support-person/support-person-detail.component';
import { SupportPerson } from 'app/shared/model/support-person.model';

describe('Component Tests', () => {
  describe('SupportPerson Management Detail Component', () => {
    let comp: SupportPersonDetailComponent;
    let fixture: ComponentFixture<SupportPersonDetailComponent>;
    const route = ({ data: of({ supportPerson: new SupportPerson(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [SupportPersonDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SupportPersonDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SupportPersonDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load supportPerson on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.supportPerson).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
