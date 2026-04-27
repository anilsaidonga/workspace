import { Component, OnInit, inject, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog, MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTooltipModule } from '@angular/material/tooltip';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

const serviceDialogTemplate = `
    <h2 mat-dialog-title>{{ isEdit ? 'Edit Service Type' : 'Add New Service Type' }}</h2>
    <mat-dialog-content>
      <form [formGroup]="serviceForm" class="dialog-form">
        <mat-form-field appearance="fill">
          <mat-label>Service Name</mat-label>
          <input matInput formControlName="name" required [readonly]="isEdit">
          <mat-error *ngIf="serviceForm.get('name')?.hasError('required')">Name is required</mat-error>
        </mat-form-field>
        <mat-form-field appearance="fill">
          <mat-label>Base Price ($)</mat-label>
          <input matInput formControlName="basePrice" type="number" step="0.01" min="0" required>
          <mat-error *ngIf="serviceForm.get('basePrice')?.hasError('required')">Price is required</mat-error>
          <mat-error *ngIf="serviceForm.get('basePrice')?.hasError('min')">Price must be positive</mat-error>
        </mat-form-field>
      </form>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button mat-button mat-dialog-close>Cancel</button>
      <button mat-raised-button color="primary" [disabled]="serviceForm.invalid || loading" (click)="saveService()">
        {{ loading ? 'Saving...' : 'Save' }}
      </button>
    </mat-dialog-actions>
`;

@Component({
  selector: 'app-service-dialog',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatDialogModule],
  template: serviceDialogTemplate,
  styles: [`
    .dialog-form { display: flex; flex-direction: column; gap: 15px; min-width: 350px; }
    mat-form-field { width: 100%; }
  `]
})
export class ServiceDialogComponent {
  private fb = inject(FormBuilder);
  private http = inject(HttpClient);
  private dialogRef = inject(MatDialogRef<ServiceDialogComponent>);
  private data = inject(MAT_DIALOG_DATA);

  isEdit = false;
  loading = false;

  serviceForm = this.fb.group({
    name: ['', Validators.required],
    basePrice: [0, [Validators.required, Validators.min(0)]]
  });

  constructor() {
    if (this.data) {
      this.isEdit = true;
      this.serviceForm.patchValue(this.data);
      this.serviceForm.get('name')?.disable();
    }
  }

  saveService() {
    if (this.serviceForm.valid) {
      this.loading = true;
      const formValue = this.serviceForm.getRawValue();

      if (this.isEdit) {
        this.http.put(`http://localhost:8080/api/admin/service-types/${this.data.id}`, formValue).subscribe({
          next: () => {
            this.loading = false;
            this.dialogRef.close(true);
          },
          error: (err) => {
            this.loading = false;
            alert('Error: ' + (err.error || 'Failed to update service type'));
          }
        });
      } else {
        this.http.post('http://localhost:8080/api/admin/service-types', formValue).subscribe({
          next: () => {
            this.loading = false;
            this.dialogRef.close(true);
          },
          error: (err) => {
            this.loading = false;
            alert('Error: ' + (err.error || 'Failed to create service type'));
          }
        });
      }
    }
  }
}

const catalogTemplate = `
    <div class="service-catalog-container">
      <mat-card class="header-card">
        <div class="header">
          <div>
            <h1>Service Types Management</h1>
            <p class="subtitle">Define service categories and base pricing</p>
          </div>
          <button mat-raised-button color="primary" (click)="openServiceDialog()" [disabled]="loading">
            <mat-icon>add_circle</mat-icon>
            Add Service Type
          </button>
        </div>
      </mat-card>

      <mat-card *ngIf="loading && services.length === 0" class="loading-card">
        <mat-spinner diameter="40"></mat-spinner>
        <p>Loading services...</p>
      </mat-card>

      <mat-card *ngIf="!loading && services.length === 0" class="empty-card">
        <mat-icon>miscellaneous_services</mat-icon>
        <p>No service types created yet. Click "Add Service Type" to get started.</p>
      </mat-card>

      <mat-card *ngIf="services.length > 0" class="table-card">
        <table mat-table [dataSource]="services" class="services-table">

          <ng-container matColumnDef="name">
            <th mat-header-cell *matHeaderCellDef>Service Name</th>
            <td mat-cell *matCellDef="let svc">
              <strong>{{ svc.name }}</strong>
            </td>
          </ng-container>

          <ng-container matColumnDef="basePrice">
            <th mat-header-cell *matHeaderCellDef>Base Price</th>
            <td mat-cell *matCellDef="let svc">
              <span class="price-badge">\${{ svc.basePrice }}</span>
            </td>
          </ng-container>

          <ng-container matColumnDef="actions">
            <th mat-header-cell *matHeaderCellDef>Actions</th>
            <td mat-cell *matCellDef="let svc">
              <button mat-icon-button matTooltip="Edit" (click)="editService(svc)">
                <mat-icon>edit</mat-icon>
              </button>
              <button mat-icon-button matTooltip="Delete" color="warn" (click)="deleteService(svc.id)">
                <mat-icon>delete</mat-icon>
              </button>
            </td>
          </ng-container>

          <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
          <tr mat-row *matRowDef="let row; columns: displayedColumns"></tr>
        </table>
      </mat-card>
    </div>
`;

@Component({
  selector: 'app-service-catalog',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatButtonModule, MatIconModule, MatDialogModule, MatCardModule, MatProgressSpinnerModule, MatTooltipModule],
  template: catalogTemplate,
  styles: [`
    .service-catalog-container { padding: 20px; max-width: 1200px; margin: 0 auto; }
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
    .services-table { width: 100%; }
    .price-badge {
      display: inline-block;
      padding: 6px 12px;
      background-color: #e8f5e9;
      color: #2e7d32;
      border-radius: 20px;
      font-weight: 600;
      font-size: 14px;
    }
    th { background-color: #f5f5f5; font-weight: 600; }
    td { padding: 12px 16px; }
    tr:hover { background-color: #fafafa; }
  `]
})
export class ServiceCatalogComponent implements OnInit {
  private http = inject(HttpClient);
  private dialog = inject(MatDialog);

  services: any[] = [];
  displayedColumns: string[] = ['name', 'basePrice', 'actions'];
  loading = true;

  ngOnInit() {
    this.loadServices();
  }

  loadServices() {
    this.loading = true;
    this.http.get<any[]>('http://localhost:8080/api/admin/service-types').subscribe({
      next: (data) => {
        this.services = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Failed to load services', err);
        this.loading = false;
        alert('Failed to load service types');
      }
    });
  }

  openServiceDialog() {
    const dialogRef = this.dialog.open(ServiceDialogComponent);
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadServices();
      }
    });
  }

  editService(service: any) {
    const dialogRef = this.dialog.open(ServiceDialogComponent, { data: service });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadServices();
      }
    });
  }

  deleteService(id: number) {
    if (confirm('Are you sure you want to delete this service type?')) {
      this.http.delete(`http://localhost:8080/api/admin/service-types/${id}`).subscribe({
        next: () => {
          this.loadServices();
          alert('Service type deleted successfully');
        },
        error: (err) => {
          alert('Error: ' + (err.error || 'Failed to delete service type'));
        }
      });
    }
  }
}
