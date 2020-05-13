import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { SubcategoryInstanceDetailComponent } from 'app/entities/subcategory-instance/subcategory-instance-detail.component';
import { SubcategoryInstance } from 'app/shared/model/subcategory-instance.model';

describe('Component Tests', () => {
  describe('SubcategoryInstance Management Detail Component', () => {
    let comp: SubcategoryInstanceDetailComponent;
    let fixture: ComponentFixture<SubcategoryInstanceDetailComponent>;
    const route = ({ data: of({ subcategoryInstance: new SubcategoryInstance(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [SubcategoryInstanceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SubcategoryInstanceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SubcategoryInstanceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load subcategoryInstance on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.subcategoryInstance).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
