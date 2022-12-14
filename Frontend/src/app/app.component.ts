import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import {
  BehaviorSubject,
  catchError,
  map,
  Observable,
  of,
  pipe,
  startWith,
} from 'rxjs';
import { dataState } from './enum/data-state.enum';
import { AppState } from './interface/app-state';
import { CustomResponse } from './interface/custom-response';
import { ServerService } from './service/server.service';
import { HttpClient } from '@angular/common/http';
import { Server } from './interface/server';
import { Status } from './enum/status.enum';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AppComponent implements OnInit {
  appState$: Observable<AppState<CustomResponse>>;
  readonly DataState = dataState;
  readonly ServerStatus = Status;
  private filterSubject = new BehaviorSubject<string>('');
  private dataSubject = new BehaviorSubject<CustomResponse>(null);

  filterStatus$ = this.filterSubject.asObservable();
  private addSubject = new BehaviorSubject<boolean>(false);
  addSubject$ = this.addSubject.asObservable();

  constructor(private serverService: ServerService, private http: HttpClient) {
    //this.NotifierModule = this.NotifierModule;
  }

  ngOnInit(): void {
    this.appState$ = this.serverService.servers$.pipe(
      map((response) => {
        this.dataSubject.next(response);
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
      .get<Server[]>('http://localhost:8080/server/list')
      .subscribe((data) => {
        console.log(data);
      });
  }

  pingServer(ipAddress: string): void {
    this.filterSubject.next(ipAddress);
    this.appState$ = this.serverService.ping$(ipAddress).pipe(
      map((response) => {
        let index = 1;
        index = this.dataSubject.value.data.servers.findIndex(
          (server) => server.id === response.data.server.id
        );
        console.log(index);
        this.dataSubject.value.data.servers[index] = response.data.server;
        console.log(this.dataSubject.value.data.servers);

        this.filterSubject.next('');
        return { dataState: dataState.LOADED, appData: this.dataSubject.value };
      }),
      startWith({
        dataState: dataState.LOADED,
        appData: this.dataSubject.value,
      }),
      catchError((error) => {
        this.filterSubject.next('');
        console.log(error);
        return of({ dataState: dataState.ERROR, appData: null, error: error });
      })
    );
  }

  filtringServers(status: Status): void {
    this.appState$ = this.serverService
      .filter$(status, this.dataSubject.value)
      .pipe(
        map((response) => {
          return { dataState: dataState.LOADED, appData: response };
        }),
        startWith({
          dataState: dataState.LOADED,
          appData: this.dataSubject.value,
        }),
        catchError((error) => {
          console.log(error);
          return of({
            dataState: dataState.ERROR,
            appData: null,
            error: error,
          });
        })
      );
  }

  onSelected(param: string): void {
    switch (param) {
      case 'SERVER_UP': {
        this.filtringServers(this.ServerStatus.SERVER_UP);
        break;
      }
      case 'SERVER_DOWN': {
        this.filtringServers(this.ServerStatus.SERVER_DOWN);
        break;
      }
      default: {
        this.filtringServers(this.ServerStatus.ALL);
        break;
      }
    }
    console.log(param);
  }
  saveServer(serverForm: NgForm): void {
    this.addSubject.next(true);
    this.appState$ = this.serverService.save$(<Server>serverForm.value).pipe(
      map((response) => {
        this.dataSubject.value.data.servers.push(response.data.server);
        document.getElementById('closeModal').click();
        serverForm.resetForm({ status: this.ServerStatus.SERVER_DOWN });
        this.addSubject.next(false);
        return { dataState: dataState.LOADED, appData: this.dataSubject.value };
      }),
      startWith({
        dataState: dataState.LOADED,
        appData: this.dataSubject.value,
      }),
      catchError((error) => {
        this.addSubject.next(false);
        console.log(error);
        return of({ dataState: dataState.ERROR, appData: null, error: error });
      })
    );
  }

  deleteServer(id: number): void {
    this.appState$ = this.serverService.delete$(id).pipe(
      map((response) => {
        if (response.data.server) {
          this.dataSubject.value.data.servers =
            this.dataSubject.value.data.servers.filter(
              (server) => server.id != id
            );
        }
        return { dataState: dataState.LOADED, appData: this.dataSubject.value };
      }),
      startWith({
        dataState: dataState.LOADED,
        appData: this.dataSubject.value,
      }),
      catchError((error) => {
        console.log(error);
        return of({ dataState: dataState.ERROR, appData: null, error: error });
      })
    );
  }
  printReport(): void {
    this.serverService.printReport();
  }
}
