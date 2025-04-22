A modern Android application built with Jetpack Compose that demonstrates clean architecture principles and modern Android development for ANZ Coding Assignment .

## App Demo
![App Demo](app_demo.gif)

### Tech Stack Used

- **Kotlin** - Because we love its null safety and coroutines
- **Jetpack Compose** - For building beautiful, native Android UI
- **Material Design** - To make everything look polished and professional
- **Android SDK 34** - Keeping it up-to-date
- **Java 17** - For all the modern Java features we need

### Architecture
We followed some solid architecture patterns:
- **Clean Architecture** - Keeping things organized and maintainable
- **MVVM** - Because separation of concerns is important
- **Repository Pattern** - For handling data like pros
- **Hilt** - Making dependency injection a breeze

### LibrariesUsed 
- **Hilt** - For dependency injection
- **Coil** - Loading images smoothly
- **Room** - Local database for offline support
- **Coroutines** - Handling async operations elegantly
- **StateFlow** - Managing state reactively
- **Navigation Compose** - Smooth navigation between screens
- **Material** - Beautiful UI components
- **Lifecycle Components** - Managing the app lifecycle

### Kotlin Features
- **Sealed Classes**
  - Perfect for handling different states (Loading, Success, Error)
  - Type-safe state management in ViewModels
  - Exhaustive when expressions - no surprises!
- **StateFlow**
  - Keeping our UI in sync with data
  - Hot streams for real-time updates
  - Lifecycle-aware, because we care about battery life

## Project Structure
```
app/
├── data/               
│   ├── repository/    # Data Repostiory
│   ├── local/         # Local storage
│   └── remote/        # API Calls 
├── domain/            # Business logic
│   ├── model/         # Data models
│   ├── repository/    # Repository interfaces
│   └── usecase/       # Use cases
└── ui/                # UI or Presentation Layer
    ├── common/        # Reusable components
    ├── list/          # User list screen
    └── detail/        # User detail screen
```

## Completed Tasks
- Shows a list of users with their info
- Displays detailed user profiles
- Works offline (because we're thoughtful like that)
- Looks modern and clean
- Handles images efficiently
- Manages errors gracefully

## Done with Assumption
1. **API**
   - REST API Base URL : https://fake-json-api.mock.beeceptor.com/
   - Get User List : https://fake-json-api.mock.beeceptor.com/users/
   - Get User By ID : https://fake-json-api.mock.beeceptor.com/users/{userId}
   - It returns user data in a specific format

2. **Data Structure**
   - Users have basic info (name, email, photo, country)
   - Photos come as URLs (we use Coil to load them)

3. **Offline Support**
   - Local caching for when you're offline
   - Auto-refresh when you're back online

4. **UI/UX**
   - Following Material Design guidelines
   - Works on all screen sizes
   - Shows loading states and errors

## Issues Faced
1. **Data Mismatch**
   - Sometimes the user details don't match the list view
   - Photos and names might be different
   - We're working on fixing this!

## Improvments 
1. **Features We Want to Add**
   - Search functionality
   - Filtering and sorting
   - Pull-to-refresh

2. **Technical Upgrades**
   - Better error handling
   - Retry mechanisms
   - Data pagination
   - Smarter caching

3. **UI**
   - Clickable phone numbers and emails
   - Maps integration for addresses
   - Share functionality
   - Deep linking
   - Copy 
   - Rich text 
   - Animations
   - Dark mode
   - Better accessibility
   - Visual Enhancements:
     - Card shadows and elevation
     - ANZ logo in the app bar
     - Material icons for contacts
     - Ripple effects
     - Subtle animations
     - Consistent spacing

4. **Testing**
   - More UI tests
   - Integration tests
   - Performance testing
   - Edge case coverage

5. **Performance**
   - Image loading
   - Memory caching
   - Scroll optimization
   - Lazy loading

6. **Security**
   - SSL pinning
   - Secure storage
   - API key management

## Requirements
- Android Studio Hedgehog | 2023.1.1+
- Android SDK 34
- JDK 17
- Gradle 8.0+

