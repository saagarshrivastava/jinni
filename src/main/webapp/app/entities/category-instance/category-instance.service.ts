import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ICategoryInstance } from 'app/shared/model/category-instance.model';

type EntityResponseType = HttpResponse<ICategoryInstance>;
type EntityArrayResponseType = HttpResponse<ICategoryInstance[]>;

@Injectable({ providedIn: 'root' })
export class CategoryInstanceService {
  public resourceUrl = SERVER_API_URL + 'api/category-instances';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/category-instances';

  constructor(protected http: HttpClient) {}

  create(categoryInstance: ICategoryInstance): Observable<EntityResponseType> {
    return this.http.post<ICategoryInstance>(this.resourceUrl, categoryInstance, { observe: 'response' });
  }

  update(categoryInstance: ICategoryInstance): Observable<EntityResponseType> {
    return this.http.put<ICategoryInstance>(this.resourceUrl, categoryInstance, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICategoryInstance>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategoryInstance[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategoryInstance[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
