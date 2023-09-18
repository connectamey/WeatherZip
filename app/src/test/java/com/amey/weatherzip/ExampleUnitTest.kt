package com.amey.weatherzip

import android.util.Log
import androidx.room.Room
import com.amey.weatherzip.model.WeatherData
import com.amey.weatherzip.model.WeatherDatabase
import com.amey.weatherzip.model.api.WeatherService
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito.mock
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
@Test
fun test_setWeatherData()
{

    val bearerToken = "eyJhbGciOiJSUzUxMiIsImN0eSI6IkpXVCIsImlzcyI6IkhFUkUiLCJhaWQiOiJHenJWV2pOVU9WZWJnR3A2eG9sbCIsImlhdCI6MTY5Mzk1ODYzOSwiZXhwIjoxNjk0MDQ1MDM5LCJraWQiOiJqMSJ9.ZXlKaGJHY2lPaUprYVhJaUxDSmxibU1pT2lKQk1qVTJRMEpETFVoVE5URXlJbjAuLksteFBRQ3JQU3hoUFp0MkVNQkFyUVEuZ2xGdDZXcGtnYlM5WXNpOUlyUlBtYkVValpyU0lNSXlDUEtHR2dCY3BDVnJDWWxnSEt3RzZuMUVkdllEZG5xMXhXX1dLNlR1MXRwT1ZKTVdLLW9OVE5JU0U2aXl4OXZ3TTFKUWo5MUhMLTQ5OU1ycE56Njgtb2ppcEVDM1RGX3pCVFh5Ylh6aVVPaWN0MFJtSEZMX0pEUE00U2I2Vzc5WnhaTzlRUEVyVHplQUk3YnN3N2MwaVRIMWMxN2xoZ3pHVVNVc05uaFRaQ29HQkZVQzQ3ejZkLTYtVmlKM2pwVlJydlo4ZTA4cWlJOC5qU0lnRWwtQV9TX0hYWW9LbWZiZ3hhMkZmZmtMN0xNTWRraWM2dnk0ZkFv.hHQVRRDnS7u4bipsbBC9VmK9UeTlJi1N5Vf3bpL-K7_VuJTFTApwegIypV0LBh6Zi9ANc58GuzyRinL4he67AzKULPZ9gBUrPoZTcEYFat88yFWAGPl1T1PhBpTsgzywoQjs3pOraf5IjAs214mmVY592ycVcJyVIg10ZGlRRRQE5hkfGmgnEz80kGEJdLlR6RNGeMnNq1fL--PMQZPx6LdPvOJiqk19iZEBwkVplbbC7ZTC-Rs4LwTkHbNjEiWHVRUJT1lhP6hBQUgUMMkgDS1LJo2Kck6JPa2tGmeKClaST_4yQbHodpaq7msGEkze6f2liRwXoFepaQfKy7fS8g"
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

    weatherService?.getWeatherReport("observation", "02119")
        ?.subscribeOn(Schedulers.io())
        ?.observeOn(AndroidSchedulers.mainThread())
        ?.onErrorResumeNext(Observable.empty())
        ?.subscribe { response ->
            // Handle the weather data response here
            Log.d("Weather Response", response.toString())
            Log.d("Temperature is", response.places.firstOrNull()?.observations?.firstOrNull()?.temperature.toString())
            val weatherData = WeatherData(
                zipCode = "02119",
                city = response.places?.first()?.observations?.first()?.place?.address?.city?: "",
                state = response.places?.first()?.observations?.first()?.place?.address?.state?: "",
                temperature = response.places.firstOrNull()?.observations?.firstOrNull()?.temperature ?: 20.0,
                description = response.places.firstOrNull()?.observations?.firstOrNull()?.description ?: ""
            )

        }
}
}