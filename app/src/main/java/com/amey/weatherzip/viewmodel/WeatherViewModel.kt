    package com.amey.weatherzip.viewmodel

    import android.app.Application
    import android.util.Log
    import androidx.lifecycle.AndroidViewModel
    import androidx.room.Room
    import com.amey.weatherzip.model.EmptyObservableException
    import com.amey.weatherzip.model.WeatherDatabase
    import com.amey.weatherzip.model.WeatherData
    import com.amey.weatherzip.model.WeatherResponse
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
    import java.util.LinkedList
    import java.util.Queue

    class WeatherViewModel(application: Application ) : AndroidViewModel(application) {
        val apiKey = ""
        var numberQueue: Queue<String> = LinkedList()
        var database = Room.databaseBuilder(application.applicationContext, WeatherDatabase::class.java, "weather_db").build()
        val recentSearchesDatabase = Room.databaseBuilder(application.applicationContext, WeatherDatabase::class.java, "weather_db_recent_queries").build()
    val weatherData: WeatherData? = null
        var weatherResponse: WeatherResponse? = null
        private val weatherDao = database.weatherDao()
        fun addHardcodedZipCodesInQueue(){
            numberQueue.clear()
            numberQueue.add("02119")
        }

        fun getWeatherDataFromAPI(selectedZipCode: String): Observable<WeatherResponse>? {
            return Observable.create { emitter ->
                try {

                    val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                        .addInterceptor { chain ->
                            val original: Request = chain.request()
                            val requestBuilder: Request.Builder = original.newBuilder()
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

                    //observables: sources of data
                    //subscribers: listening to observables
                    //subscribeOn: does work in background
                    //observeOn: comes back to main thread after getting info
                    //subscribe: Comes back after getting info

                    val weatherService: WeatherService? = retrofit.create(WeatherService::class.java)
                    weatherService?.getWeatherReport("observation", selectedZipCode,apiKey)
                        ?.subscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.onErrorResumeNext { throwable: Throwable ->
                            if (throwable is NoSuchElementException) {
                                Observable.error(EmptyObservableException())
                            } else {
                                Observable.empty()
                            }
                        }
                        ?.subscribe { response ->
                            // Handle the weather data response here
                            Log.d("Weather Response", response.toString())
                            Log.d("Temperature is", response.places.firstOrNull()?.observations?.firstOrNull()?.temperature.toString())
                          weatherResponse = response
                            val weatherData = WeatherData(
                                zipCode = selectedZipCode,
                                city = response.places?.first()?.observations?.first()?.place?.address?.city?: "",
                                state = response.places?.first()?.observations?.first()?.place?.address?.state?: "",
                                temperature = response.places.firstOrNull()?.observations?.firstOrNull()?.temperature ?: 20.0,
                                description = response.places.firstOrNull()?.observations?.firstOrNull()?.description ?: ""
                            )
                            GlobalScope.launch {
                                weatherDao.insertWeatherData(weatherData)
                            }
                            emitter.onNext(response)
                            emitter.onComplete()
                        }


                } catch (e: Exception) {
                    emitter.onError(e)
                }
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(Observable.empty())
        }

    }
