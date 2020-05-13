import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICloudRegion } from 'app/shared/model/cloud-region.model';

@Component({
  selector: 'jhi-cloud-region-detail',
  templateUrl: './cloud-region-detail.component.html'
})
export class CloudRegionDetailComponent implements OnInit {
  cloudRegion: ICloudRegion | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cloudRegion }) => (this.cloudRegion = cloudRegion));
  }

  previousState(): void {
    window.history.back();
  }
}
