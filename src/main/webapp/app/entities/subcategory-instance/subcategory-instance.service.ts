import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ISubcategoryInstance } from 'app/shared/model/subcategory-instance.model';

type EntityResponseType = HttpResponse<ISubcategoryInstance>;
type EntityArrayResponseType = HttpResponse<ISubcategoryInstance[]>;

@Injectable({ providedIn: 'root' })
export class SubcategoryInstanceService {
  public resourceUrl = SERVER_API_URL + 'api/subcategory-instances';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/subcategory-instances';

  constructor(protected http: HttpClient) {}

  create(subcategoryInstance: ISubcategoryInstance): Observable<EntityResponseType> {
    return this.http.post<ISubcategoryInstance>(this.resourceUrl, subcategoryInstance, { observe: 'response' });
  }

  update(subcategoryInstance: ISubcategoryInstance): Observable<EntityResponseType> {
    return this.http.put<ISubcategoryInstance>(this.resourceUrl, subcategoryInstance, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISubcategoryInstance>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISubcategoryInstance[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISubcategoryInstance[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
