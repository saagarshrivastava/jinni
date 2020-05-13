import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ISupportInstance, SupportInstance } from 'app/shared/model/support-instance.model';
import { SupportInstanceService } from './support-instance.service';
import { ISupportPerson } from 'app/shared/model/support-person.model';
import { SupportPersonService } from 'app/entities/support-person/support-person.service';
import { ISession } from 'app/shared/model/session.model';
import { SessionService } from 'app/entities/session/session.service';

type SelectableEntity = ISupportPerson | ISession;

@Component({
  selector: 'jhi-support-instance-update',
  templateUrl: './support-instance-update.component.html'
})
export class SupportInstanceUpdateComponent implements OnInit {
  isSaving = false;
  supportpersonids: ISupportPerson[] = [];
  sessions: ISession[] = [];
  starttimeDp: any;
  endtimeDp: any;

  editForm = this.fb.group({
    id: [],
    starttime: [],
    endtime: [],
    chatlogs: [],
    sessionid: [],
    supportpersonid: [],
    supportpersonid: [],
    sessionid: []
  });

  constructor(
    protected supportInstanceService: SupportInstanceService,
    protected supportPersonService: SupportPersonService,
    protected sessionService: SessionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ supportInstance }) => {
      this.updateForm(supportInstance);

      this.supportPersonService
        .query({ filter: 'supportinstance-is-null' })
        .pipe(
          map((res: HttpResponse<ISupportPerson[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ISupportPerson[]) => {
          if (!supportInstance.supportpersonid || !supportInstance.supportpersonid.id) {
            this.supportpersonids = resBody;
          } else {
            this.supportPersonService
              .find(supportInstance.supportpersonid.id)
              .pipe(
                map((subRes: HttpResponse<ISupportPerson>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ISupportPerson[]) => (this.supportpersonids = concatRes));
          }
        });

      this.sessionService.query().subscribe((res: HttpResponse<ISession[]>) => (this.sessions = res.body || []));
    });
  }

  updateForm(supportInstance: ISupportInstance): void {
    this.editForm.patchValue({
      id: supportInstance.id,
      starttime: supportInstance.starttime,
      endtime: supportInstance.endtime,
      chatlogs: supportInstance.chatlogs,
      sessionid: supportInstance.sessionid,
      supportpersonid: supportInstance.supportpersonid,
      supportpersonid: supportInstance.supportpersonid,
      sessionid: supportInstance.sessionid
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const supportInstance = this.createFromForm();
    if (supportInstance.id !== undefined) {
      this.subscribeToSaveResponse(this.supportInstanceService.update(supportInstance));
    } else {
      this.subscribeToSaveResponse(this.supportInstanceService.create(supportInstance));
    }
  }

  private createFromForm(): ISupportInstance {
    return {
      ...new SupportInstance(),
      id: this.editForm.get(['id'])!.value,
      starttime: this.editForm.get(['starttime'])!.value,
      endtime: this.editForm.get(['endtime'])!.value,
      chatlogs: this.editForm.get(['chatlogs'])!.value,
      sessionid: this.editForm.get(['sessionid'])!.value,
      supportpersonid: this.editForm.get(['supportpersonid'])!.value,
      supportpersonid: this.editForm.get(['supportpersonid'])!.value,
      sessionid: this.editForm.get(['sessionid'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISupportInstance>>): void {
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
