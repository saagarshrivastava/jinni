import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISupportPerson } from 'app/shared/model/support-person.model';

@Component({
  selector: 'jhi-support-person-detail',
  templateUrl: './support-person-detail.component.html'
})
export class SupportPersonDetailComponent implements OnInit {
  supportPerson: ISupportPerson | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ supportPerson }) => (this.supportPerson = supportPerson));
  }

  previousState(): void {
    window.history.back();
  }
}
