import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';

export interface AuthResponse {
  jwt: string;
  id: number;
  username: string;
  email: string;
  roles: string[];
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';
  private currentUserSubject: BehaviorSubject<any>;
  public currentUser$: Observable<any>;

  constructor(private http: HttpClient, private router: Router) {
    this.currentUserSubject = new BehaviorSubject<any>(this.getUserFromStorage());
    this.currentUser$ = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): any {
    return this.currentUserSubject.value;
  }

  login(username: string, password: string): Observable<AuthResponse> {
    return this.http
      .post<AuthResponse>(`${this.apiUrl}/signin`, { username, password })
      .pipe(
        tap((response) => {
          this.setUserInStorage(response);
          this.currentUserSubject.next(response);
        })
      );
  }

  logout(): void {
    localStorage.removeItem('jwt_token');
    localStorage.removeItem('user_id');
    localStorage.removeItem('username');
    localStorage.removeItem('email');
    localStorage.removeItem('roles');
    this.currentUserSubject.next(null);
    this.router.navigate(['/login']);
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  getToken(): string | null {
    return localStorage.getItem('jwt_token');
  }

  private setUserInStorage(response: AuthResponse): void {
    localStorage.setItem('jwt_token', response.jwt);
    localStorage.setItem('user_id', response.id.toString());
    localStorage.setItem('username', response.username);
    localStorage.setItem('email', response.email);
    localStorage.setItem('roles', JSON.stringify(response.roles));
  }

  private getUserFromStorage(): any {
    const token = localStorage.getItem('jwt_token');
    if (!token) return null;

    return {
      jwt: token,
      id: localStorage.getItem('user_id'),
      username: localStorage.getItem('username'),
      email: localStorage.getItem('email'),
      roles: JSON.parse(localStorage.getItem('roles') || '[]')
    };
  }

  hasRole(role: string): boolean {
    const user = this.currentUserValue;
    return user && user.roles && user.roles.includes(`ROLE_${role}`);
  }

  getRoles(): string[] {
    const user = this.currentUserValue;
    return user ? user.roles : [];
  }
}
