import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { Status } from '../enum/status.enum';
import { CustomResponse } from '../interface/custom-response';
import { Server } from '../interface/server';

@Injectable({
  providedIn: 'root',
})
export class ServerService {
  constructor(private http: HttpClient) {}
  private readonly apiUrl: string = 'http://localhost:8081';

  getServers(): Observable<CustomResponse> {
    return this.http.get<CustomResponse>(this.apiUrl);
  }
  //seconde implementation of getServers()
  servers$ = <Observable<CustomResponse>>(
    this.http
      .get<CustomResponse>(`${this.apiUrl}/server/list`)
      .pipe(tap(console.log), catchError(this.handleError))
  );
  getAllServers() {
    return this.http
      .get<CustomResponse>(`${this.apiUrl}/server/list`)
      .subscribe((data) => {
        console.log(data);
      });
  }

  save$ = (server: Server) =>
    <Observable<CustomResponse>>(
      this.http
        .post<CustomResponse>(this.apiUrl + '/server/save', server)
        .pipe(tap(console.log), catchError(this.handleError))
    );
  server$ = (serverId: number) =>
    <Observable<CustomResponse>>(
      this.http
        .get<CustomResponse>(this.apiUrl + '/server/get' + '/' + serverId)
        .pipe(tap(console.log), catchError(this.handleError))
    );

  ping$ = (ipAddress: string) =>
    <Observable<CustomResponse>>(
      this.http
        .get<CustomResponse>(this.apiUrl + '/server/ping' + '/' + ipAddress)
        .pipe(tap(console.log), catchError(this.handleError))
    );

  filter$ = (status: Status, response: CustomResponse) =>
    <Observable<CustomResponse>>new Observable<CustomResponse>((subscriber) => {
      console.log(response);
      subscriber.next(
        status === Status.ALL
          ? { ...response, message: 'Servers filtred by ${status} status' }
          : {
              ...response,
              message:
                response.data.servers.filter(
                  (server) => server.status === status
                ).length > 0
                  ? 'Servers filtred by ${status === Status.SERVER_UP ? "SERVER UP" : "SERVER DOWN"} status'
                  : 'No servers of ${status} found',
              data: {
                servers: response.data.servers.filter(
                  (server) => server.status === status
                ),
              },
            }
      );
      subscriber.complete();
    }).pipe(tap(console.log), catchError(this.handleError));

  delete$ = (serverId: number) =>
    <Observable<CustomResponse>>(
      this.http
        .delete<CustomResponse>(this.apiUrl + '/server/delete' + '/' + serverId)
        .pipe(tap(console.log), catchError(this.handleError))
    );

  handleError(error: HttpErrorResponse): Observable<never> {
    console.log(error);
    return throwError('there is an error , error-code : }' + error.status);
  }

  printReport(): void {
    let dataType = 'application/vnd.ms-excel.sheet.macroEnabled.12';
    let tableData = document.getElementById('servers');
    let htmlTable = tableData.outerHTML.replace(/ /g, '%20');
    let linkDownload = document.createElement('a');
    document.body.appendChild(linkDownload);
    linkDownload.href = 'data:' + dataType + ', ' + htmlTable;
    linkDownload.download = 'servers-full-report.xls';
    linkDownload.click();
    document.body.removeChild(linkDownload);
  }
}
