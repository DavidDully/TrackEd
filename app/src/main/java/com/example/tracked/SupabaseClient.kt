package com.example.tracked

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient

object SupabaseManager {

    val supabase: SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://gifeewsoxxwslavoorxg.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImdpZmVld3NveHh3c2xhdm9vcnhnIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjMxNDk3ODMsImV4cCI6MjA3ODcyNTc4M30.dYeMOwaTOwVQ1pLFCjEQ0fowzCckEdZ6N5ENJ69cKFU"
    ) {
        install(io.github.jan.supabase.gotrue.GoTrue)
        install(io.github.jan.supabase.postgrest.Postgrest)
    }
}