import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IOfferType } from 'app/shared/model/offer-type.model';

type EntityResponseType = HttpResponse<IOfferType>;
type EntityArrayResponseType = HttpResponse<IOfferType[]>;

@Injectable({ providedIn: 'root' })
export class OfferTypeService {
  public resourceUrl = SERVER_API_URL + 'api/offer-types';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/offer-types';

  constructor(protected http: HttpClient) {}

  create(offerType: IOfferType): Observable<EntityResponseType> {
    return this.http.post<IOfferType>(this.resourceUrl, offerType, { observe: 'response' });
  }

  update(offerType: IOfferType): Observable<EntityResponseType> {
    return this.http.put<IOfferType>(this.resourceUrl, offerType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOfferType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOfferType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOfferType[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
