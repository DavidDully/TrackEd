# TrackEd Supabase Integration TODO

## 1. Initialize Supabase in MainActivity
- [ ] Add SupabaseManager.initialize(context) in MainActivity.onCreate()

## 2. Create Data Classes
- [ ] ScienceModuleData.kt - for science_modules table
- [ ] TopicData.kt - for topics table
- [ ] StudyProgressData.kt - for study_progress table

## 3. Expand SupabaseApi Interface
- [ ] Add endpoints for science_modules (GET, POST)
- [ ] Add endpoints for topics (GET, POST)
- [ ] Add endpoints for study_progress (GET, POST, PATCH)
- [ ] Add endpoints for users if needed (GET, POST)

## 4. Update Screens to Use Supabase Data
- [ ] ProgressScreen.kt - fetch study_progress and display real data
- [ ] ModulesScreen.kt - fetch science_modules and topics
- [ ] ScienceScreen.kt - integrate with modules
- [ ] TopicScreen.kt - show topic details and progress
- [ ] StudyScreen.kt - update progress on study sessions

## 5. Seed Database
- [ ] Create seeder function to insert science_modules.json data into Supabase
- [ ] Run seeder on app start if tables are empty

## 6. Add User Authentication (Optional)
- [ ] Implement login/signup if needed
- [ ] Update API calls to include user_id

## 7. Set Up RLS Policies in Supabase
- [ ] Create policies for user data isolation

## 8. Testing and Error Handling
- [ ] Add network error handling in screens
- [ ] Test data sync between app and Supabase
- [ ] Verify schema is run in Supabase dashboard
