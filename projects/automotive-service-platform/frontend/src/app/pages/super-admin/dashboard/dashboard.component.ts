import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { Router } from '@angular/router';

@Component({
  selector: 'app-super-admin-dashboard',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatIconModule, MatButtonModule, MatProgressSpinnerModule],
  template: `
    <div class="dashboard-container">
      <mat-card class="header-card">
        <div class="header">
          <div>
            <h1>Super Admin Dashboard</h1>
            <p class="subtitle">System overview and metrics</p>
          </div>
          <button mat-raised-button color="primary" (click)="refreshStats()">
            <mat-icon>refresh</mat-icon>
            Refresh
          </button>
        </div>
      </mat-card>

      <div *ngIf="loading" class="loading-container">
        <mat-spinner diameter="50"></mat-spinner>
        <p>Loading dashboard...</p>
      </div>

      <div *ngIf="!loading" class="stats-grid">
        <!-- Staff Stats -->
        <mat-card class="stat-card admin-card">
          <mat-card-header>
            <mat-icon>supervised_user_circle</mat-icon>
            <mat-card-title>Admins</mat-card-title>
          </mat-card-header>
          <mat-card-content>
            <h2 class="stat-value">{{ stats.totalAdmins || 0 }}</h2>
            <p class="stat-label">Active administrators</p>
          </mat-card-content>
          <mat-card-footer>
            <button mat-stroked-button (click)="goToUserManagement()">Manage</button>
          </mat-card-footer>
        </mat-card>

        <mat-card class="stat-card mechanic-card">
          <mat-card-header>
            <mat-icon>construction</mat-icon>
            <mat-card-title>Mechanics</mat-card-title>
          </mat-card-header>
          <mat-card-content>
            <h2 class="stat-value">{{ stats.totalMechanics || 0 }}</h2>
            <p class="stat-label">Registered mechanics</p>
          </mat-card-content>
          <mat-card-footer>
            <button mat-stroked-button (click)="goToUserManagement()">Manage</button>
          </mat-card-footer>
        </mat-card>

        <mat-card class="stat-card customer-card">
          <mat-card-header>
            <mat-icon>person</mat-icon>
            <mat-card-title>Customers</mat-card-title>
          </mat-card-header>
          <mat-card-content>
            <h2 class="stat-value">{{ stats.totalCustomers || 0 }}</h2>
            <p class="stat-label">Total customers</p>
          </mat-card-content>
          <mat-card-footer>
            <button mat-stroked-button disabled>View</button>
          </mat-card-footer>
        </mat-card>

        <!-- Job & Operations Stats -->
        <mat-card class="stat-card jobs-card">
          <mat-card-header>
            <mat-icon>assignment</mat-icon>
            <mat-card-title>Active Jobs</mat-card-title>
          </mat-card-header>
          <mat-card-content>
            <h2 class="stat-value">{{ stats.activeJobs || 0 }}</h2>
            <p class="stat-label">In progress</p>
          </mat-card-content>
          <mat-card-footer>
            <button mat-stroked-button disabled>View</button>
          </mat-card-footer>
        </mat-card>

        <!-- Service & Inventory -->
        <mat-card class="stat-card service-card">
          <mat-card-header>
            <mat-icon>miscellaneous_services</mat-icon>
            <mat-card-title>Service Types</mat-card-title>
          </mat-card-header>
          <mat-card-content>
            <h2 class="stat-value">{{ stats.totalServices || 0 }}</h2>
            <p class="stat-label">Available services</p>
          </mat-card-content>
          <mat-card-footer>
            <button mat-stroked-button (click)="goToServices()">Manage</button>
          </mat-card-footer>
        </mat-card>

        <mat-card class="stat-card inventory-card">
          <mat-card-header>
            <mat-icon>inventory_2</mat-icon>
            <mat-card-title>Inventory</mat-card-title>
          </mat-card-header>
          <mat-card-content>
            <h2 class="stat-value">{{ stats.totalParts || 0 }}</h2>
            <p class="stat-label">Parts in stock</p>
          </mat-card-content>
          <mat-card-footer>
            <button mat-stroked-button (click)="goToInventory()">Manage</button>
          </mat-card-footer>
        </mat-card>

        <!-- Revenue Summary -->
        <mat-card class="revenue-card">
          <mat-card-header>
            <mat-icon>attach_money</mat-icon>
            <mat-card-title>Total Revenue</mat-card-title>
            <p class="stat-label">All time</p>
          </mat-card-header>
          <mat-card-content>
            <h1 class="revenue-value">{{ stats.totalRevenue | currency:'INR' }}</h1>
          </mat-card-content>
        </mat-card>

        <!-- Appointments -->
        <mat-card class="appointment-card">
          <mat-card-header>
            <mat-icon>calendar_month</mat-icon>
            <mat-card-title>Total Appointments</mat-card-title>
            <p class="stat-label">Scheduled</p>
          </mat-card-header>
          <mat-card-content>
            <h2 class="stat-value">{{ stats.totalAppointments || 0 }}</h2>
          </mat-card-content>
        </mat-card>
      </div>

      <div *ngIf="!loading && error" class="error-container">
        <mat-card>
          <mat-icon>error_outline</mat-icon>
          <p>{{ error }}</p>
          <button mat-raised-button color="primary" (click)="refreshStats()">Retry</button>
        </mat-card>
      </div>
    </div>
  `,
  styles: [`
    .dashboard-container { padding: 24px; max-width: 1400px; margin: 0 auto; }
    
    .header-card { margin-bottom: 24px; padding: 24px; }
    .header { display: flex; justify-content: space-between; align-items: center; gap: 20px; }
    .header > div:first-child { flex: 1; }
    .header h1 { margin: 0 0 8px 0; font-size: 32px; font-weight: 600; line-height: 1.2; }
    .subtitle { margin: 0; color: #999; font-size: 13px; font-weight: 400; }

    .loading-container { 
      display: flex; 
      flex-direction: column; 
      align-items: center; 
      justify-content: center; 
      gap: 16px; 
      padding: 60px 20px;
      text-align: center;
      color: #666;
    }

    .error-container {
      padding: 20px;
      text-align: center;
    }
    .error-container mat-card {
      padding: 40px;
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 16px;
    }
    .error-container mat-icon { font-size: 48px; width: 48px; height: 48px; color: #d32f2f; }

    .stats-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
      gap: 20px;
      margin-bottom: 20px;
    }

    .stat-card {
      padding: 20px;
      cursor: pointer;
      transition: all 0.3s ease;
      border-left: 4px solid #1976d2;
    }
    .stat-card:hover { transform: translateY(-4px); box-shadow: 0 8px 16px rgba(0,0,0,0.12); }
    
    .stat-card.admin-card { border-left-color: #1976d2; }
    .stat-card.mechanic-card { border-left-color: #ff9800; }
    .stat-card.customer-card { border-left-color: #4caf50; }
    .stat-card.jobs-card { border-left-color: #f44336; }
    .stat-card.service-card { border-left-color: #9c27b0; }
    .stat-card.inventory-card { border-left-color: #2196f3; }

    mat-card-header {
      display: flex;
      align-items: center;
      gap: 12px;
      margin-bottom: 16px;
    }
    mat-card-header mat-icon { font-size: 28px; width: 28px; height: 28px; color: #1976d2; }

    mat-card-title { margin: 0; font-size: 16px; }
    .stat-label { margin: 8px 0 0 0; font-size: 13px; color: #999; }
    .stat-value { margin: 12px 0; font-size: 36px; font-weight: 700; color: #1976d2; }

    mat-card-footer { 
      display: flex; 
      justify-content: flex-end; 
      margin-top: 12px; 
      padding-top: 12px; 
      border-top: 1px solid #f0f0f0;
    }

    .revenue-card { 
      grid-column: 1 / -1; 
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      color: white;
      padding: 24px;
    }
    .revenue-card mat-card-header mat-icon { color: rgba(255,255,255,0.9); }
    .revenue-card mat-card-title, .revenue-card .stat-label { color: rgba(255,255,255,0.9); }
    .revenue-value { margin: 16px 0; font-size: 48px; font-weight: 700; color: #fff; }

    .appointment-card { 
      background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
      color: white;
      padding: 24px;
    }
    .appointment-card mat-card-header mat-icon { color: rgba(255,255,255,0.9); }
    .appointment-card mat-card-title, .appointment-card .stat-label { color: rgba(255,255,255,0.9); }
    .appointment-card .stat-value { color: #fff; }

    button:disabled { cursor: not-allowed; opacity: 0.6; }
  `]
})
export class SuperAdminDashboardComponent implements OnInit {
  private http = inject(HttpClient);
  private router = inject(Router);

  stats: any = {
    totalAdmins: 0,
    totalMechanics: 0,
    totalCustomers: 0,
    activeJobs: 0,
    totalServices: 0,
    totalParts: 0,
    totalRevenue: 0,
    totalAppointments: 0
  };
  loading = true;
  error = '';

  ngOnInit() {
    this.loadStats();
  }

  loadStats() {
    this.loading = true;
    this.error = '';

    Promise.all([
      this.http.get<any>('http://localhost:8080/api/admin/users').toPromise(),
      this.http.get<any>('http://localhost:8080/api/admin/parts').toPromise(),
      this.http.get<any>('http://localhost:8080/api/admin/service-types').toPromise(),
      this.http.get<any>('http://localhost:8080/api/appointments').toPromise()
    ]).then(([usersResp, partsResp, servicesResp, appointmentsResp]) => {
      // Count users by role
      const users = usersResp || [];
      this.stats.totalAdmins = users.filter((u: any) => u.role === 'ADMIN').length;
      this.stats.totalMechanics = users.filter((u: any) => u.role === 'MECHANIC').length;
      
      // Set other stats
      this.stats.totalParts = (partsResp || []).length;
      this.stats.totalServices = (servicesResp || []).length;
      this.stats.totalAppointments = (appointmentsResp || []).length;
      
      // Set default values for unavailable metrics
      this.stats.totalCustomers = 0;
      this.stats.activeJobs = 0;
      this.stats.totalRevenue = 0;

      this.loading = false;
    }).catch((err) => {
      console.error('Failed to load dashboard stats', err);
      this.error = 'Failed to load dashboard statistics. Please check your backend connection.';
      this.loading = false;
      // Set default values even on error
      this.stats.totalAdmins = 0;
      this.stats.totalMechanics = 0;
      this.stats.totalCustomers = 0;
      this.stats.activeJobs = 0;
      this.stats.totalServices = 0;
      this.stats.totalParts = 0;
      this.stats.totalRevenue = 0;
      this.stats.totalAppointments = 0;
    });
  }

  refreshStats() {
    this.loadStats();
  }

  goToUserManagement() {
    this.router.navigate(['/super-admin/users']);
  }

  goToServices() {
    this.router.navigate(['/super-admin/services']);
  }

  goToInventory() {
    this.router.navigate(['/super-admin/inventory']);
  }
}
