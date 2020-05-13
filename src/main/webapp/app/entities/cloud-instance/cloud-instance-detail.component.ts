import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICloudInstance } from 'app/shared/model/cloud-instance.model';

@Component({
  selector: 'jhi-cloud-instance-detail',
  templateUrl: './cloud-instance-detail.component.html'
})
export class CloudInstanceDetailComponent implements OnInit {
  cloudInstance: ICloudInstance | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cloudInstance }) => (this.cloudInstance = cloudInstance));
  }

  previousState(): void {
    window.history.back();
  }
}
