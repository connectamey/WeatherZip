package com.amey.weatherzip.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import com.amey.weatherzip.model.WeatherDatabase
import com.amey.weatherzip.model.WeatherData
import com.amey.weatherzip.model.api.WeatherService

import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WeatherViewModel(application: Application ) : AndroidViewModel(application) {
    val numbers = listOf("02119", "94016", "92084", "02120")
    val database = Room.databaseBuilder(application.applicationContext, WeatherDatabase::class.java, "weather_db").build()
val weatherData: WeatherData? = null
    private val weatherDao = database.weatherDao()
    fun setWeatherData() {

    val bearerToken = ""
         val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original: Request = chain.request()
                val requestBuilder: Request.Builder = original.newBuilder()
                    .header("Authorization", "Bearer $bearerToken")
                    .method(original.method(), original.body())
                val request: Request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()
         val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://weather.hereapi.com")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
         val weatherService: WeatherService? = retrofit.create(WeatherService::class.java)
        for (number in numbers){
            weatherService?.getWeatherReport("observation", number)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.onErrorResumeNext(Observable.empty())
                ?.subscribe { response ->
                    // Handle the weather data response here
                    Log.d("Weather Response", response.toString())
                    Log.d("Temperature is", response.places.firstOrNull()?.observations?.firstOrNull()?.temperature.toString())
                    val weatherData = WeatherData(
                        zipCode = number,
                        city = response.places?.first()?.observations?.first()?.place?.address?.city?: "",
                        state = response.places?.first()?.observations?.first()?.place?.address?.state?: "",
                        temperature = response.places.firstOrNull()?.observations?.firstOrNull()?.temperature ?: 20.0,
                        description = response.places.firstOrNull()?.observations?.firstOrNull()?.description ?: ""
                    )
                    GlobalScope.launch {
                        weatherDao.insertWeatherData(weatherData)
                    }
                }
        }    }


    fun getWeatherData(selectedZipCode: String): Observable<Observable<WeatherData?>>? {
        return Observable.create { emitter ->
            try {
                val response = weatherDao.getWeatherData(selectedZipCode)
                emitter.onNext(response)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorResumeNext(Observable.empty())
    }
}
