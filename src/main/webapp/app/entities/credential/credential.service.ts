import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICredential } from 'app/shared/model/credential.model';

type EntityResponseType = HttpResponse<ICredential>;
type EntityArrayResponseType = HttpResponse<ICredential[]>;

@Injectable({ providedIn: 'root' })
export class CredentialService {
    private resourceUrl = SERVER_API_URL + 'api/credentials';

    constructor(private http: HttpClient) {}

    create(credential: ICredential): Observable<EntityResponseType> {
        return this.http.post<ICredential>(this.resourceUrl, credential, { observe: 'response' });
    }

    update(credential: ICredential): Observable<EntityResponseType> {
        return this.http.put<ICredential>(this.resourceUrl, credential, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICredential>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICredential[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
