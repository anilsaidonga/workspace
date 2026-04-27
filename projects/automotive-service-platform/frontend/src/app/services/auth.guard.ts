import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const currentUser = this.authService.currentUserValue;

    if (!currentUser) {
      // Not logged in, redirect to login
      this.router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
      return false;
    }

    // Check role if required
    if (route.data['roles'] && route.data['roles'].length > 0) {
      const hasRole = route.data['roles'].some((role: string) => 
        this.authService.hasRole(role)
      );

      if (!hasRole) {
        // Role not authorized, redirect to home
        this.router.navigate(['/']);
        return false;
      }
    }

    return true;
  }
}
