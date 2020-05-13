import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISubcategoryInstance } from 'app/shared/model/subcategory-instance.model';

@Component({
  selector: 'jhi-subcategory-instance-detail',
  templateUrl: './subcategory-instance-detail.component.html'
})
export class SubcategoryInstanceDetailComponent implements OnInit {
  subcategoryInstance: ISubcategoryInstance | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subcategoryInstance }) => (this.subcategoryInstance = subcategoryInstance));
  }

  previousState(): void {
    window.history.back();
  }
}
