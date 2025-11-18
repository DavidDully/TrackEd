package com.example.tracked

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.*
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import java.util.concurrent.TimeUnit

// Supabase configuration
object SupabaseManager {
    private const val SUPABASE_URL = "https://qlfbuiebjkhukhuowkgr.supabase.co"
    // ✅ Safe to use anon key in client apps
    private const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InFsZmJ1aWViamtodWtodW93a2dyIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjMxMzIxMjAsImV4cCI6MjA3ODcwODEyMH0.jsSXjP4GGw-zhfTlUKbnGYThjCgiwM1gUlQe07K_mOo"

    private var supabaseApi: SupabaseApi? = null

    fun initialize(context: Context) {
        if (supabaseApi == null) {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }

            val httpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .header("Authorization", "Bearer $SUPABASE_KEY")
                        .header("apikey", SUPABASE_KEY)
                        .header("Content-Type", "application/json")
                        .build()
                    chain.proceed(request)
                }
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

            val json = Json { ignoreUnknownKeys = true }
            val contentType = "application/json".toMediaType()

            val retrofit = Retrofit.Builder()
                .baseUrl("$SUPABASE_URL/rest/v1/")
                .client(httpClient)
                .addConverterFactory(json.asConverterFactory(contentType))
                .build()

            supabaseApi = retrofit.create(SupabaseApi::class.java)
        }
    }

    fun getApi(): SupabaseApi {
        return supabaseApi ?: throw IllegalStateException("Supabase not initialized. Call initialize() first.")
    }
}

// Supabase REST API interface
interface SupabaseApi {
    // Users
    @GET("users")
    suspend fun getUsers(): List<UserData>

    @POST("users")
    suspend fun createUser(@Body user: UserData): UserData

    // Modules
    @GET("modules")
    suspend fun getModules(): List<ScienceModuleData>

    @POST("modules")
    suspend fun createModule(@Body module: ScienceModuleData): ScienceModuleData

    // Topics
    @GET("topics")
    suspend fun getTopics(): List<TopicData>

    @POST("topics")
    suspend fun createTopic(@Body topic: TopicData): TopicData

    // Study Progress
    @GET("study_progress")
    suspend fun getStudyProgress(): List<StudyProgressData>

    @POST("study_progress")
    suspend fun createStudyProgress(@Body progress: StudyProgressData): StudyProgressData

    @PATCH("study_progress")
    suspend fun updateStudyProgress(@Query("id") id: String, @Body progress: StudyProgressData): StudyProgressData

    // Reminders
    @GET("reminders")
    suspend fun getReminders(): List<ReminderData>

    @POST("reminders")
    suspend fun createReminder(@Body reminder: ReminderData): ReminderData

    @DELETE("reminders")
    suspend fun deleteReminder(@Query("id") id: String): Unit
}
