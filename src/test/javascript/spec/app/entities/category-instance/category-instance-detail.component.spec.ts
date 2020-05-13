import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { CategoryInstanceDetailComponent } from 'app/entities/category-instance/category-instance-detail.component';
import { CategoryInstance } from 'app/shared/model/category-instance.model';

describe('Component Tests', () => {
  describe('CategoryInstance Management Detail Component', () => {
    let comp: CategoryInstanceDetailComponent;
    let fixture: ComponentFixture<CategoryInstanceDetailComponent>;
    const route = ({ data: of({ categoryInstance: new CategoryInstance(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [CategoryInstanceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CategoryInstanceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategoryInstanceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load categoryInstance on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categoryInstance).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
