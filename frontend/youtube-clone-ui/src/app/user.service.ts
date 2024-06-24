import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {catchError, map, Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userId!: string;
  constructor(private httpClient: HttpClient) { }

  getUserId(): string {
    return this.userId;
  }

  subscribeToUser(userId: string): Observable<boolean>{
    return this.httpClient.post<boolean>("http://localhost:8080/api/user/subscribe/"+userId, null);
  }

  registerUser(): Observable<string> {
    return this.httpClient.get("http://localhost:8080/api/user/register", {
      responseType: 'text',
      observe: 'response'
    }).pipe(
      map(response => {
        if (response.ok) {
          const userId = response.body || '';
          this.userId = userId;
          return userId;
        } else {
          throw new Error('Error during user registration');
        }
      }),
      catchError(error => {
        console.error('Error during user registration', error);
        return throwError(() => new Error('Error during user registration'));
      })
    );
  }

  unSubscribeUser(userId: string) {
    return this.httpClient.post<boolean>("http://localhost:8080/api/user/unsubscribe/"+userId, null);
  }
}
