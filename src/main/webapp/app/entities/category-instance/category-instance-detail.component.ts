import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategoryInstance } from 'app/shared/model/category-instance.model';

@Component({
  selector: 'jhi-category-instance-detail',
  templateUrl: './category-instance-detail.component.html'
})
export class CategoryInstanceDetailComponent implements OnInit {
  categoryInstance: ICategoryInstance | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoryInstance }) => (this.categoryInstance = categoryInstance));
  }

  previousState(): void {
    window.history.back();
  }
}
