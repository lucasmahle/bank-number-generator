import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IApiResponse } from '../models/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import { ApiResponseError } from '../error/api-response.error';
import { NumberType } from '../models/number-type.enum';
import { IGeneratedNumber } from '../models/generated-number.model';

@Injectable({
  providedIn: 'root'
})
export class NumberService {
  baseUrl = environment.apiUrl;

  constructor(
    private http: HttpClient
  ) { }

  getNextNumber(): Observable<{ number: number, message?: string }> {
    return this.http.post<IApiResponse<{ number: number, message?: string }>>(this.baseUrl + '/number/next', {})
      .pipe(
        map(response => {
          if (!response.success) {
            throw new ApiResponseError(response.message);
          }

          return {
            number: response.data ? response.data.number : 0,
            message: response.message
          };
        }),
        catchError(this.handleError)
      );
  }

  getLatestNumberList(): Observable<IGeneratedNumber[]> {
    return this.http.get<IApiResponse<IGeneratedNumber[]>>(this.baseUrl + '/number/latest')
      .pipe(
        map(response => {
          if (!response.success) {
            throw new ApiResponseError(response.message);
          }

          return response.data;
        }),
        catchError(this.handleError)
      );
  }

  getCurrentNumber(): Observable<IGeneratedNumber> {
    return this.http.get<IApiResponse<IGeneratedNumber>>(this.baseUrl + '/number/current')
      .pipe(
        map(response => {
          if (!response.success) {
            throw new ApiResponseError(response.message);
          }

          return response.data;
        }),
        catchError(this.handleError)
      );
  }

  resetNumberGeneration(): Observable<void> {
    return this.http.post<IApiResponse<void>>(this.baseUrl + '/number/reset', {})
      .pipe(
        map(response => {
        }),
        catchError(this.handleError)
      );
  }

  generateNormalNumber(): Observable<IGeneratedNumber> {
    return this.generateNumber(NumberType.Normal);
  }

  generatePreferentialNumber(): Observable<IGeneratedNumber> {
    return this.generateNumber(NumberType.Preferential);
  }

  generateNumber(type: NumberType): Observable<IGeneratedNumber> {
    return this.http.post<IApiResponse<IGeneratedNumber>>(this.baseUrl + `/number/${type}`, {})
      .pipe(
        map(response => {
          if (!response.success) {
            throw new ApiResponseError(response.message);
          }

          return response.data;
        }),
        catchError(this.handleError)
      );
  }

  private handleError(error: HttpErrorResponse | ApiResponseError) {
    console.error('response error:', error);

    if (error instanceof ApiResponseError) {
      return throwError(error.message);
    }

    return throwError('Error ao executar consulta');
  }
}
