# 📅 DueMate – Smart Assignment & Task Tracker

DueMate is a **feature-rich Android application** built to help students and professionals organize assignments, tasks, and important deadlines effortlessly.  
It supports **text, images, reminders, progress tracking, and secure login** — all wrapped in a clean, modern UI.

---

## 🚀 Features

### 📝 Task & Assignment Management
- Add assignments with **subject name**, **title**, **description**, and **optional images**.
- Automatic **sorting by deadline** — nearest deadlines always appear on top.
- Store multiple types of entries: assignments, events, meeting notes, etc.

### 📅 Smart Deadline Reminders
- Smart notifications **24h**, **12h**, **6h**, and **1h** before deadline.
- Overdue alerts when the deadline passes.
- **Color-coded notifications**:
  - 🟢 Green → Plenty of time
  - 🟡 Yellow → Approaching deadline
  - 🔴 Red → Deadline close/overdue

### 🎨 Modern UI & Dark Mode
- **Material Design 3** with smooth animations and responsive layouts.
- **Light & Dark themes** with instant switching.
- Bold titles and clean list view for quick scanning.

### 👤 User Authentication
- **Email-based secure login** with Firebase Authentication.
- Your data is private and synced across devices.

### 📂 Media Support
- Attach multiple **images** to a task (reference files, scanned notes, etc.).
- Built-in **full-screen image viewer**.

### 🔍 Search & Filter
- Search tasks by **subject**, **keywords**, or **task type**.
- Filter tasks by **status** (Not Started / In Progress / Completed).

### ⚡ Quick Add Widget
- Add assignments straight from your **home screen widget** without opening the app.

### 📊 Progress Tracker
- Mark tasks as **Not Started**, **In Progress** or **Completed**.
- Track progress visually with percentage completion.

---

## 🛠️ Tech Stack

| Component         | Technology Used |
|-------------------|-----------------|
| Language          | Kotlin |
| UI Framework      | Jetpack Compose |
| Local Database    | Room Database |
| Authentication    | Firebase Auth |
| Cloud Storage     | Firebase Firestore & Storage |
| Notifications     | AlarmManager |
| Theming           | Material Design 3 |
| Version Control   | Git + GitHub |

---

## 🖼️ UI Design Preview

> Images are served via GitHub Raw to avoid path/case issues.

| Authentication | Home |
|---|---|
| <img src="https://raw.githubusercontent.com/mastercoder421/DueMate/main/ui-design/Auth_Screen.png" width="300"/> | <img src="https://raw.githubusercontent.com/mastercoder421/DueMate/main/ui-design/Home_Screen.png" width="300"/> |

| Add/Edit Task | Task Details |
|---|---|
| <img src="https://raw.githubusercontent.com/mastercoder421/DueMate/main/ui-design/Add_Edit_Task.png" width="300"/> | <img src="https://raw.githubusercontent.com/mastercoder421/DueMate/main/ui-design/Task_Details.png" width="300"/> |

| Search & Filter | Calendar View |
|---|---|
| <img src="https://raw.githubusercontent.com/mastercoder421/DueMate/main/ui-design/Search_Filter.png" width="300"/> | <img src="https://raw.githubusercontent.com/mastercoder421/DueMate/main/ui-design/Calendar_View.png" width="300"/> |

| Settings |
|---|
| <img src="https://raw.githubusercontent.com/mastercoder421/DueMate/main/ui-design/Settings.png" width="300"/> |


---

## 📥 Installation & Setup

### Prerequisites
- **Android Studio** (latest version recommended)
- **Minimum SDK:** 21 (Android 5.0 Lollipop)
- **Firebase Project** configured with:
  - Firebase Authentication
  - Firebase Firestore
  - Firebase Storage

### Steps to Run Locally
1. **Clone the repository:**
   ```bash
   git clone https://github.com/mastercoder421/DueMate.git
