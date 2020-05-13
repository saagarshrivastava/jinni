import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ICloudInstance, CloudInstance } from 'app/shared/model/cloud-instance.model';
import { CloudInstanceService } from './cloud-instance.service';
import { ICloudRegion } from 'app/shared/model/cloud-region.model';
import { CloudRegionService } from 'app/entities/cloud-region/cloud-region.service';
import { IExamBackend } from 'app/shared/model/exam-backend.model';
import { ExamBackendService } from 'app/entities/exam-backend/exam-backend.service';

type SelectableEntity = ICloudRegion | IExamBackend;

@Component({
  selector: 'jhi-cloud-instance-update',
  templateUrl: './cloud-instance-update.component.html'
})
export class CloudInstanceUpdateComponent implements OnInit {
  isSaving = false;
  cloudregionids: ICloudRegion[] = [];
  exambackendids: IExamBackend[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    description: [],
    cloudregionid: [],
    exambackendid: [],
    cloudregionid: [],
    exambackendid: []
  });

  constructor(
    protected cloudInstanceService: CloudInstanceService,
    protected cloudRegionService: CloudRegionService,
    protected examBackendService: ExamBackendService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cloudInstance }) => {
      this.updateForm(cloudInstance);

      this.cloudRegionService
        .query({ filter: 'cloudinstance-is-null' })
        .pipe(
          map((res: HttpResponse<ICloudRegion[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICloudRegion[]) => {
          if (!cloudInstance.cloudregionid || !cloudInstance.cloudregionid.id) {
            this.cloudregionids = resBody;
          } else {
            this.cloudRegionService
              .find(cloudInstance.cloudregionid.id)
              .pipe(
                map((subRes: HttpResponse<ICloudRegion>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICloudRegion[]) => (this.cloudregionids = concatRes));
          }
        });

      this.examBackendService
        .query({ filter: 'cloudinstance-is-null' })
        .pipe(
          map((res: HttpResponse<IExamBackend[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IExamBackend[]) => {
          if (!cloudInstance.exambackendid || !cloudInstance.exambackendid.id) {
            this.exambackendids = resBody;
          } else {
            this.examBackendService
              .find(cloudInstance.exambackendid.id)
              .pipe(
                map((subRes: HttpResponse<IExamBackend>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IExamBackend[]) => (this.exambackendids = concatRes));
          }
        });
    });
  }

  updateForm(cloudInstance: ICloudInstance): void {
    this.editForm.patchValue({
      id: cloudInstance.id,
      code: cloudInstance.code,
      description: cloudInstance.description,
      cloudregionid: cloudInstance.cloudregionid,
      exambackendid: cloudInstance.exambackendid,
      cloudregionid: cloudInstance.cloudregionid,
      exambackendid: cloudInstance.exambackendid
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cloudInstance = this.createFromForm();
    if (cloudInstance.id !== undefined) {
      this.subscribeToSaveResponse(this.cloudInstanceService.update(cloudInstance));
    } else {
      this.subscribeToSaveResponse(this.cloudInstanceService.create(cloudInstance));
    }
  }

  private createFromForm(): ICloudInstance {
    return {
      ...new CloudInstance(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      description: this.editForm.get(['description'])!.value,
      cloudregionid: this.editForm.get(['cloudregionid'])!.value,
      exambackendid: this.editForm.get(['exambackendid'])!.value,
      cloudregionid: this.editForm.get(['cloudregionid'])!.value,
      exambackendid: this.editForm.get(['exambackendid'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICloudInstance>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
