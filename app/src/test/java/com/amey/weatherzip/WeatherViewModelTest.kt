import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.amey.weatherzip.model.EmptyObservableException
import com.amey.weatherzip.model.WeatherDBDao
import com.amey.weatherzip.model.WeatherResponse
import com.amey.weatherzip.model.api.WeatherService
import com.amey.weatherzip.viewmodel.WeatherViewModel
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
@RunWith(RobolectricTestRunner::class)
class WeatherViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: WeatherViewModel

    @Mock
    private lateinit var application: Application

    @Mock
    private lateinit var weatherService: WeatherService

    private val testScheduler = TestScheduler()

    private lateinit var context: Context

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        context = ApplicationProvider.getApplicationContext()

        application = ApplicationProvider.getApplicationContext()
        viewModel = WeatherViewModel(application)

        // Initialize RxJava and RxAndroid schedulers
        RxJavaPlugins.setIoSchedulerHandler { testScheduler }
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
        RxJavaPlugins.setNewThreadSchedulerHandler { testScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }


    //Check one hardcoded zipcode is 02119
    @Test
    fun testAddHardcodedZipCodesInQueue() {
        viewModel.addHardcodedZipCodesInQueue()

        // Assert that the queue contains the expected zip code
        assert(viewModel.numberQueue.size == 1)
        assert(viewModel.numberQueue.peek() == "02119")
    }

    //Check API is showing correct location of Boston
    @Test
    fun testGetBostonWeatherDataFromAPI_Success() {
        val selectedZipCode = "02119"
        val mockWeatherResponse = Mockito.mock(WeatherResponse::class.java)

        Mockito.`when`(weatherService.getWeatherReport("observation", selectedZipCode, viewModel.apiKey))
            .thenReturn(Observable.just(mockWeatherResponse))

        val testObserver = viewModel.getWeatherDataFromAPI(selectedZipCode)?.test()
        testScheduler.triggerActions()

        // Assert that the observer received the response
        testObserver?.assertNoErrors()
            ?.assertValueCount(1)
            ?.assertValue { response: WeatherResponse ->
            response.places[0].observations[0].place.address.city == "Boston"

            }
    }

    //Check API is showing correct location of San Francisco
    @Test
    fun testGetSanFranciscoWeatherDataFromAPI_Success() {
        val selectedZipCode = "94102"
        val mockWeatherResponse = Mockito.mock(WeatherResponse::class.java)

        Mockito.`when`(weatherService.getWeatherReport("observation", selectedZipCode, viewModel.apiKey))
            .thenReturn(Observable.just(mockWeatherResponse))

        val testObserver = viewModel.getWeatherDataFromAPI(selectedZipCode)?.test()
        testScheduler.triggerActions()

        // Assert that the observer received the response
        testObserver?.assertNoErrors()
            ?.assertValueCount(1)
            ?.assertValue { response: WeatherResponse ->
                response.places[0].observations[0].place.address.city == "San Francisco"

            }
    }

    //If the zip is invalid then no data
    @Test
    fun testGetInvalidWeatherDataFromAPI_Success() {
        val selectedZipCode = "9"
        val mockWeatherResponse = Mockito.mock(WeatherResponse::class.java)

        Mockito.`when`(
            weatherService.getWeatherReport(
                "observation",
                selectedZipCode,
                viewModel.apiKey
            )
        )
            .thenReturn(Observable.just(mockWeatherResponse))

        val testObserver = viewModel.getWeatherDataFromAPI(selectedZipCode)?.test()
        testScheduler.triggerActions()

        // Assert that the observer received the response
        testObserver?.assertNoErrors()
            ?.assertValueCount(0)

            }

    }
