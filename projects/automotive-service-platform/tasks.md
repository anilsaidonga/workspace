# 🚀 Automotive Service Platform: Task Breakdown

This document breaks down the entire project implementation into logical, sequential tasks based on the HLD and LLD.

---

## Phase 1: Backend Setup & Core Models

The goal of this phase is to establish the database structure and core Java objects.

*   [x] **Task 1.1: Project Initialization**:
    *   [x] Set up Spring Boot project with necessary dependencies (Spring Web, Spring Data JPA, Spring Security, H2/PostgreSQL Driver).
    *   [x] Configure database connection in `application.properties`.

*   [x] **Task 1.2: Create Core JPA Entities**:
    *   [x] `User.java`: Represents `CUSTOMER`, `ADMIN`, `MECHANIC`. Include fields like `phone_number`, `name`, `role`, and `status` (PROVISIONAL, VERIFIED).
    *   [x] `Vehicle.java`: Represents a customer's vehicle. Include `vehicle_number`, `type`, `fuel_type`, and `condition` (Drivable, Non-Drivable). Link to `User`.
    *   [x] `JobCard.java`: The central entity. Include `job_type` (Workshop, Doorstep), `status`, and relationships to `User` and `Vehicle`.
    *   [x] `Appointment.java`: For online bookings. Include `appointment_type` (Self Drive, Tow Required, Diagnosis), `time_slot`, and `status`.
    *   [x] `Tow.java`: For tow requests. Include `pickup_location` and `status`.
    *   [x] `Estimate.java`: For repair estimates. Include lists for `labour_items` and `parts_items`.
    *   [x] `Part.java`: For inventory management. Include `name` and `quantity`.
    *   [x] `Payment.java`: For billing. Include `amount`, `mode`, and `status`.

*   [x] **Task 1.3: Create Repositories**:
    *   [x] Create Spring Data JPA repositories for all entities (e.g., `UserRepository`, `JobCardRepository`, etc.).

---

## Phase 2: Authentication & Business Logic (Backend APIs)

Implement the core business logic and expose it via REST APIs.

*   [ ] **Task 2.1: Authentication & Authorization**:
    *   [ ] Implement JWT-based security for authenticating users.
    *   [ ] Set up `UserDetailsService` to load user data.
    *   [ ] Configure role-based access control (`@PreAuthorize`) for endpoints (ADMIN, MECHANIC, CUSTOMER).
    *   [ ] Implement OTP logic for customer verification.

*   [ ] **Task 2.2: Customer & Vehicle Management APIs**:
    *   [ ] Create `CustomerController` with endpoints for admin to manage customers (CRUD).
    *   [ ] Create `VehicleController` for customers to manage their vehicles (CRUD).

*   [ ] **Task 2.3: Online Booking & Appointment API**:
    *   [ ] Create `AppointmentController`.
    *   [ ] Implement `POST /api/appointments` for customers to book a slot. This endpoint must handle the three paths: `Self Drive`, `Tow Required`, `Diagnosis Only`.
    *   [ ] Implement `GET /api/appointments/slots` to show available time slots.
    *   [ ] Implement admin endpoints to view and manage all appointments.

*   [ ] **Task 2.4: Job Card Lifecycle API**:
    *   [ ] Create `JobCardController`.
    *   [ ] Implement `POST /api/job-cards/walk-in` for admins to create a job for a walk-in customer.
    *   [ ] Implement logic to auto-create a `JobCard` when an appointment is "checked-in".
    *   [ ] Implement `PUT /api/job-cards/{id}/status` for mechanics and admins to update job status according to the defined lifecycle.

*   [ ] **Task 2.5: Diagnosis, Estimate & Approval API**:
    *   [ ] Create `EstimateController`.
    *   [ ] Implement endpoints for mechanics to add diagnosis details to a job.
    *   [ ] Implement endpoints for admins to create an `Estimate` for a job.
    *   [ ] Implement an endpoint for customers to `Accept` or `Reject` an estimate. Store decision history.

*   [ ] **Task 2.6: Edge Case Handling**:
    *   [ ] Implement logic for a "Vehicle on HOLD" when a towed vehicle arrives without an owner.
    *   [ ] Design flow for handling abandoned vehicles and partial billing.

---

## Phase 3: Frontend Implementation (Angular)

Build the user interface for all roles.

*   [ ] **Task 3.1: Project Setup & Core Components**:
    *   [ ] Initialize Angular project.
    *   [ ] Create a `SharedModule` for common components (e.g., headers, buttons).
    *   [ ] Implement `AuthService` to handle JWT tokens and user roles.
    *   [ ] Implement routing with guards for Customer, Admin, and Mechanic roles.
    *   [ ] Create Login and OTP verification pages.

*   [ ] **Task 3.2: Customer-Facing Flow**:
    *   [ ] **Dashboard**: View personal vehicles and a summary of active/past jobs.
    *   [ ] **Booking Form**: Create a new appointment, select vehicle, problem, and date. The form must handle the "Can you drive?" question.
    *   [ ] **Job Tracking View**: Display the status of an ongoing job.
    *   [ ] **Estimate Approval View**: Display estimate details with "Accept" and "Reject" buttons.

*   [ ] **Task 3.3: Admin (Service Advisor) Flow**:
    *   [ ] **Main Dashboard**: Overview of today's appointments, ongoing jobs, and pending tasks.
    *   [ ] **Walk-in Form**: A dedicated form to quickly create a job for a walk-in customer.
    *   [ ] **Appointment Management**: View all upcoming appointments, check-in customers, and handle no-shows.
    *   [ ] **Job Management View**: A detailed table to view and manage all job cards.
    *   [ ] **Estimate Creation Form**: A form to build and send an estimate for a job.

*   [ ] **Task 3.4: Mechanic Flow**:
    *   [ ] **Assigned Jobs Dashboard**: A simple list of jobs assigned to the logged-in mechanic.
    *   [ ] **Job Details View**: View vehicle/customer info, add diagnosis notes/photos, and update job status (e.g., 'In Progress', 'Waiting for Parts', 'Ready for QC').

---

## Phase 4: Testing & Deployment

*   [ ] **Task 4.1: Backend Testing**:
    *   [ ] Write unit tests for services and controllers.
    *   [ ] Write integration tests for critical API flows (e.g., booking to job completion).

*   [ ] **Task 4.2: Frontend Testing**:
    *   [ ] Write unit tests for components and services.
    *   [ ] Write E2E tests for main user journeys (customer booking, admin job creation).

*   [ ] **Task 4.3: Deployment**:
    *   [ ] Dockerize the Spring Boot backend.
    *   [ ] Dockerize the Angular frontend (using a web server like Nginx).
    *   [ ] Create a `docker-compose.yml` file to run the entire stack.
    *   [ ] (Optional) Set up a CI/CD pipeline on GitHub Actions.
