# 🏁 Automotive Service Platform – Complete High-Level Design (HLD)

> **Purpose of this document**
> This README describes **every possible real-world flow** of the automotive service business in **simple, non-technical language**.
> If this HLD is satisfied, the system can be safely built using software (LLD).

---

## 1. BUSINESS GOAL

Create a **one-stop automotive service center** in Andhra Pradesh that supports:

* Walk-in customers
* Online booking
* Tow-based service
* Doorstep diagnosis

The system must handle **good customers, confused customers, and difficult customers** without breaking operations or losing money.

---

## 2. PEOPLE INVOLVED (ACTORS)

### Human Roles

* **Customer** – Vehicle owner
* **Service Advisor (Admin)** – Front desk / operations
* **Mechanic** – Does diagnosis & repair
* **Tow Driver** – Towing vehicle
* **QC / Manager** – Final quality check (optional)

### System Roles (Invisible but Important)

* Scheduler (manages time & capacity)
* Inventory checker (parts availability)
* Notification system (SMS / WhatsApp)

---

## 3. ALL POSSIBLE ENTRY POINTS (HOW A JOB STARTS)

A service job can start in **only these ways**:

1. Walk-in (customer drives vehicle)
2. Walk-in (vehicle arrives by tow)
3. Online appointment – customer can drive
4. Online appointment – customer needs tow
5. Online appointment – customer not sure
6. Doorstep breakdown / diagnosis

Every scenario in real life fits into one of these.

---

## 4. CUSTOMER IDENTIFICATION (COMMON STEP)

* Phone number is mandatory
* System checks:

  * Existing customer → load details
  * New customer → create **temporary (provisional) account**

Customer can verify later using OTP.

No service is blocked because of login issues.

---

## 5. VEHICLE HANDLING (COMMON STEP)

Customer may:

* Select existing vehicle
* Add new vehicle

Vehicle condition is marked as:

* Drivable
* Not drivable
* Unknown

This affects tow and booking decisions.

---

## 6. ONLINE BOOKING – COMPLETE FLOW

### Step 1: Customer selects

* Vehicle
* Problem / service type
* Preferred date

### Step 2: System asks a critical question

**“Can you drive the vehicle to the workshop?”**

Customer answers:

* YES
* NO
* NOT SURE

---

### Path A: YES – Customer Can Drive

* System shows available slots
* Customer books slot
* Appointment confirmed
* Customer arrives
* Admin checks-in
* Job card is created

---

### Path B: NO – Tow Required

* System checks tow availability
* Customer selects pickup time
* Tow is scheduled
* Vehicle reaches workshop
* Job card is auto-created

---

### Path C: NOT SURE – Diagnose First

* System converts booking into **doorstep diagnosis**

---

## 7. DOORSTEP DIAGNOSIS – COMPLETE FLOW

### Step 1: Customer requests diagnosis

* Shares location
* Shares symptoms

### Step 2: Mechanic visits

### Step 3: Diagnosis outcome

Mechanic decides:

1. Fixed on the spot
2. Safe to drive
3. Not safe to drive

---

### Outcome 1: Fixed on Spot

* Job completed
* Bill generated
* Payment collected

### Outcome 2: Safe to Drive

* Customer books workshop slot

### Outcome 3: Not Safe to Drive

* Customer chooses:

  * Accept tow → workshop job
  * Reject → visit fee only

---

## 8. WALK-IN CUSTOMER FLOW

* Customer arrives directly
* Admin checks capacity
* Normal slot OR emergency override
* Job card created
* Mechanic diagnosis
* Estimate prepared

Customer chooses:

* Accept full
* Accept partial
* Reject

---

## 9. WORKSHOP JOB LIFE CYCLE

1. Diagnosis
2. Estimate approval
3. Repair work
4. Parts check
5. Quality check
6. Billing
7. Delivery

Possible job states:

* In progress
* Waiting for parts
* Paused
* Completed

---

## 10. PARTS & INVENTORY CASES

* Parts available → proceed
* Parts not available → wait
* Customer brings own parts

  * Labour charged
  * No warranty on parts

---

## 11. PAYMENT SCENARIOS

* Visit fee only
* Full payment
* Partial payment
* Balance pending

Vehicle delivery depends on payment status.

---

## 12. REAL-LIFE EDGE CASES (VERY IMPORTANT)

### 1. Vehicle arrives by tow but customer is not present

This can happen when:

* Local tow driver brings the vehicle
* Customer will come later
* Customer phone number is not immediately available

**Business rule:**

* Job is NOT created under tow driver’s phone number
* Admin creates a temporary HOLD record
* Vehicle status = ON HOLD
* No repair work starts
* No estimate approval taken

As soon as the owner’s phone number is confirmed:

* Job is linked to the customer
* Normal workflow continues

---

### 2. Customer abandons vehicle

* Vehicle marked as HOLD / ABANDONED
* Storage charges apply

### 3. Customer takes vehicle mid-job

* Partial bill
* Job paused

### 4. Customer changes decision multiple times

* Decision history saved

### 5. Mechanic mistake / rework

* No charge to customer
* Internal tracking

### 6. Repeat complaint

* Linked to previous job
* Discount / free / paid decision

### 7. No-show appointments

* Marked as NO-SHOW

### 8. Workshop holiday booking

* Auto block or reschedule

---

## 13. TOW SERVICE – ALL CASES

Tow can be triggered from:

* Online booking
* Doorstep diagnosis
* Walk-in emergency

Tow outcomes:

* Successful
* Cancelled
* Failed

Charges adjusted accordingly.

---

## 14. ADMIN OVERRIDES (CONTROL & SAFETY)

Admin can override:

* Capacity
* Slot limits

Every override records:

* Reason
* Time
* Who approved

---

## 15. FINAL COMPLETENESS CHECK

✔ All customer behaviors covered
✔ Tow handled everywhere
✔ Confusion & indecision handled
✔ Business protected from loss
✔ Can scale later without redesign

---
