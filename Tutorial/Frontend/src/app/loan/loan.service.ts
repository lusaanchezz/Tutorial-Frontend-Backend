import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Pageable } from '../core/model/page/Pageable';
import { Observable, of } from 'rxjs';
import { LoanPage } from './model/LoanPage';
import { LoanSearchDto } from './model/LoanSearchDto';
import { LOAN_DATA } from './model/mock-loans';
import { Loan } from './model/Loan';

@Injectable({
  providedIn: 'root'
})
export class LoanService {

  url: string = 'http://localhost:8080/loan';

  constructor(private http: HttpClient) { }

  getLoans(searchDto: LoanSearchDto): Observable<LoanPage> {
    this.url = this.createUrl(searchDto.gameId, searchDto.clientId, searchDto.searchDate);
    console.log("url: " + this.url)
    return this.http.post<LoanPage>(this.url, searchDto);
  }

  saveLoan(loan: Loan): Observable<void> {
    return this.http.put<void>(this.url, loan);
  }

  deleteLoan(idLoan: number): Observable<void> {
    return this.http.delete<void>(this.url + '/' + idLoan);
  }

  private createUrl(gameId?: number, clientId?: number, date?: String) : string {
    let params = '';

    if(gameId != null) {
      params += 'game=' + gameId;
    }
    if(clientId != null) {
      if(params != '') {
        params += "&"
      }
      params += 'client=' + clientId;
    }
    if(date != null) {
      if(params != '') {
        params += "&"
      }
      params += 'date=' + date;
    }

    if(params == '') return this.url;
    else return this.url + '?' + params;
  }
}
