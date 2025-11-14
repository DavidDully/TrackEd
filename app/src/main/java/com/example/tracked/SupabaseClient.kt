package com.example.tracked

import android.content.Context
import com.squareup.okhttp3.OkHttpClient
import com.squareup.okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.*
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import java.util.concurrent.TimeUnit

// Supabase configuration
object SupabaseManager {
    private const val SUPABASE_URL = "https://qlfbuiebjkhukhuowkgr.supabase.co"
    private const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InFsZmJ1aWViamtodWtodW93a2dyIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzE2MDY0NjIsImV4cCI6MjA0NzE4MjQ2Mn0.NnL_lmxNI3nG-pKw6eZ0xzY7Qx9Fq2kR8JtL3vU1Abc"

    private var supabaseApi: SupabaseApi? = null

    fun initialize(context: Context) {
        if (supabaseApi == null) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

            val httpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor { chain ->
                    val original = chain.request()
                    val requestBuilder = original.newBuilder()
                        .header("Authorization", "Bearer $SUPABASE_KEY")
                        .header("apikey", SUPABASE_KEY)
                        .header("Content-Type", "application/json")
                    chain.proceed(requestBuilder.build())
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
    // Reminders
    @GET("reminders")
    suspend fun getReminders(): List<ReminderData>

    @POST("reminders")
    suspend fun createReminder(@Body reminder: ReminderData): ReminderData

    @PATCH("reminders?id=eq.{id}")
    suspend fun updateReminder(@Path("id") id: String, @Body reminder: ReminderData): List<ReminderData>

    @DELETE("reminders?id=eq.{id}")
    suspend fun deleteReminder(@Path("id") id: String)

    // Modules
    @GET("modules")
    suspend fun getModules(): List<ScienceModuleData>

    @POST("modules")
    suspend fun createModule(@Body module: ScienceModuleData): ScienceModuleData

    // Topics
    @GET("topics")
    suspend fun getTopics(): List<TopicData>

    @GET("topics?module_id=eq.{moduleId}")
    suspend fun getTopicsByModule(@Path("moduleId") moduleId: String): List<TopicData>

    @POST("topics")
    suspend fun createTopic(@Body topic: TopicData): TopicData

    // Study Progress
    @GET("study_progress")
    suspend fun getStudyProgress(): List<StudyProgressData>

    @GET("study_progress?user_id=eq.{userId}")
    suspend fun getStudyProgressByUser(@Path("userId") userId: String): List<StudyProgressData>

    @POST("study_progress")
    suspend fun createStudyProgress(@Body progress: StudyProgressData): StudyProgressData

    @PATCH("study_progress?id=eq.{id}")
    suspend fun updateStudyProgress(@Path("id") id: String, @Body progress: StudyProgressData): List<StudyProgressData>
}
