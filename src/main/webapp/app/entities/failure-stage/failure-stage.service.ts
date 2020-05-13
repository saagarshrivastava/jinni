import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IFailureStage } from 'app/shared/model/failure-stage.model';

type EntityResponseType = HttpResponse<IFailureStage>;
type EntityArrayResponseType = HttpResponse<IFailureStage[]>;

@Injectable({ providedIn: 'root' })
export class FailureStageService {
  public resourceUrl = SERVER_API_URL + 'api/failure-stages';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/failure-stages';

  constructor(protected http: HttpClient) {}

  create(failureStage: IFailureStage): Observable<EntityResponseType> {
    return this.http.post<IFailureStage>(this.resourceUrl, failureStage, { observe: 'response' });
  }

  update(failureStage: IFailureStage): Observable<EntityResponseType> {
    return this.http.put<IFailureStage>(this.resourceUrl, failureStage, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFailureStage>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFailureStage[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFailureStage[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
