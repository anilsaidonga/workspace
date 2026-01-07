# 🧩 Automotive Service Platform
## Low-Level Design (LLD)

This document explains the **Low-Level Design (LLD)** of the Automotive Service Platform.

LLD answers **HOW exactly the system works internally**, step by step, in **simple language**.

> If HLD is *what the system should do*, LLD is *how each part behaves*.

---

# 1️⃣ USERS & AUTHENTICATION

## 1.1 User Types

| User Type | Purpose |
|---------|--------|
| ADMIN | Service advisor / manager |
| MECHANIC | Works on jobs |
| CUSTOMER | Vehicle owner |

---

## 1.2 User Data

Each user stores:
- User ID
- Phone Number (unique)
- Name
- Role
- Account Status
  - PROVISIONAL (created by admin)
  - VERIFIED (OTP verified)

---

## 1.3 Authentication Logic

### Admin / Mechanic
- Login via phone/email + password
- Role-based access enforced

### Customer
- Login via phone + OTP
- First login creates profile automatically

---

# 2️⃣ CUSTOMER MANAGEMENT

## 2.1 Shadow (Provisional) Customer

Created when:
- Walk-in customer
- Tow vehicle arrives without owner

Rules:
- Phone number is primary ID
- No password required initially
- Can be upgraded to VERIFIED later

---

## 2.2 Customer Linking Rules

- Tow driver phone is **never** a customer
- Vehicles on HOLD are linked only after owner is identified

---

# 3️⃣ VEHICLE MANAGEMENT

## 3.1 Vehicle Data

Stores:
- Vehicle ID
- Vehicle Number
- Vehicle Type (Bike / Car)
- Fuel Type
- Current Condition
  - Drivable
  - Non-Drivable
  - Unknown

---

## 3.2 Vehicle Ownership

- One customer → multiple vehicles
- Vehicle may exist without customer (HOLD state)

---

# 4️⃣ APPOINTMENT MANAGEMENT

## 4.1 Appointment Data

Stores:
- Appointment ID
- Customer
- Vehicle
- Time Slot
- Appointment Type
  - Self Drive
  - Tow Required
  - Diagnosis Only
- Status
  - Confirmed
  - Checked-in
  - No-show
  - Cancelled

---

## 4.2 Slot Allocation Logic

- Each slot has max capacity
- Online booking blocked when full
- Admin override allowed

---

# 5️⃣ TOW MANAGEMENT

## 5.1 Tow Data

Stores:
- Tow ID
- Vehicle
- Pickup Location
- Tow Status
  - Scheduled
  - Picked
  - Completed
  - Cancelled

Rules:
- Tow is always linked to a vehicle
- Tow driver is not a system user

---

# 6️⃣ JOB CARD (CORE ENTITY)

## 6.1 Job Card Data

Stores:
- Job ID
- Customer
- Vehicle
- Job Type
  - Workshop
  - Doorstep
- Job Status
  - Pending
  - In Progress
  - Waiting for Parts
  - QC Passed
  - Completed

---

## 6.2 Job Creation Logic

| Entry Point | Job Creation |
|-----------|-------------|
| Walk-in | Admin creates job |
| Appointment | Auto-create on check-in |
| Doorstep | Created on request |

---

## 6.3 Job Status Rules

- Status transitions are controlled
- Invalid jumps are blocked

---

# 7️⃣ DIAGNOSIS & ESTIMATE

## 7.1 Diagnosis

- Mechanic records problems
- Media attachments allowed

---

## 7.2 Estimate Data

Stores:
- Estimate ID
- Job
- Labour Items
- Parts Items
- Taxes
- Total Amount

---

## 7.3 Estimate Approval

Customer can:
- Accept full
- Accept partial
- Reject

Decision history stored permanently.

---

# 8️⃣ INVENTORY MANAGEMENT

## 8.1 Inventory Data

Stores:
- Part Name
- Available Quantity

---

## 8.2 Inventory Guard Logic

- Estimate checks stock
- Missing parts → Job paused
- Admin alerted

---

# 9️⃣ PAYMENT MANAGEMENT

## 9.1 Payment Data

Stores:
- Payment ID
- Job
- Amount
- Payment Mode
  - Cash
  - Online (future)
- Payment Status
  - Paid
  - Partial
  - Pending

---

## 9.2 Payment Rules

- Payment required before delivery
- Partial payment allowed (policy-driven)

---

# 🔟 VEHICLE DELIVERY

## 10.1 Quality Check (QC)

- Mechanic marks QC passed
- Admin confirms

---

## 10.2 Delivery Logic

- Payment verified
- Vehicle marked delivered
- Job closed

---

# 1️⃣1️⃣ REAL-LIFE EDGE CASES

- Vehicle abandoned → storage charges
- Customer takes vehicle mid-job → partial bill
- Repeat complaint → linked job
- Mechanic error → rework, no charge

---

# 1️⃣2️⃣ ROLE-BASED ACCESS CONTROL

| Role | Access |
|----|-------|
| Admin | Full access |
| Mechanic | Assigned jobs only |
| Customer | Own vehicles & jobs |

---

# 🧠 IMPLEMENTATION NOTES

- Phone number is the primary identity
- Job Card is the central entity
- Everything links back to Job

---

# ✅ LLD STATUS

This LLD:
- Fully maps HLD
- Covers all job entry paths
- Is safe for direct implementation

---

> **Next step:** Implement modules one by one following the execution README.

