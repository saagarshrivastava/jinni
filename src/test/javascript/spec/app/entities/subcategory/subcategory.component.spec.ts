import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JinniTestModule } from '../../../test.module';
import { SubcategoryComponent } from 'app/entities/subcategory/subcategory.component';
import { SubcategoryService } from 'app/entities/subcategory/subcategory.service';
import { Subcategory } from 'app/shared/model/subcategory.model';

describe('Component Tests', () => {
  describe('Subcategory Management Component', () => {
    let comp: SubcategoryComponent;
    let fixture: ComponentFixture<SubcategoryComponent>;
    let service: SubcategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [SubcategoryComponent]
      })
        .overrideTemplate(SubcategoryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SubcategoryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubcategoryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Subcategory(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.subcategories && comp.subcategories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
