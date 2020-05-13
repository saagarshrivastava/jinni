import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IOffer, Offer } from 'app/shared/model/offer.model';
import { OfferService } from './offer.service';
import { IOfferType } from 'app/shared/model/offer-type.model';
import { OfferTypeService } from 'app/entities/offer-type/offer-type.service';
import { IIncident } from 'app/shared/model/incident.model';
import { IncidentService } from 'app/entities/incident/incident.service';
import { IExam } from 'app/shared/model/exam.model';
import { ExamService } from 'app/entities/exam/exam.service';

type SelectableEntity = IOfferType | IIncident | IExam;

@Component({
  selector: 'jhi-offer-update',
  templateUrl: './offer-update.component.html'
})
export class OfferUpdateComponent implements OnInit {
  isSaving = false;
  offertypeids: IOfferType[] = [];
  incidents: IIncident[] = [];
  exams: IExam[] = [];

  editForm = this.fb.group({
    id: [],
    incidentid: [],
    offertypeid: [],
    examid: [],
    discountpercentage: [],
    offertypeid: [],
    incidentid: [],
    examid: []
  });

  constructor(
    protected offerService: OfferService,
    protected offerTypeService: OfferTypeService,
    protected incidentService: IncidentService,
    protected examService: ExamService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ offer }) => {
      this.updateForm(offer);

      this.offerTypeService
        .query({ filter: 'offer-is-null' })
        .pipe(
          map((res: HttpResponse<IOfferType[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IOfferType[]) => {
          if (!offer.offertypeid || !offer.offertypeid.id) {
            this.offertypeids = resBody;
          } else {
            this.offerTypeService
              .find(offer.offertypeid.id)
              .pipe(
                map((subRes: HttpResponse<IOfferType>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IOfferType[]) => (this.offertypeids = concatRes));
          }
        });

      this.incidentService.query().subscribe((res: HttpResponse<IIncident[]>) => (this.incidents = res.body || []));

      this.examService.query().subscribe((res: HttpResponse<IExam[]>) => (this.exams = res.body || []));
    });
  }

  updateForm(offer: IOffer): void {
    this.editForm.patchValue({
      id: offer.id,
      incidentid: offer.incidentid,
      offertypeid: offer.offertypeid,
      examid: offer.examid,
      discountpercentage: offer.discountpercentage,
      offertypeid: offer.offertypeid,
      incidentid: offer.incidentid,
      examid: offer.examid
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const offer = this.createFromForm();
    if (offer.id !== undefined) {
      this.subscribeToSaveResponse(this.offerService.update(offer));
    } else {
      this.subscribeToSaveResponse(this.offerService.create(offer));
    }
  }

  private createFromForm(): IOffer {
    return {
      ...new Offer(),
      id: this.editForm.get(['id'])!.value,
      incidentid: this.editForm.get(['incidentid'])!.value,
      offertypeid: this.editForm.get(['offertypeid'])!.value,
      examid: this.editForm.get(['examid'])!.value,
      discountpercentage: this.editForm.get(['discountpercentage'])!.value,
      offertypeid: this.editForm.get(['offertypeid'])!.value,
      incidentid: this.editForm.get(['incidentid'])!.value,
      examid: this.editForm.get(['examid'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOffer>>): void {
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
