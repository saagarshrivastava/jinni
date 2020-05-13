import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISupportInstance } from 'app/shared/model/support-instance.model';

@Component({
  selector: 'jhi-support-instance-detail',
  templateUrl: './support-instance-detail.component.html'
})
export class SupportInstanceDetailComponent implements OnInit {
  supportInstance: ISupportInstance | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ supportInstance }) => (this.supportInstance = supportInstance));
  }

  previousState(): void {
    window.history.back();
  }
}
