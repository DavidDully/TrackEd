# Supabase Setup Guide for TrackEd

## Steps to create the database in Supabase

1. **Open your Supabase dashboard**: https://supabase.com/dashboard/project/qlfbuiebjkhukhuowkgr

2. **Go to SQL Editor**:
   - Click on **SQL Editor** in the left sidebar
   - Click **+ New Query**

3. **Copy the schema**:
   - Open `supabase_schema.sql` from this repository
   - Copy all the SQL code

4. **Paste and run**:
   - Paste the SQL into the Supabase SQL editor
   - Click **Run** (or press `Cmd+Enter` / `Ctrl+Enter`)
   - Wait for the tables to be created

5. **Verify tables were created**:
   - Go to **Table Editor** in the sidebar
   - You should see:
     - `users`
     - `reminders`
     - `science_modules`
     - `topics`
     - `study_progress`

## Database Schema Overview

| Table | Purpose |
|-------|---------|
| `users` | Stores user account information |
| `reminders` | Stores reminders with text and dates |
| `science_modules` | Stores science learning modules |
| `topics` | Stores subtopics within modules |
| `study_progress` | Tracks user progress on each topic |

## Next Steps (Android Integration)

Once the database is created, you'll need to:

1. **Get Supabase credentials**:
   - Go to **Project Settings → API** in Supabase
   - Copy the **Project URL** and **Anon Key**

2. **Add Supabase to Android**:
   - Add dependency to `app/build.gradle.kts`:
   ```kotlin
   implementation("io.github.supabase:postgrest-kt:X.X.X")
   implementation("io.github.supabase:realtime-kt:X.X.X")
   ```

3. **Update RemindersScreen** to fetch/save from Supabase instead of SharedPreferences

4. **Set up Row Level Security (RLS)** policies to protect user data

## Resources

- [Supabase Documentation](https://supabase.com/docs)
- [Supabase Android/Kotlin Integration](https://supabase.com/docs/reference/kotlin/introduction)
