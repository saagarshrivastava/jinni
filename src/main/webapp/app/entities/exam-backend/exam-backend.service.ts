import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IExamBackend } from 'app/shared/model/exam-backend.model';

type EntityResponseType = HttpResponse<IExamBackend>;
type EntityArrayResponseType = HttpResponse<IExamBackend[]>;

@Injectable({ providedIn: 'root' })
export class ExamBackendService {
  public resourceUrl = SERVER_API_URL + 'api/exam-backends';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/exam-backends';

  constructor(protected http: HttpClient) {}

  create(examBackend: IExamBackend): Observable<EntityResponseType> {
    return this.http.post<IExamBackend>(this.resourceUrl, examBackend, { observe: 'response' });
  }

  update(examBackend: IExamBackend): Observable<EntityResponseType> {
    return this.http.put<IExamBackend>(this.resourceUrl, examBackend, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IExamBackend>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IExamBackend[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IExamBackend[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
