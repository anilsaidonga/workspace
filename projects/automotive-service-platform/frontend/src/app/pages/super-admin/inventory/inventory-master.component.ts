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

@Component({
  selector: 'app-part-dialog',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatDialogModule],
  template: `
    <h2 mat-dialog-title>{{ isEdit ? 'Edit Part' : 'Add New Part' }}</h2>
    <mat-dialog-content>
      <form [formGroup]="partForm" class="dialog-form">
        <mat-form-field appearance="fill">
          <mat-label>Part Name</mat-label>
          <input matInput formControlName="name" required [readonly]="isEdit">
          <mat-error *ngIf="partForm.get('name')?.hasError('required')">Name is required</mat-error>
        </mat-form-field>

        <mat-form-field appearance="fill">
          <mat-label>Quantity</mat-label>
          <input matInput formControlName="quantity" type="number" min="0" required>
          <mat-error *ngIf="partForm.get('quantity')?.hasError('required')">Quantity is required</mat-error>
        </mat-form-field>
      </form>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button mat-button mat-dialog-close>Cancel</button>
      <button mat-raised-button color="primary" [disabled]="partForm.invalid || loading" (click)="savePart()">
        {{ loading ? 'Saving...' : 'Save' }}
      </button>
    </mat-dialog-actions>
  `,
  styles: [`
    .dialog-form { display: flex; flex-direction: column; gap: 15px; min-width: 350px; }
    mat-form-field { width: 100%; }
  `]
})
export class PartDialogComponent {
  private fb = inject(FormBuilder);
  private http = inject(HttpClient);
  private dialogRef = inject(MatDialogRef<PartDialogComponent>);
  private data = inject(MAT_DIALOG_DATA);

  isEdit = false;
  loading = false;

  partForm = this.fb.group({
    name: ['', Validators.required],
    quantity: [0, [Validators.required, Validators.min(0)]]
  });

  constructor() {
    if (this.data) {
      this.isEdit = true;
      this.partForm.patchValue(this.data);
      this.partForm.get('name')?.disable();
    }
  }

  savePart() {
    if (this.partForm.valid) {
      this.loading = true;
      const formValue = this.partForm.getRawValue();

      if (this.isEdit) {
        this.http.put(`http://localhost:8080/api/admin/parts/${this.data.id}`, formValue).subscribe({
          next: () => {
            this.loading = false;
            this.dialogRef.close(true);
          },
          error: (err) => {
            this.loading = false;
            alert('Error: ' + (err.error || 'Failed to update part'));
          }
        });
      } else {
        this.http.post('http://localhost:8080/api/admin/parts', formValue).subscribe({
          next: () => {
            this.loading = false;
            this.dialogRef.close(true);
          },
          error: (err) => {
            this.loading = false;
            alert('Error: ' + (err.error || 'Failed to create part'));
          }
        });
      }
    }
  }
}

@Component({
  selector: 'app-inventory-master',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatButtonModule, MatIconModule, MatDialogModule, MatCardModule, MatProgressSpinnerModule, MatTooltipModule],
  template: `
    <div class="inventory-container">
      <mat-card class="header-card">
        <div class="header">
          <div>
            <h1>Inventory Master</h1>
            <p class="subtitle">Manage parts and stock levels</p>
          </div>
          <button mat-raised-button color="primary" (click)="openPartDialog()" [disabled]="loading">
            <mat-icon>add_circle</mat-icon>
            Add Part
          </button>
        </div>
      </mat-card>

      <mat-card *ngIf="loading && parts.length === 0" class="loading-card">
        <mat-spinner diameter="40"></mat-spinner>
        <p>Loading inventory...</p>
      </mat-card>

      <mat-card *ngIf="!loading && parts.length === 0" class="empty-card">
        <mat-icon>inventory_2</mat-icon>
        <p>No parts in inventory yet. Click "Add Part" to get started.</p>
      </mat-card>

      <mat-card *ngIf="parts.length > 0" class="table-card">
        <table mat-table [dataSource]="parts" class="parts-table">
          
          <ng-container matColumnDef="name">
            <th mat-header-cell *matHeaderCellDef>Part Name</th>
            <td mat-cell *matCellDef="let part">
              <strong>{{ part.name }}</strong>
            </td>
          </ng-container>

          <ng-container matColumnDef="quantity">
            <th mat-header-cell *matHeaderCellDef>Quantity in Stock</th>
            <td mat-cell *matCellDef="let part">
              <span class="quantity-badge" [ngClass]="part.quantity <= 5 ? 'low-stock' : 'normal-stock'">
                {{ part.quantity }} units
              </span>
            </td>
          </ng-container>

          <ng-container matColumnDef="actions">
            <th mat-header-cell *matHeaderCellDef>Actions</th>
            <td mat-cell *matCellDef="let part">
              <button mat-icon-button matTooltip="Edit" (click)="editPart(part)">
                <mat-icon>edit</mat-icon>
              </button>
              <button mat-icon-button matTooltip="Delete" color="warn" (click)="deletePart(part.id)">
                <mat-icon>delete</mat-icon>
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
    .inventory-container { padding: 20px; max-width: 1200px; margin: 0 auto; }
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
    .parts-table { width: 100%; }

    .quantity-badge {
      display: inline-block;
      padding: 6px 12px;
      border-radius: 20px;
      font-weight: 600;
      font-size: 14px;
    }
    .quantity-badge.normal-stock { background-color: #e8f5e9; color: #2e7d32; }
    .quantity-badge.low-stock { background-color: #ffebee; color: #c62828; }

    th { background-color: #f5f5f5; font-weight: 600; }
    td { padding: 12px 16px; }
    tr:hover { background-color: #fafafa; }
  `]
})
export class InventoryMasterComponent implements OnInit {
  private http = inject(HttpClient);
  private dialog = inject(MatDialog);

  parts: any[] = [];
  displayedColumns: string[] = ['name', 'quantity', 'actions'];
  loading = true;

  ngOnInit() {
    this.loadParts();
  }

  loadParts() {
    this.loading = true;
    this.http.get<any[]>('http://localhost:8080/api/admin/parts').subscribe({
      next: (data) => {
        this.parts = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Failed to load parts', err);
        this.loading = false;
        alert('Failed to load inventory');
      }
    });
  }

  openPartDialog() {
    const dialogRef = this.dialog.open(PartDialogComponent);
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadParts();
      }
    });
  }

  editPart(part: any) {
    const dialogRef = this.dialog.open(PartDialogComponent, { data: part });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadParts();
      }
    });
  }

  deletePart(id: number) {
    if (confirm('Are you sure you want to delete this part?')) {
      this.http.delete(`http://localhost:8080/api/admin/parts/${id}`).subscribe({
        next: () => {
          this.loadParts();
          alert('Part deleted successfully');
        },
        error: (err) => {
          alert('Error: ' + (err.error || 'Failed to delete part'));
        }
      });
    }
  }
}
