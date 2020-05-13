import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { SubcategoryDetailComponent } from 'app/entities/subcategory/subcategory-detail.component';
import { Subcategory } from 'app/shared/model/subcategory.model';

describe('Component Tests', () => {
  describe('Subcategory Management Detail Component', () => {
    let comp: SubcategoryDetailComponent;
    let fixture: ComponentFixture<SubcategoryDetailComponent>;
    const route = ({ data: of({ subcategory: new Subcategory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [SubcategoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SubcategoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SubcategoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load subcategory on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.subcategory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
