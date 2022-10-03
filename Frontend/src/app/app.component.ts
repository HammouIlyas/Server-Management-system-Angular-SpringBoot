import { Component, OnInit } from '@angular/core';
import { catchError, map, Observable, of, startWith } from 'rxjs';
import { dataState } from './enum/data-state.enum';
import { AppState } from './interface/app-state';
import { CustomResponse } from './interface/custom-response';
import { ServerService } from './service/server.service';
import { HttpClient } from '@angular/common/http';
import { Server } from './interface/server';
import { Status } from './enum/status.enum';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  appState$: Observable<AppState<CustomResponse>>;
  readonly DataState = dataState;
  readonly ServerStatus = Status;
  constructor(private serverService: ServerService, private http: HttpClient) {}

  ngOnInit(): void {
    this.appState$ = this.serverService.servers$.pipe(
      map((response) => {
        console.log('tiiiiiit tiiiiiiiiit');
        return { dataState: dataState.LOADED, appData: response };
      }),
      startWith({ dataState: dataState.LOADING, appData: null }),
      catchError((error) => {
        console.log(error);
        return of({ dataState: dataState.ERROR, appData: null, error: error });
      })
    );
  }
  //do it the old way
  getserver(): any {
    return this.http
      .get<Server[]>('http://localhost:8081/server/list')
      .subscribe((data) => {
        console.log(data);
      });
  }
}
