import { Component, OnInit, inject, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog, MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-user-dialog',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatSelectModule, MatButtonModule, MatDialogModule],
  template: `
    <h2 mat-dialog-title>{{ isEdit ? 'Edit Staff' : 'Add New Staff' }}</h2>
    <mat-dialog-content>
      <form [formGroup]="userForm" class="dialog-form">
        <mat-form-field appearance="fill">
          <mat-label>Name</mat-label>
          <input matInput formControlName="name" required>
          <mat-error *ngIf="userForm.get('name')?.hasError('required')">Name is required</mat-error>
        </mat-form-field>

        <mat-form-field appearance="fill" *ngIf="!isEdit">
          <mat-label>Phone Number</mat-label>
          <input matInput formControlName="phoneNumber" required>
          <mat-error *ngIf="userForm.get('phoneNumber')?.hasError('required')">Phone is required</mat-error>
        </mat-form-field>

        <mat-form-field appearance="fill">
          <mat-label>Email</mat-label>
          <input matInput formControlName="email" type="email">
          <mat-error *ngIf="userForm.get('email')?.hasError('email')">Invalid email</mat-error>
        </mat-form-field>

        <mat-form-field appearance="fill" *ngIf="!isEdit">
          <mat-label>Role</mat-label>
          <mat-select formControlName="role" required>
            <mat-option value="ADMIN">Admin (Service Advisor)</mat-option>
            <mat-option value="MECHANIC">Mechanic</mat-option>
          </mat-select>
          <mat-error *ngIf="userForm.get('role')?.hasError('required')">Role is required</mat-error>
        </mat-form-field>

        <mat-form-field appearance="fill" [ngClass]="{'half-width': isEdit}">
          <mat-label>{{ isEdit ? 'New Password (leave blank to keep current)' : 'Password' }}</mat-label>
          <input matInput formControlName="password" type="password" [required]="!isEdit">
          <mat-error *ngIf="!isEdit && userForm.get('password')?.hasError('required')">Password is required</mat-error>
        </mat-form-field>
      </form>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button mat-button mat-dialog-close>Cancel</button>
      <button mat-raised-button color="primary" [disabled]="userForm.invalid || loading" (click)="saveUser()">
        {{ loading ? 'Saving...' : 'Save' }}
      </button>
    </mat-dialog-actions>
  `,
  styles: [`
    .dialog-form { display: flex; flex-direction: column; gap: 15px; min-width: 350px; }
    mat-form-field { width: 100%; }
    .half-width { width: 48%; }
  `]
})
export class UserDialogComponent {
  private fb = inject(FormBuilder);
  private http = inject(HttpClient);
  private dialogRef = inject(MatDialogRef<UserDialogComponent>);
  private data = inject(MAT_DIALOG_DATA);

  isEdit = false;
  loading = false;

  userForm = this.fb.group({
    name: ['', Validators.required],
    phoneNumber: ['', Validators.required],
    email: ['', [Validators.email]],
    role: ['', Validators.required],
    password: ['', Validators.required]
  });

  constructor() {
    if (this.data) {
      this.isEdit = true;
      this.userForm.patchValue(this.data);
      this.userForm.get('phoneNumber')?.disable();
      this.userForm.get('role')?.disable();
      this.userForm.get('password')?.setValidators([]); // password optional in edit mode
      this.userForm.get('password')?.updateValueAndValidity();
    }
  }

  saveUser() {
    if (this.userForm.valid) {
      this.loading = true;
      const formValue = this.userForm.getRawValue();
      
      if (this.isEdit) {
        this.http.put(`http://localhost:8080/api/admin/users/${this.data.id}`, formValue).subscribe({
          next: () => {
            this.loading = false;
            this.dialogRef.close(true);
          },
          error: (err) => {
            this.loading = false;
            alert('Error: ' + (err.error || 'Failed to update user'));
          }
        });
      } else {
        this.http.post('http://localhost:8080/api/admin/users', formValue).subscribe({
          next: () => {
            this.loading = false;
            this.dialogRef.close(true);
          },
          error: (err) => {
            this.loading = false;
            alert('Error: ' + (err.error || 'Failed to create user'));
          }
        });
      }
    }
  }
}

@Component({
  selector: 'app-user-management',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatButtonModule, MatIconModule, MatDialogModule, MatCardModule, MatProgressSpinnerModule, MatTooltipModule],
  template: `
    <div class="user-management-container">
      <mat-card class="header-card">
        <div class="header">
          <div>
            <h1>Staff Management</h1>
            <p class="subtitle">Create and manage admin and mechanic accounts</p>
          </div>
          <button mat-raised-button color="primary" (click)="openUserDialog()" [disabled]="loading">
            <mat-icon>person_add</mat-icon>
            Add New Staff
          </button>
        </div>
      </mat-card>

      <mat-card *ngIf="loading && users.length === 0" class="loading-card">
        <mat-spinner diameter="40"></mat-spinner>
        <p>Loading staff...</p>
      </mat-card>

      <mat-card *ngIf="!loading && users.length === 0" class="empty-card">
        <mat-icon>person_outline</mat-icon>
        <p>No staff members created yet. Click "Add New Staff" to get started.</p>
      </mat-card>

      <mat-card *ngIf="users.length > 0" class="table-card">
        <table mat-table [dataSource]="users" class="staff-table">
          
          <ng-container matColumnDef="name">
            <th mat-header-cell *matHeaderCellDef>Name</th>
            <td mat-cell *matCellDef="let user">
              <strong>{{user.name}}</strong>
            </td>
          </ng-container>

          <ng-container matColumnDef="phone">
            <th mat-header-cell *matHeaderCellDef>Phone</th>
            <td mat-cell *matCellDef="let user">{{user.phoneNumber}}</td>
          </ng-container>

          <ng-container matColumnDef="email">
            <th mat-header-cell *matHeaderCellDef>Email</th>
            <td mat-cell *matCellDef="let user">{{user.email || '—'}}</td>
          </ng-container>

          <ng-container matColumnDef="role">
            <th mat-header-cell *matHeaderCellDef>Role</th>
            <td mat-cell *matCellDef="let user">
              <span class="role-badge" [ngClass]="'role-' + user.role.toLowerCase()">
                {{user.role === 'ADMIN' ? 'Service Advisor' : 'Mechanic'}}
              </span>
            </td>
          </ng-container>

          <ng-container matColumnDef="status">
            <th mat-header-cell *matHeaderCellDef>Status</th>
            <td mat-cell *matCellDef="let user">
              <span class="status-badge" [ngClass]="'status-' + user.status.toLowerCase()">
                {{user.status}}
              </span>
            </td>
          </ng-container>

          <ng-container matColumnDef="actions">
            <th mat-header-cell *matHeaderCellDef>Actions</th>
            <td mat-cell *matCellDef="let user">
              <button mat-icon-button matTooltip="Edit" (click)="editUser(user)">
                <mat-icon>edit</mat-icon>
              </button>
              <button mat-icon-button matTooltip="Deactivate" color="warn" (click)="deactivateUser(user.id)">
                <mat-icon>person_off</mat-icon>
              </button>
            </td>
          </ng-container>

          <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
          <tr mat-row *matRowDef="let row; columns: displayedColumns;"></tr>
        </table>
      </mat-card>
    </div>
  `,
  styles: [`
    .user-management-container { padding: 20px; max-width: 1200px; margin: 0 auto; }
    .header-card { margin-bottom: 20px; padding: 24px; }
    .header { display: flex; justify-content: space-between; align-items: center; gap: 20px; }
    .header > div:first-child { flex: 1; }
    .header h1 { margin: 0 0 8px 0; font-size: 28px; font-weight: 600; line-height: 1.2; }
    .subtitle { margin: 0; color: #999; font-size: 13px; font-weight: 400; }
    .header button { gap: 8px; flex-shrink: 0; }
    
    .loading-card, .empty-card { padding: 40px; text-align: center; }
    .loading-card { display: flex; flex-direction: column; align-items: center; gap: 15px; }
    .empty-card mat-icon { font-size: 48px; width: 48px; height: 48px; color: #999; }
    .empty-card p { color: #666; }

    .table-card { margin-bottom: 20px; overflow-x: auto; }
    .staff-table { width: 100%; }
    
    .role-badge {
      display: inline-block;
      padding: 4px 12px;
      border-radius: 20px;
      font-size: 12px;
      font-weight: 500;
    }
    .role-admin { background-color: #e3f2fd; color: #1976d2; }
    .role-mechanic { background-color: #f3e5f5; color: #7b1fa2; }

    .status-badge {
      display: inline-block;
      padding: 4px 12px;
      border-radius: 20px;
      font-size: 12px;
      font-weight: 500;
    }
    .status-verified { background-color: #c8e6c9; color: #2e7d32; }
    .status-provisional { background-color: #fff9c4; color: #f57f17; }

    th { background-color: #f5f5f5; font-weight: 600; }
    td { padding: 12px 16px; }
    tr:hover { background-color: #fafafa; }
  `]
})
export class UserManagementComponent implements OnInit {
  private http = inject(HttpClient);
  private dialog = inject(MatDialog);

  users: any[] = [];
  displayedColumns: string[] = ['name', 'phone', 'email', 'role', 'status', 'actions'];
  loading = true;

  ngOnInit() {
    this.loadUsers();
  }

  loadUsers() {
    this.loading = true;
    this.http.get<any[]>('http://localhost:8080/api/admin/users').subscribe({
      next: (data) => {
        this.users = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Failed to load users', err);
        this.loading = false;
        alert('Failed to load staff members');
      }
    });
  }

  openUserDialog() {
    const dialogRef = this.dialog.open(UserDialogComponent);
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadUsers();
      }
    });
  }

  editUser(user: any) {
    const dialogRef = this.dialog.open(UserDialogComponent, { data: user });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadUsers();
      }
    });
  }

  deactivateUser(id: number) {
    if (confirm('Are you sure you want to deactivate this staff member? They will not be able to login.')) {
      this.http.delete(`http://localhost:8080/api/admin/users/${id}`).subscribe({
        next: () => {
          this.loadUsers();
          alert('Staff member deactivated successfully');
        },
        error: (err) => {
          alert('Error: ' + (err.error || 'Failed to deactivate user'));
        }
      });
    }
  }
}
