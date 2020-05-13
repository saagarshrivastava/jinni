import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ISupportPerson } from 'app/shared/model/support-person.model';

type EntityResponseType = HttpResponse<ISupportPerson>;
type EntityArrayResponseType = HttpResponse<ISupportPerson[]>;

@Injectable({ providedIn: 'root' })
export class SupportPersonService {
  public resourceUrl = SERVER_API_URL + 'api/support-people';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/support-people';

  constructor(protected http: HttpClient) {}

  create(supportPerson: ISupportPerson): Observable<EntityResponseType> {
    return this.http.post<ISupportPerson>(this.resourceUrl, supportPerson, { observe: 'response' });
  }

  update(supportPerson: ISupportPerson): Observable<EntityResponseType> {
    return this.http.put<ISupportPerson>(this.resourceUrl, supportPerson, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISupportPerson>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISupportPerson[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISupportPerson[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
