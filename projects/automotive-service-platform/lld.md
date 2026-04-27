# 🔧 Automotive Service Platform – Low-Level Design (LLD)

> **From HLD to Code:** This document details the software architecture, data models, APIs, and component interactions for the automotive service platform.

---

## 1. SYSTEM ARCHITECTURE

### 1.1 High-Level Layers

```
┌─────────────────────────────────────────────────┐
│          Frontend (Angular 21)                   │
│  ├─ Authentication Pages (Login, OTP)           │
│  ├─ Customer Portal (Booking, Job Status)       │
│  ├─ Admin Panel (Job Management, Reporting)     │
│  ├─ Mechanic Dashboard (Job Execution)          │
│  └─ Inventory Management                         │
├─────────────────────────────────────────────────┤
│          HTTP REST API Layer                      │
│  ├─ AuthController                              │
│  ├─ AppointmentController                       │
│  ├─ JobCardController                           │
│  ├─ EstimateController                          │
│  ├─ VehicleController                           │
│  └─ Other Resource Controllers                  │
├─────────────────────────────────────────────────┤
│          Service Layer (Business Logic)          │
│  ├─ OtpService (sends OTP)                      │
│  ├─ AuthService (JWT generation)                │
│  ├─ AppointmentService                          │
│  ├─ JobCardService                              │
│  ├─ EstimateService                             │
│  └─ UserService                                 │
├─────────────────────────────────────────────────┤
│          Data Access Layer (Repositories)        │
│  ├─ JpaRepository implementations                │
│  └─ Custom query methods                        │
├─────────────────────────────────────────────────┤
│          Database (H2 / PostgreSQL)              │
│  └─ 11 Core Tables + Indexes                   │
└─────────────────────────────────────────────────┘
```

### 1.2 Authentication Flow

```
Customer OTP Login:
1. Customer enters phone number → POST /api/auth/send-otp
2. OtpService generates 6-digit OTP (5-min expiry)
3. OTP sent via SMS/Email (currently console logs)
4. Customer enters OTP → POST /api/auth/verify-otp
5. UserService checks: existing user? YES: load | NO: create PROVISIONAL account
6. JwtUtils generates 24-hour JWT token
7. Frontend stores token in localStorage
8. Frontend includes "Authorization: Bearer {token}" in all requests

Staff Password Login:
1. Staff enters phone + password → POST /api/auth/signin
2. UserDetailsServiceImpl loads user from DB
3. SecurityConfig validates BCrypt password
4. JwtUtils generates token (same as above)
```

### 1.3 Request-Response Pattern

```
Request Flow:
Browser/App → AuthTokenFilter → Extract JWT from header
          ↓
      Validate signature + expiry
          ↓
      Load UserDetails from database
          ↓
      Set SecurityContextHolder
          ↓
      Route to @PreAuthorize-checked endpoint
          ↓
      Business logic executes

Response Flow:
Controller method → Service layer → Repository layer → DB
          ↑
    Return DTO
          ↑
    Spring serializes to JSON
          ↑
    Client receives
```

---

## 2. DATA MODELS

### 2.1 Entity Relationship Diagram (ERD)

```
User (PK: id)
├─ 1-N Vehicle
├─ 1-N Appointment
├─ 1-N JobCard
├─ 1-N Payment
└─ Roles: SUPER_ADMIN, ADMIN, MECHANIC, CUSTOMER

Vehicle (PK: id, UNQ: vehicleNumber)
├─ N-1 User (owner)
├─ 0-1 Tow (active tow)
├─ 1-N Appointment
├─ 1-N JobCard
└─ Condition: DRIVABLE, NON_DRIVABLE, UNKNOWN, TOWED_NO_OWNER

Appointment (PK: id)
├─ N-1 User (customer)
├─ N-1 Vehicle
├─ Type: SELF_DRIVE, TOW_REQUIRED, DIAGNOSIS_ONLY
└─ Status: CONFIRMED, CHECKED_IN, NO_SHOW, CANCELLED

JobCard (PK: id)
├─ N-1 User (customer)
├─ N-1 Vehicle
├─ 1-1 Estimate (eager fetch)
├─ 1-1 Tow (optional)
├─ Type: WORKSHOP, DOORSTEP
└─ Status: PENDING, IN_PROGRESS, WAITING_FOR_PARTS, QC_PASSED, COMPLETED, ON_HOLD, ABANDONED

Estimate (PK: id)
├─ 1-1 JobCard
├─ 1-N LabourItem
├─ 1-N PartItem
└─ Fields: taxes, totalAmount, approved flag

LabourItem (PK: id)
├─ N-1 Estimate
└─ Fields: description, cost

PartItem (PK: id)
├─ N-1 Estimate
├─ N-1 Part (eager fetch)
└─ Fields: quantity, cost

Part (PK: id, UNQ: name)
└─ Fields: name, quantity (inventory)

Payment (PK: id)
├─ N-1 JobCard
├─ Mode: CASH, ONLINE
└─ Status: PAID, PARTIAL, PENDING

Tow (PK: id)
├─ N-1 Vehicle
├─ 1-1 JobCard
├─ Status: SCHEDULED, PICKED, COMPLETED, CANCELLED
└─ Field: pickupLocation

ServiceType (PK: id, UNQ: name)
└─ Fields: name, basePrice (master data)
```

### 2.2 Database Constraints

```sql
-- User Table
PRIMARY KEY (id)
UNIQUE (phoneNumber)
UNIQUE (email)
CHECK (role IN ('SUPER_ADMIN', 'ADMIN', 'MECHANIC', 'CUSTOMER'))
CHECK (status IN ('PROVISIONAL', 'VERIFIED'))

-- Vehicle Table
PRIMARY KEY (id)
UNIQUE (vehicleNumber)
FOREIGN KEY (user_id) REFERENCES User(id)
FOREIGN KEY (tow_id) REFERENCES Tow(id)
CHECK (type IN ('BIKE', 'CAR'))
CHECK (fuelType IN ('PETROL', 'DIESEL', 'ELECTRIC', 'CNG'))
CHECK (condition IN ('DRIVABLE', 'NON_DRIVABLE', 'UNKNOWN', 'TOWED_NO_OWNER'))

-- Appointment Table
PRIMARY KEY (id)
FOREIGN KEY (user_id) REFERENCES User(id)
FOREIGN KEY (vehicle_id) REFERENCES Vehicle(id)
CHECK (type IN ('SELF_DRIVE', 'TOW_REQUIRED', 'DIAGNOSIS_ONLY'))
CHECK (status IN ('CONFIRMED', 'CHECKED_IN', 'NO_SHOW', 'CANCELLED'))

-- JobCard Table
PRIMARY KEY (id)
FOREIGN KEY (user_id) REFERENCES User(id)
FOREIGN KEY (vehicle_id) REFERENCES Vehicle(id)
FOREIGN KEY (estimate_id) REFERENCES Estimate(id)
FOREIGN KEY (tow_id) REFERENCES Tow(id)
CHECK (type IN ('WORKSHOP', 'DOORSTEP'))
CHECK (status IN ('PENDING', 'IN_PROGRESS', 'WAITING_FOR_PARTS', 
                  'QC_PASSED', 'COMPLETED', 'ON_HOLD', 'ABANDONED'))

-- Estimate Table
PRIMARY KEY (id)
FOREIGN KEY (job_card_id) REFERENCES JobCard(id)

-- LabourItem Table
PRIMARY KEY (id)
FOREIGN KEY (estimate_id) REFERENCES Estimate(id)

-- PartItem Table
PRIMARY KEY (id)
FOREIGN KEY (estimate_id) REFERENCES Estimate(id)
FOREIGN KEY (part_id) REFERENCES Part(id)

-- Part Table
PRIMARY KEY (id)
UNIQUE (name)

-- Payment Table
PRIMARY KEY (id)
FOREIGN KEY (job_card_id) REFERENCES JobCard(id)
CHECK (mode IN ('CASH', 'ONLINE'))
CHECK (status IN ('PAID', 'PARTIAL', 'PENDING'))

-- Tow Table
PRIMARY KEY (id)
FOREIGN KEY (vehicle_id) REFERENCES Vehicle(id)
FOREIGN KEY (job_card_id) REFERENCES JobCard(id)
CHECK (status IN ('SCHEDULED', 'PICKED', 'COMPLETED', 'CANCELLED'))

-- ServiceType Table
PRIMARY KEY (id)
UNIQUE (name)
```

---

## 3. API SPECIFICATION

### 3.1 Authentication Endpoints

#### POST /api/auth/send-otp
**Purpose:** Generate OTP for customer login/signup  
**Access:** Public  
**Request:**
```json
{
  "phoneNumber": "9876543210"
}
```
**Response:** 
```json
{
  "message": "OTP sent to 9876543210"
}
```
**Business Logic:**
1. Validate phone number format
2. Generate 6-digit OTP
3. Store in-memory in OtpService (5-min expiry)
4. Send via SMS/Email (currently console logs)

---

#### POST /api/auth/verify-otp
**Purpose:** Verify OTP and authenticate customer  
**Access:** Public  
**Request:**
```json
{
  "phoneNumber": "9876543210",
  "otp": "123456"
}
```
**Response:** (Success)
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "id": 1,
  "username": "9876543210",
  "email": null,
  "roles": ["ROLE_CUSTOMER"]
}
```
**Response:** (Invalid OTP)
```json
{
  "message": "Invalid or expired OTP"
}
```
**Business Logic:**
1. Validate OTP from in-memory store
2. Check: User exists?
   - YES: Load user (verify phone matches)
   - NO: Create new PROVISIONAL user
3. Generate JWT (24-hour expire)
4. Remove OTP (single-use)

---

#### POST /api/auth/signin
**Purpose:** Staff password-based login  
**Access:** Public  
**Request:**
```json
{
  "username": "9876543210",  // staff phone number
  "password": "secure_password"
}
```
**Response:** Same as verify-otp  
**Authorization:** SUPER_ADMIN, ADMIN, MECHANIC only (CUSTOMER cannot use this)  
**Business Logic:**
1. Load user by phone number (username)
2. Validate BCrypt password
3. Check role ≠ CUSTOMER
4. Generate JWT

---

### 3.2 Vehicle Management Endpoints

#### POST /api/vehicles
**Purpose:** Add vehicle to customer's profile  
**Access:** Authenticated customers only  
**Request:**
```json
{
  "vehicleNumber": "TS 06 BX 1234",
  "type": "CAR",
  "fuelType": "PETROL",
  "condition": "DRIVABLE"
}
```
**Response:**
```json
{
  "id": 1,
  "vehicleNumber": "TS 06 BX 1234",
  "type": "CAR",
  "fuelType": "PETROL",
  "condition": "DRIVABLE",
  "userId": 1
}
```
**Validation:**
- vehicleNumber must be unique
- type must be BIKE or CAR
- condition required
- fuelType required

**Business Logic:**
1. Validate vehicle number doesn't exist
2. Create vehicle with logged-in user ID
3. Return vehicle DTO

---

#### GET /api/vehicles
**Purpose:** List all vehicles of logged-in customer  
**Access:** Authenticated customers  
**Response:**
```json
[
  {
    "id": 1,
    "vehicleNumber": "TS 06 BX 1234",
    "type": "CAR",
    "fuelType": "PETROL",
    "condition": "DRIVABLE",
    "userId": 1
  }
]
```

---

#### DELETE /api/vehicles/{id}
**Purpose:** Delete vehicle (ownership check)  
**Access:** Authenticated customers  
**Response:** 
```json
{
  "message": "Vehicle deleted successfully"
}
```
**Validation:**
- Ownership check: vehicle.userId == currentUser.id
- Return 403 Forbidden if not owner

---

### 3.3 Appointment Booking Endpoints

#### POST /api/appointments
**Purpose:** Book appointment for service  
**Access:** Authenticated customers  
**Request:**
```json
{
  "vehicleId": 1,
  "appointmentType": "SELF_DRIVE",
  "timeSlot": "2026-12-31T10:00:00"
}
```
**Response:**
```json
{
  "id": 1,
  "vehicleId": 1,
  "userId": 1,
  "appointmentType": "SELF_DRIVE",
  "timeSlot": "2026-12-31T10:00:00",
  "status": "CONFIRMED"
}
```
**Validation:**
- timeSlot must be future date (not past)
- vehicleId must belong to authenticated user
- appointmentType must be valid
- Implement slot availability check (TODO)

**Business Logic:**
1. Validate ownership: vehicle.userId == currentUser.id
2. Validate timeSlot > now
3. Check slot availability (placeholder)
4. Create Appointment with status CONFIRMED
5. Return DTO

---

#### GET /api/appointments/my-appointments
**Purpose:** List customer's appointments  
**Access:** Authenticated customers  
**Response:**
```json
[
  {
    "id": 1,
    "vehicleId": 1,
    "userId": 1,
    "appointmentType": "SELF_DRIVE",
    "timeSlot": "2026-12-31T10:00:00",
    "status": "CONFIRMED"
  }
]
```

---

#### GET /api/appointments
**Purpose:** List all appointments  
**Access:** ADMIN only  
**Response:** Same as above (array of all appointments)

---

### 3.4 Job Card Management Endpoints

#### POST /api/job-cards/check-in/{appointmentId}
**Purpose:** Check-in appointment → create JobCard  
**Access:** ADMIN only  
**Response:**
```json
{
  "id": 1,
  "userId": 1,
  "vehicleId": 1,
  "jobType": "WORKSHOP",
  "jobStatus": "PENDING",
  "estimateId": null,
  "towId": null
}
```
**Business Logic:**
1. Load appointment by ID
2. Validate appointment exists and status = CONFIRMED
3. Create JobCard with:
   - userId, vehicleId same as appointment
   - jobStatus = PENDING
   - jobType = WORKSHOP (if appointmentType = SELF_DRIVE) or DOORSTEP
   - estimate = null (created on estimation)
4. Update Appointment.status = CHECKED_IN
5. Return JobCard DTO

---

#### POST /api/job-cards/walk-in
**Purpose:** Create walk-in job (no appointment)  
**Access:** ADMIN only  
**Request:**
```json
{
  "userId": 1,
  "vehicleId": 1,
  "jobType": "WORKSHOP"
}
```
**Response:** JobCard DTO (same structure)  
**Business Logic:**
1. Validate user & vehicle exist
2. Create JobCard directly with jobStatus = PENDING

---

#### PUT /api/job-cards/{id}/status
**Purpose:** Update job status during execution  
**Access:** ADMIN, MECHANIC  
**Request:**
```json
{
  "newStatus": "IN_PROGRESS"
}
```
**Response:**
```json
{
  "message": "Job status updated to IN_PROGRESS"
}
```
**Valid Status Transitions:**
- PENDING → IN_PROGRESS (mechanic starts work)
- IN_PROGRESS → WAITING_FOR_PARTS (parts not available)
- WAITING_FOR_PARTS → IN_PROGRESS (parts received)
- IN_PROGRESS → QC_PASSED (work done, passes QC)
- QC_PASSED → COMPLETED (final state)
- COMPLETED → [Terminal state]
- Any state → ON_HOLD (pause)
- Any state → ABANDONED (customer no-show)

---

#### GET /api/job-cards
**Purpose:** List all job cards  
**Access:** ADMIN, MECHANIC  
**Response:**
```json
[
  {
    "id": 1,
    "userId": 1,
    "vehicleId": 1,
    "jobType": "WORKSHOP",
    "jobStatus": "PENDING"
  }
]
```

---

### 3.5 Estimate Management Endpoints

#### POST /api/estimates
**Purpose:** Create cost estimate for job  
**Access:** ADMIN only  
**Request:**
```json
{
  "jobCardId": 1,
  "labourItems": [
    {
      "description": "Diagnostic check",
      "cost": 500
    }
  ],
  "partItems": [
    {
      "partId": 1,
      "quantity": 2,
      "cost": 200
    }
  ],
  "taxes": 180
}
```
**Response:**
```json
{
  "id": 1,
  "jobCardId": 1,
  "totalAmount": 1380,  // 500 + (200*2) + 180
  "approved": false,
  "labourItems": [...],
  "partItems": [...]
}
```
**Calculation Formula:**
```
totalAmount = SUM(labourCost) + SUM(partCost × quantity) + taxes
```

**Business Logic:**
1. Validate jobCardId exists
2. Create LabourItems
3. Create PartItems (link to Part master)
4. Calculate totalAmount
5. Set approved = false (awaiting customer approval)
6. Link estimate to JobCard

---

#### GET /api/estimates/job-card/{jobCardId}
**Purpose:** Retrieve estimate for job  
**Access:** ADMIN, MECHANIC, customer (only for own jobs)  
**Response:** Same as POST response  
**Validation:**
- Customer can only see their own estimates (jobCard.userId == currentUser.id)

---

#### POST /api/estimates/{id}/approve
**Purpose:** Customer approves estimate  
**Access:** Customer (own jobs only)  
**Response:**
```json
{
  "message": "Estimate approved. Work can now proceed."
}
```
**Business Logic:**
1. Validate estimate exists
2. Validate current user is job owner (jobCard.userId == currentUser.id)
3. Set approved = true
4. Update JobCard.jobStatus = IN_PROGRESS (ready for mechanic)

---

#### POST /api/estimates/{id}/reject
**Purpose:** Customer rejects estimate  
**Access:** Customer  
**Response:**
```json
{
  "message": "Estimate rejected. Job status: ON_HOLD"
}
```
**Business Logic:**
1. Validate estimate exists and ownership
2. Find associated JobCard
3. Set JobCard.jobStatus = ON_HOLD
4. Set approved = false

---

### 3.6 Parts Inventory Endpoints

#### POST /api/parts
**Purpose:** Add part to inventory  
**Access:** ADMIN only  
**Request:**
```json
{
  "name": "Oil Filter OEM 2020",
  "quantity": 50
}
```
**Response:**
```json
{
  "id": 1,
  "name": "Oil Filter OEM 2020",
  "quantity": 50
}
```
**Validation:**
- Part name must be unique

---

#### GET /api/parts
**Purpose:** List all parts  
**Access:** ADMIN, MECHANIC  
**Response:**
```json
[
  {
    "id": 1,
    "name": "Oil Filter OEM 2020",
    "quantity": 50
  }
]
```

---

### 3.7 Edge Case Endpoints

#### POST /api/edge-cases/hold-vehicle
**Purpose:** Hold vehicle from unclaimed tow  
**Access:** ADMIN only  
**Request:**
```json
{
  "vehicleNumber": "TS 06 BX 1234",
  "type": "CAR",
  "fuelType": "PETROL"
}
```
**Response:**
```json
{
  "id": 1,
  "vehicleNumber": "TS 06 BX 1234",
  "type": "CAR",
  "condition": "TOWED_NO_OWNER"
}
```
**Business Logic:**
1. Check: Vehicle exists with this number?
   - YES: Check if already held → error
   - NO: Create vehicle with condition = TOWED_NO_OWNER
2. Assign to SHADOW_USER (special user for held vehicles)
3. Create JobCard in ON_HOLD status

---

#### POST /api/edge-cases/link-customer/{jobCardId}
**Purpose:** Link customer to on-hold job  
**Access:** ADMIN only  
**Request:**
```json
{
  "phoneNumber": "9876543210"
}
```
**Response:**
```json
{
  "message": "Job linked to customer successfully"
}
```
**Business Logic:**
1. Load JobCard (must be ON_HOLD)
2. Find/create customer by phone
3. Transfer JobCard.userId from SHADOW_USER → real customer
4. Update Vehicle.userId → real customer
5. Update JobCard.jobStatus = PENDING (ready for estimate)

---

#### POST /api/edge-cases/abandon/{jobCardId}
**Purpose:** Mark job as abandoned  
**Access:** ADMIN only  
**Response:**
```json
{
  "message": "Job marked as abandoned"
}
```
**Business Logic:**
1. Load JobCard
2. Set jobStatus = ABANDONED
3. Mark vehicle as TOWED_NO_OWNER (if applicable)
4. Calculate storage charges

---

### 3.8 Dashboard Endpoints

#### GET /api/dashboard/stats
**Purpose:** Admin dashboard statistics  
**Access:** SUPER_ADMIN only  
**Response:**
```json
{
  "totalAdmins": 5,
  "totalMechanics": 12,
  "totalCustomers": 150,
  "activeJobs": 25,
  "completedJobs": 500,
  "totalRevenue": 450000
}
```

---

## 4. SECURITY SPECIFICATIONS

### 4.1 Authentication

| Method | Flow | Token Expiry | Use Case |
|--------|------|-------------|----------|
| OTP | Phone → OTP → Verify → JWT | 24 hours | Customer login |
| Password | Phone + Password → JWT | 24 hours | Staff login |

### 4.2 Authorization

| Role | Endpoints |
|------|-----------|
| SUPER_ADMIN | Admin users CRUD, Service types CRUD, Dashboard |
| ADMIN | Job CRUD, Estimate creation, User management, Parts inventory |
| MECHANIC | Job status updates, View job cards |
| CUSTOMER | Appointment booking, Vehicle management, Estimate approval |

### 4.3 Data Ownership Validation

- Customers can only access their own vehicles, appointments, and job cards
- Estimates visible only to customer and admin/mechanic working on the job
- Soft deletes (not hard) for audit trail

---

## 5. ERROR HANDLING

### 5.1 HTTP Status Codes

| Code | Scenario |
|------|----------|
| 200 OK | Success |
| 400 Bad Request | Validation error, invalid input |
| 401 Unauthorized | No JWT or expired JWT |
| 403 Forbidden | Authenticated but not authorized (role/ownership) |
| 404 Not Found | Resource not found |
| 500 Internal Server Error | Unexpected error |

### 5.2 Error Response Format

```json
{
  "message": "Vehicle not found with ID: 1"
}
```

---

## 6. WORKFLOW DIAGRAMS

### 6.1 Complete Job Lifecycle

```
START
  ↓
APPOINTMENT or WALK-IN
  ├─ Online Booking → Appointment Created (CONFIRMED)
  ├─ Check-in → JobCard (PENDING)
  ├─ Walk-in → JobCard (PENDING) directly
  ↓
ADMIN CREATES ESTIMATE
  ├─ Labour items + Parts items
  ├─ Calculate total with taxes
  ├─ Estimate created (approved = false)
  ↓
CUSTOMER APPROVAL
  ├─ YES → approved = true, JobCard → IN_PROGRESS
  ├─ NO → approved = false, JobCard → ON_HOLD
  ├─ NO RESPONSE → Auto ON_HOLD after 24 hours (TODO)
  ↓
MECHANIC EXECUTION
  ├─ JobCard → IN_PROGRESS
  ├─ Parts become available → Work continues
  ├─ Parts NOT available → JobCard → WAITING_FOR_PARTS
  ├─ Work complete → QC PASS → JobCard → QC_PASSED
  ↓
ADMIN FINAL CHECK
  ├─ Payment collected
  ├─ Vehicle delivered
  ├─ JobCard → COMPLETED
  ↓
END (Revenue recorded)
```

### 6.2 Tow Service Flow

```
APPOINTMENT TYPE = TOW_REQUIRED
  ↓
SYSTEM CREATES TOW
  ├─ Status = SCHEDULED
  ├─ Pick-up location from customer address
  ↓
TOWED → PICKED UP
  ├─ Tow status = PICKED
  ↓
VEHICLE ARRIVES AT WORKSHOP
  ├─ Check: Customer phone known?
  │  ├─ YES → Link to customer
  │  └─ NO → Create HOLD (SHADOW_USER)
  ↓
NORMAL JOB LIFECYCLE continues
```

### 6.3 Edge Case: Unclaimed Vehicle

```
VEHICLE ARRIVES BY TOW (Customer unknown)
  ↓
ADMIN HOLD-VEHICLE
  ├─ Create Vehicle with condition=TOWED_NO_OWNER
  ├─ Assign to SHADOW_USER
  ├─ Create JobCard (ON_HOLD)
  ↓
WHEN CUSTOMER CALLS
  ↓
ADMIN LINK-CUSTOMER
  ├─ Find customer by phone
  ├─ Transfer JobCard.userId → customer
  ├─ Transfer Vehicle.userId → customer
  ├─ Career to PENDING
  ↓
NORMAL JOB LIFECYCLE continues
```

---

## 7. COMPONENT SPECIFICATIONS

### 7.1 Backend Components

| Component | Responsibility | Dependencies |
|-----------|---|---|
| **AuthController** | Route OTP/password requests | AuthService, OtpService, JwtUtils |
| **AppointmentController** | Route appointment requests | AppointmentRepository, VehicleRepository |
| **JobCardController** | Route job creation/status updates | JobCardRepository, EstimateRepository |
| **EstimateController** | Route estimate operations | EstimateRepository, JobCardRepository |
| **VehicleController** | Route vehicle operations | VehicleRepository, UserRepository |
| **AuthTokenFilter** | Intercept requests, validate JWT | JwtUtils, UserRepository |
| **GlobalExceptionHandler** | Centralized error handling | Spring exception handling |

### 7.2 Frontend Components (To Be Built)

| Component | Purpose | Dependencies |
|-----------|---------|---|
| **LoginComponent** | Customer OTP login | AuthService, HttpClient |
| **BookingComponent** | Create appointments | AppointmentService, VehicleService |
| **JobCardComponent** | View & track job status | JobCardService |
| **DashboardComponent** | Admin statistics | DashboardService |
| **EstimateComponent** | View & approve estimates | EstimateService |
| **InventoryComponent** | Manage parts | PartService |
| **UserManagementComponent** | SUPER_ADMIN staff management | UserService |

---

## 8. IMPLEMENTATION PHASES

### Phase 1: Authentication & Core Entities (✅ COMPLETED)
- JWT-based auth
- User roles & security
- Basic CRUD for all entities
- OTP/Password login

### Phase 2: Appointment & Job Management (~70% done)
- Appointment booking (✅)
- Job card lifecycle (✅)
- Estimate creation & approval (✅)
- Edge case handling (✅)
- **TODO:** Slot availability algorithm

### Phase 3: Frontend Pages (0% done)
- [ ] Login page (OTP)
- [ ] Customer portal (bookings, job status)
- [ ] Admin dashboard (job management)
- [ ] Mechanic dashboard (execution)
- [ ] Inventory management

### Phase 4: Integrations (0% done)
- [ ] SMS/Email for OTP & notifications
- [ ] Payment gateway (Razorpay/Stripe)
- [ ] File uploads (vehicle photos)
- [ ] Reporting & dashboards

### Phase 5: Production Readiness (0% done)
- [ ] Database migration (PostgreSQL)
- [ ] Docker containerization
- [ ] CI/CD pipeline
- [ ] Performance optimization
- [ ] Monitoring & logging

---

## 9. DATABASE MIGRATION SCRIPTS (for PostgreSQL)

```sql
-- Run after switching from H2 to PostgreSQL
ALTER TABLE users ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE users ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
-- Similar for all entities (audit fields)

CREATE INDEX idx_user_phone ON users(phone_number);
CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_vehicle_number ON vehicles(vehicle_number);
CREATE INDEX idx_vehicle_user ON vehicles(user_id);
CREATE INDEX idx_appointment_user ON appointments(user_id);
CREATE INDEX idx_appointment_status ON appointments(status);
CREATE INDEX idx_job_card_user ON job_cards(user_id);
CREATE INDEX idx_job_card_status ON job_cards(job_status);
CREATE INDEX idx_job_card_vehicle ON job_cards(vehicle_id);
```

---

## 10. TESTING STRATEGY

### Unit Tests
- Service layer methods
- Validation logic
- Calculation formulas (estimate total)

### Integration Tests (Bruno Collection)
- Auth flows (OTP, password)
- Full job lifecycle (appointment → completion)
- Edge cases (unclaimed vehicle → linking)
- Role-based access control
- Data ownership validation

### Manual Testing (Frontend)
- UI responsiveness
- Form validation
- Error message display
- Navigation flow

---

## 11. Known Limitations & TODOs

1. **OTP Delivery:** Currently console logs, needs SMS/Email
2. **Slot Availability:** Algorithm not implemented
3. **Payment Gateway:** Not integrated
4. **Notifications:** SMS/Email not sent
5. **File Uploads:** Vehicle photos not supported
6. **Doorstep Diagnosis:** Workflow incomplete
7. **Dashboard Stats:** Missing method implementations
8. **Transaction Management:** No @Transactional annotations
9. **Audit Logging:** Not implemented
10. **Rate Limiting:** Not implemented

---

## 12. Performance Considerations

- JWT validation on every request (stateless but CPU-intensive)
- Eager fetch for Estimate → optimizes single-request loads
- Index recommendations: phone, email, vehicle_number, status columns
- Connection pooling: Configure HikariCP for database
- Caching: Consider Redis for OTP & frequently accessed data

---

## 13. Security Checklist

- [x] CSRF disabled (stateless)
- [x] BCrypt password hashing
- [x] JWT signature validation
- [x] Role-based access control
- [ ] Input validation (TODO: add @Valid annotations)
- [ ] SQL injection prevention (using JPA)
- [ ] Rate limiting (TODO)
- [ ] Audit logging (TODO)
- [ ] HTTPS only (production)
- [ ] Secrets management (environment variables)

---

**Document Version:** 1.0  
**Last Updated:** April 2026  
**Author:** Automotive Service Platform Team
