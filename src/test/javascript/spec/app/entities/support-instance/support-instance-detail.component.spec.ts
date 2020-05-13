import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { SupportInstanceDetailComponent } from 'app/entities/support-instance/support-instance-detail.component';
import { SupportInstance } from 'app/shared/model/support-instance.model';

describe('Component Tests', () => {
  describe('SupportInstance Management Detail Component', () => {
    let comp: SupportInstanceDetailComponent;
    let fixture: ComponentFixture<SupportInstanceDetailComponent>;
    const route = ({ data: of({ supportInstance: new SupportInstance(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [SupportInstanceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SupportInstanceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SupportInstanceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load supportInstance on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.supportInstance).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
