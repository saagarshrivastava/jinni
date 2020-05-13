import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { ProctorDetailComponent } from 'app/entities/proctor/proctor-detail.component';
import { Proctor } from 'app/shared/model/proctor.model';

describe('Component Tests', () => {
  describe('Proctor Management Detail Component', () => {
    let comp: ProctorDetailComponent;
    let fixture: ComponentFixture<ProctorDetailComponent>;
    const route = ({ data: of({ proctor: new Proctor(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [ProctorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProctorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProctorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load proctor on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.proctor).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
