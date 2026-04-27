import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatIconModule,
    MatSnackBarModule
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);
  private snackBar = inject(MatSnackBar);

  staffLoginForm = this.fb.group({
    username: ['', [Validators.required]],
    password: ['', [Validators.required]],
  });

  loading = false;
  hidePassword = true;
  loginError = '';

  staffLogin() {
    if (this.staffLoginForm.valid) {
      this.loading = true;
      this.loginError = '';

      const { username, password } = this.staffLoginForm.value;

      this.authService.login(username!, password!).subscribe({
        next: (res) => {
          this.loading = false;
          this.snackBar.open('✅ Login successful!', 'Close', { 
            duration: 2000,
            panelClass: ['success-snackbar']
          });

          setTimeout(() => {
            if (res.roles.includes('ROLE_SUPER_ADMIN')) {
              this.router.navigate(['/super-admin/dashboard']);
            } else if (res.roles.includes('ROLE_ADMIN')) {
              this.router.navigate(['/admin/dashboard']);
            } else if (res.roles.includes('ROLE_MECHANIC')) {
              this.router.navigate(['/mechanic/dashboard']);
            } else if (res.roles.includes('ROLE_CUSTOMER')) {
              this.router.navigate(['/customer/dashboard']);
            }
          }, 500);
        },
        error: (err) => {
          this.loading = false;
          const errorMsg = err.error?.message || err.statusText || 'Login failed. Please check credentials.';
          this.loginError = errorMsg;
          this.snackBar.open('❌ ' + errorMsg, 'Close', { 
            duration: 4000,
            panelClass: ['error-snackbar']
          });
          console.error('Login Error:', err);
        }
      });
    }
  }

  togglePasswordVisibility() {
    this.hidePassword = !this.hidePassword;
  }
}
