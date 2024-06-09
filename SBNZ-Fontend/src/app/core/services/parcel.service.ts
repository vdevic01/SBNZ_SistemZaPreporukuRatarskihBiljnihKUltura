import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {CreateParcelDto, CropType, Parcel} from "../model/parcel";
import {environment} from "../../environment";

@Injectable({
  providedIn: 'root'
})
export class ParcelService {

  constructor(private http: HttpClient) { }

  getParcelsForCurrentUser():Observable<Parcel[]>{
    return this.http.get<Parcel[]>(`${environment.apiHost}/parcels`);
  }

  getParcel(id: number):Observable<Parcel>{
    return this.http.get<Parcel>(`${environment.apiHost}/parcel/${id}`);
  }

  sowCrop(parcelId: number, cropType: CropType): Observable<Parcel>{
    return this.http.post<Parcel>(`${environment.apiHost}/parcel/${parcelId}/sow/${cropType}`, {});
  }

  createParcel(parcel: CreateParcelDto): Observable<Parcel>{
    return this.http.post<Parcel>(`${environment.apiHost}/parcel`, parcel);
  }
}
