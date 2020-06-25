import { Injectable } from '@angular/core';
import { Subject, Observable, Observer } from 'rxjs';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  constructor(

  ) { }

  public connect(): Subject<MessageEvent> {
    const ws = this.create();

    const observable = Observable.create((obs: Observer<MessageEvent>) => {
      ws.onmessage = obs.next.bind(obs);
      ws.onerror = obs.error.bind(obs);
      ws.onclose = () => {
        console.log('Web Socket closed');
      }

      return obs.complete.bind(obs);
    });

    const observer = {
      next: (data: Object) => {
        if (ws.readyState === WebSocket.OPEN) {
          ws.send(JSON.stringify(data));
        }
      },
      complete: () => {
        ws.close();
      }
    };

    return Subject.create(observer, observable);
  }


  private create(): WebSocket {
    return new WebSocket(environment.wsUrl);
  }
}
