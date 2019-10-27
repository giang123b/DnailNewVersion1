package com.example.dnail3

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Rect
import android.location.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dnail2.Products.Model
import com.example.dnail2.Products.ModelAdapter
import com.example.dnail2.Times.Time
import com.example.dnail2.Times.TimeAdapter
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_5search_worker.*
import java.text.SimpleDateFormat
import java.util.*


class SearchWorkerFragment : Fragment(), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private var locationUpdateState = false

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val REQUEST_CHECK_SETTINGS = 2
        private const val PLACE_PICKER_REQUEST = 3
    }

    private lateinit var searchView: SearchView
    private lateinit var text_linearTimeLocation_enterLocation: TextView
    private lateinit var text_linearTimeLocation_enterTime: TextView
    private lateinit var button_linearChooseTime_ok: MaterialButton

    private lateinit var text_chooseTime_day1: TextView
    private lateinit var text_chooseTime_day2: TextView
    private lateinit var text_chooseTime_day3: TextView
    private lateinit var text_chooseTime_morning: TextView
    private lateinit var text_chooseTime_afternoon: TextView
    private lateinit var text_chooseTime_night: TextView

    private lateinit var recyclerView_timeMorning: RecyclerView
    private lateinit var recyclerView_timeAfternoon: RecyclerView
    private lateinit var recyclerView_timeNight: RecyclerView

    private lateinit var button_linearTimeLocation_searchWorker: MaterialButton

    private var adapterMorning: TimeAdapter? = null
    private var adapterAfternoon: TimeAdapter? = null
    private var adapterNight: TimeAdapter? = null
    private var timeListMorning: MutableList<Time>? = null
    private var timeListAfternoon: MutableList<Time>? = null
    private var timeListNight: MutableList<Time>? = null

    private lateinit var txtMoney: TextView
    private var adapter: ModelAdapter? = null
    private var adapterPhu: ModelAdapter? = null
    private var modelList: MutableList<Model>? = null
    private var modelListPhu: MutableList<Model>? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewPhu: RecyclerView

    private lateinit var text_linearBookSuccessful_selectedModel: TextView
    private lateinit var text_linearBookSuccessful_selectedModelPrice: TextView

    private lateinit var image_linearBookSuccessful_selectedModel: ImageView
    private lateinit var imageView_linearBookSuccessful_threeDots: ImageView

    private lateinit var imageView_three_dots: ImageView

    private lateinit var linear_price: LinearLayout
    private lateinit var imageView_esc_in_way_to_pay: ImageView

    private lateinit var text_promotion: TextView
    private lateinit var imageView_esc_in_promotion: ImageView
    private lateinit var text_note: TextView
    private lateinit var imageView_esc_in_note: ImageView
    private lateinit var button_linearModel_bookWorker: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root :View = inflater.inflate(R.layout.fragment_5search_worker, container, false)

        setMap()
        onMapSearch(root)
        addEvents(root)

        setRecyclerView(root)
        setRecyclerViewPhu(root)

        return root
    }


    // Events
    private fun addEvents(root: View) {
        text_linearTimeLocation_enterLocation = root.findViewById(R.id.text_linearTimeLocation_enterLocation)
        text_linearTimeLocation_enterTime = root.findViewById(R.id.text_linearTimeLocation_enterTime)
        button_linearChooseTime_ok = root.findViewById(R.id.button_linearChooseTime_ok)
        button_linearTimeLocation_searchWorker = root.findViewById(R.id.button_linearTimeLocation_searchWorker)
        imageView_three_dots = root.findViewById(R.id.imageView_three_dots)
        imageView_linearBookSuccessful_threeDots = root.findViewById(R.id.imageView_linearBookSuccessful_threeDots)

        text_linearTimeLocation_enterLocation.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                linear_chooseTime.setVisibility(GONE)
                button_linearTimeLocation_searchWorker.setVisibility(GONE)
                linear_selectModel.setVisibility(GONE)
                searchView.visibility = View.VISIBLE

                button_linearSearchLocation_ok.setVisibility(View.VISIBLE)
                linear_time_locaion.setVisibility(GONE)

                button_linearSearchLocation_ok.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        text_linearTimeLocation_enterLocation.setText(searchView.query)
                        linear_time_locaion.setVisibility(View.VISIBLE)
                        searchView.visibility = View.GONE
                        button_linearTimeLocation_searchWorker.setVisibility(View.VISIBLE)
                        button_linearSearchLocation_ok.setVisibility(View.GONE)
                    }
                })
            }

        })

        text_linearTimeLocation_enterTime.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                linear_chooseTime.setVisibility(View.VISIBLE)

                button_linearChooseTime_ok.setVisibility(View.VISIBLE)

                button_linearTimeLocation_searchWorker.setVisibility(GONE)

                linear_selectModel.setVisibility(GONE)

                button_linearSearchLocation_ok.setVisibility(GONE)

                val calendar = Calendar.getInstance()

                val simpleDateFormatDay = SimpleDateFormat("dd/MM")

                text_linearTimeLocation_enterTime.setText(simpleDateFormatDay.format(calendar.time))
            }
        })

        button_linearChooseTime_ok.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                linear_chooseTime.setVisibility(GONE)
                button_linearTimeLocation_searchWorker.setVisibility(View.VISIBLE)
                button_linearChooseTime_ok.setVisibility(GONE)
            }
        })

        //set linear choose time
        setLinearChooseTime(root)

        button_linearTimeLocation_searchWorker.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                text_linearTimeLocatin_searchWorker.setVisibility(View.VISIBLE)
                button_linearTimeLocation_searchWorker.setText(R.string.text_huy)
                timeCountDown()
            }
        })

        imageView_three_dots.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (v != null) {
                    showPopupMenuInSelectModel(v)
                }
            }
        })

        imageView_linearBookSuccessful_threeDots.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (v != null) {
                    showPopupMenuInBookSuccessful(v)
                }
            }
        })

        //        bottom bar
        setBottomBar(root)

        //        set linear choose time
        setLinearChooseTime(root)

    }

    // set linear choose time
    var day = ""
    private fun setLinearChooseTime(view: View) {

        text_chooseTime_day1 = view.findViewById(R.id.text_chooseTime_day1)
        text_chooseTime_day2 = view.findViewById(R.id.text_chooseTime_day2)
        text_chooseTime_day3 = view.findViewById(R.id.text_chooseTime_day3)

        val calendar = Calendar.getInstance()

        val simpleDateFormatDay = SimpleDateFormat("dd")
        val simpleDateFormatMonth = SimpleDateFormat("MM")
        val month = simpleDateFormatMonth.format(calendar.time)

        val day1 = Integer.parseInt(simpleDateFormatDay.format(calendar.time)).toString()
        val day2 = (Integer.parseInt(simpleDateFormatDay.format(calendar.time)) + 1).toString()
        val day3 = (Integer.parseInt(simpleDateFormatDay.format(calendar.time)) + 2).toString()

        text_chooseTime_day1.setText("$day1/$month")
        text_chooseTime_day2.setText("$day2/$month")
        text_chooseTime_day3.setText("$day3/$month")

        day = text_chooseTime_day1.getText() as String

        text_chooseTime_day1.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                text_chooseTime_day1.setBackgroundResource(R.drawable.shape_round_full_pink)
                text_chooseTime_day1.setTextColor(resources.getColor(R.color.white))
                text_chooseTime_day2.setBackgroundResource(R.drawable.shape_round_not_full_pink)
                text_chooseTime_day2.setTextColor(resources.getColor(R.color.pink_theme))
                text_chooseTime_day3.setBackgroundResource(R.drawable.shape_round_not_full_pink)
                text_chooseTime_day3.setTextColor(resources.getColor(R.color.pink_theme))

                day = text_chooseTime_day1.getText() as String
                text_linearTimeLocation_enterTime.setText(day)
            }
        })

        text_chooseTime_day2.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                text_chooseTime_day2.setBackgroundResource(R.drawable.shape_round_full_pink)
                text_chooseTime_day2.setTextColor(resources.getColor(R.color.white))
                text_chooseTime_day1.setBackgroundResource(R.drawable.shape_round_not_full_pink)
                text_chooseTime_day1.setTextColor(resources.getColor(R.color.pink_theme))
                text_chooseTime_day3.setBackgroundResource(R.drawable.shape_round_not_full_pink)
                text_chooseTime_day3.setTextColor(resources.getColor(R.color.pink_theme))

                day = text_chooseTime_day2.getText() as String
                text_linearTimeLocation_enterTime.setText(day)
            }
        })

        text_chooseTime_day3.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                text_chooseTime_day3.setBackgroundResource(R.drawable.shape_round_full_pink)
                text_chooseTime_day3.setTextColor(resources.getColor(R.color.white))
                text_chooseTime_day1.setBackgroundResource(R.drawable.shape_round_not_full_pink)
                text_chooseTime_day1.setTextColor(resources.getColor(R.color.pink_theme))
                text_chooseTime_day2.setBackgroundResource(R.drawable.shape_round_not_full_pink)
                text_chooseTime_day2.setTextColor(resources.getColor(R.color.pink_theme))

                day = text_chooseTime_day3.getText() as String
                text_linearTimeLocation_enterTime.setText(day)
            }
        })

        text_chooseTime_morning = view.findViewById(R.id.text_chooseTime_morning)
        text_chooseTime_afternoon = view.findViewById(R.id.text_chooseTime_afternoon)
        text_chooseTime_night = view.findViewById(R.id.text_chooseTime_night)

        text_chooseTime_morning.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                text_chooseTime_morning.setBackgroundResource(R.drawable.shape_round_full_pink)
                text_chooseTime_morning.setTextColor(resources.getColor(R.color.white))
                text_chooseTime_afternoon.setBackgroundResource(R.drawable.shape_round_not_full_pink)
                text_chooseTime_afternoon.setTextColor(resources.getColor(R.color.pink_theme))
                text_chooseTime_night.setBackgroundResource(R.drawable.shape_round_not_full_pink)
                text_chooseTime_night.setTextColor(resources.getColor(R.color.pink_theme))

                recyclerView_timeMorning.setVisibility(View.VISIBLE)
                recyclerView_timeAfternoon.setVisibility(GONE)
                recyclerView_timeNight.setVisibility(GONE)
            }
        })

        text_chooseTime_afternoon.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                text_chooseTime_afternoon.setBackgroundResource(R.drawable.shape_round_full_pink)
                text_chooseTime_afternoon.setTextColor(resources.getColor(R.color.white))
                text_chooseTime_morning.setBackgroundResource(R.drawable.shape_round_not_full_pink)
                text_chooseTime_morning.setTextColor(resources.getColor(R.color.pink_theme))
                text_chooseTime_night.setBackgroundResource(R.drawable.shape_round_not_full_pink)
                text_chooseTime_night.setTextColor(resources.getColor(R.color.pink_theme))

                recyclerView_timeMorning.setVisibility(GONE)
                recyclerView_timeAfternoon.setVisibility(View.VISIBLE)
                recyclerView_timeNight.setVisibility(GONE)
            }
        })

        text_chooseTime_night.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                text_chooseTime_night.setBackgroundResource(R.drawable.shape_round_full_pink)
                text_chooseTime_night.setTextColor(resources.getColor(R.color.white))
                text_chooseTime_afternoon.setBackgroundResource(R.drawable.shape_round_not_full_pink)
                text_chooseTime_afternoon.setTextColor(resources.getColor(R.color.pink_theme))
                text_chooseTime_morning.setBackgroundResource(R.drawable.shape_round_not_full_pink)
                text_chooseTime_morning.setTextColor(resources.getColor(R.color.pink_theme))

                recyclerView_timeMorning.setVisibility(GONE)
                recyclerView_timeAfternoon.setVisibility(GONE)
                recyclerView_timeNight.setVisibility(View.VISIBLE)
            }
        })

        recyclerView_timeMorning = view.findViewById(R.id.recyclerView_timeMorning)
        recyclerView_timeAfternoon = view.findViewById(R.id.recyclerView_timeAfternoon)
        recyclerView_timeNight = view.findViewById(R.id.recyclerView_timeNight)

        rcMorning(view)
        rcAfternoon(view)
        rcNight(view)
    }

    // bottom bar
    private fun setBottomBar(view: View) {

// control
        linear_price = view.findViewById(R.id.linear_price)
        imageView_esc_in_way_to_pay = view.findViewById(R.id.imageView_esc_in_way_to_pay)
        text_promotion = view.findViewById(R.id.text_promotion)
        imageView_esc_in_promotion = view.findViewById(R.id.imageView_esc_in_promotion)
        text_note = view.findViewById(R.id.text_note)
        imageView_esc_in_note = view.findViewById(R.id.imageView_esc_in_note)
        button_linearModel_bookWorker = view.findViewById(R.id.button_linearModel_bookWorker)

// event
        linear_price.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                linear_way_to_pay.setVisibility(View.VISIBLE)
                linear_promotion.setVisibility(View.GONE)
                linear_note.setVisibility(View.GONE)
                app_bar_bottom.setVisibility(View.GONE)
                button_linearModel_bookWorker.setVisibility(View.GONE)
            }
        })

        imageView_esc_in_way_to_pay.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                linear_way_to_pay.setVisibility(View.GONE)
                app_bar_bottom.setVisibility(View.VISIBLE)
                button_linearModel_bookWorker.setVisibility(View.VISIBLE)
            }
        })

        text_promotion.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                linear_promotion.setVisibility(View.VISIBLE)
                linear_way_to_pay.setVisibility(View.GONE)
                linear_note.setVisibility(View.GONE)
                app_bar_bottom.setVisibility(View.GONE)
                button_linearModel_bookWorker.setVisibility(View.GONE)
            }
        })

        imageView_esc_in_promotion.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                linear_promotion.setVisibility(View.GONE)
                app_bar_bottom.setVisibility(View.VISIBLE)
                button_linearModel_bookWorker.setVisibility(View.VISIBLE)
            }
        })

        text_note.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                linear_note.setVisibility(View.VISIBLE)
                linear_way_to_pay.setVisibility(View.GONE)
                linear_promotion.setVisibility(View.GONE)
                app_bar_bottom.setVisibility(View.GONE)
                button_linearModel_bookWorker.setVisibility(View.GONE)
            }
        })

        imageView_esc_in_note.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                linear_note.setVisibility(View.GONE)
                app_bar_bottom.setVisibility(View.VISIBLE)
                button_linearModel_bookWorker.setVisibility(View.VISIBLE)
            }
        })

        button_linearModel_bookWorker.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                linear_bookSuccessful.setVisibility(View.VISIBLE)
                linear_time_locaion.setVisibility(View.GONE)
                linear_selectModel.setVisibility(View.GONE)
                button_linearModel_bookWorker.setVisibility(View.GONE)

                text_linearBookSuccessful_time.setText(getString(R.string.text_thoi_gian) + text_linearTimeLocation_enterTime.getText())
                text_linearBookSuccessful_location.setText(getString(R.string.text_dia_diem) + text_linearTimeLocation_enterLocation.getText())
            }
        })
    }

    //    Map
    fun onMapSearch(root:View) {
        searchView = root.findViewById(R.id.searchLocation) as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val location = searchView.query.toString()
                var addressList: List<Address>? = null

                if (location != null || location != "") {
                    val geocoder = Geocoder(context)
                    try {
                        addressList = geocoder.getFromLocationName(location, 1)

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                    val address = addressList!![0]
                    val latLng = LatLng(address.latitude, address.longitude)
                    map.addMarker(MarkerOptions().position(latLng).title(location))
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                }

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun setMap() {
        val mapFragment = this.childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)

                lastLocation = p0.lastLocation
                placeMarkerOnMap(LatLng(lastLocation.latitude, lastLocation.longitude))
            }
        }

        createLocationRequest()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                locationUpdateState = true
                startLocationUpdates()
            }
        }
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                val place = PlacePicker.getPlace(context, data)
                var addressText = place.name.toString()
                addressText += "\n" + place.address.toString()

//                placeMarkerOnMap(place.latLng)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onResume() {
        super.onResume()
        if (!locationUpdateState) {
            startLocationUpdates()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)

        setUpMap()
    }

    override fun onMarkerClick(p0: Marker?) = false

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(
                this!!.context!!,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this!!.activity!!,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        map.isMyLocationEnabled = true
        map.mapType = GoogleMap.MAP_TYPE_NORMAL

        fusedLocationClient.lastLocation.addOnSuccessListener(this!!.activity!!){
                location ->
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(currentLatLng)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
            }
        }

    }

    private fun placeMarkerOnMap(location: LatLng) {
        val markerOptions = MarkerOptions().position(location)

        val titleStr = getAddress(location)  // add these two lines
        markerOptions.title(titleStr)

        map.addMarker(markerOptions)
    }

    private fun getAddress(latLng: LatLng): String {
        // 1
        val geocoder = Geocoder(context)
        val addresses: List<Address>?
        val address: Address?
        var addressText = ""

        try {
            // 2
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            // 3
            if (null != addresses && !addresses.isEmpty()) {
                address = addresses[0]
                for (i in 0 until address.maxAddressLineIndex) {
                    addressText += if (i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(i)
                }
            }
        } catch (e: IOException) {
            Log.e("MapsActivity", e.localizedMessage)
        }

        return addressText
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this!!.context!!,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this!!.activity!!,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null /* Looper */)
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(this!!.activity!!)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            locationUpdateState = true
            startLocationUpdates()
        }
        task.addOnFailureListener { e ->
            if (e is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    e.startResolutionForResult(activity,
                        REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    private fun loadPlacePicker() {
        val builder = PlacePicker.IntentBuilder()

        try {
            startActivityForResult(builder.build(activity), PLACE_PICKER_REQUEST)
        } catch (e: GooglePlayServicesRepairableException) {
            e.printStackTrace()
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        }
    }


    private fun timeCountDown() {
        val w = object : CountDownTimer(1000, 1000) {
            override fun onTick(mil: Long) {

            }

            override fun onFinish() {
                text_linearTimeLocatin_searchWorker.visibility = GONE
                button_linearTimeLocation_searchWorker.visibility = GONE
                linear_selectModel.visibility = View.VISIBLE
                button_linearTimeLocation_searchWorker.setText(R.string.text_tim_tho_mong)
                button_linearModel_bookWorker.visibility = View.VISIBLE
            }
        }.start()
    }

    private fun showPopupMenuInSelectModel(view: View) {
        // inflate menu
        val popup = PopupMenu(context, view)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.menu_refresh_woker, popup.menu)
        popup.setOnMenuItemClickListener(MyMenuItemClickListener())
        popup.show()
    }

    internal var refesh = 1
    internal inner class MyMenuItemClickListener : PopupMenu.OnMenuItemClickListener {

        override fun onMenuItemClick(menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {
                R.id.refresh_worker -> {
                    if (refesh > 0) {
                        refesh = -1
                        linear_selectModel.visibility = View.GONE
                        recyclerView_model.setVisibility(View.GONE)

                        text_linearTimeLocatin_searchWorker.visibility = View.VISIBLE
                        recyclerView_model1.setVisibility(View.VISIBLE)

                        text_linearSelectModel_vote.setVisibility(View.GONE)
                        text_linearSelectModel_vote1.setVisibility(View.VISIBLE)

                        timeCountDown()
                    } else {
                        refesh = 1
                        linear_selectModel.visibility = View.GONE
                        recyclerView_model1.setVisibility(GONE)

                        text_linearTimeLocatin_searchWorker.visibility = View.VISIBLE
                        recyclerView_model.setVisibility(View.VISIBLE)

                        text_linearSelectModel_vote1.setVisibility(View.GONE)
                        text_linearSelectModel_vote.setVisibility(View.VISIBLE)

                        timeCountDown()
                    }
                    return true
                }
            }
            return false
        }
    }

    private fun showPopupMenuInBookSuccessful(view: View) {
        // inflate menu
        val popup = PopupMenu(context, view)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.menu_complete_book, popup.menu)
        popup.setOnMenuItemClickListener(MyMenuItemClickListenerInBookSuccessful())
        popup.show()
    }

    /**
     * Click listener for popup menu items
     */
    internal inner class MyMenuItemClickListenerInBookSuccessful :
        PopupMenu.OnMenuItemClickListener {

        override fun onMenuItemClick(menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {
                R.id.refresh_worker -> {
                    linear_bookSuccessful.visibility = View.GONE
                    linear_time_locaion.visibility = View.VISIBLE
                    button_linearTimeLocation_searchWorker.visibility = View.VISIBLE
                    return true
                }
            }
            return false
        }
    }

    //    Choose time
    private fun rcNight(view: View) {
        timeListNight = ArrayList<Time>()
        adapterNight = TimeAdapter(
            this!!.context!!, timeListNight as ArrayList<Time>, text_linearTimeLocation_enterTime,
            text_linearTimeLocation_enterTime.text as String
        )

        val mLayoutManager = GridLayoutManager(context, 3)
        recyclerView_timeNight.setLayoutManager(mLayoutManager)
        recyclerView_timeNight.addItemDecoration(GridSpacingItemDecoration(3, dpToPx(10), true))
        recyclerView_timeNight.setItemAnimator(DefaultItemAnimator())
        recyclerView_timeNight.setAdapter(adapterNight)

        prepareTimeNight()
    }

    private fun rcAfternoon(view: View) {

        timeListAfternoon = ArrayList<Time>()
        //        chooseDay(view);
        adapterAfternoon = TimeAdapter(
            this!!.context!!,
            timeListAfternoon as ArrayList<Time>, text_linearTimeLocation_enterTime,
            text_linearTimeLocation_enterTime.text as String
        )

        val mLayoutManager = GridLayoutManager(context, 3)
        recyclerView_timeAfternoon.setLayoutManager(mLayoutManager)
        recyclerView_timeAfternoon.addItemDecoration(GridSpacingItemDecoration(3, dpToPx(10), true))
        recyclerView_timeAfternoon.setItemAnimator(DefaultItemAnimator())
        recyclerView_timeAfternoon.setAdapter(adapterAfternoon)

        //        hour = adapterAfternoon.getHour();
        //        text_linearTimeLocation_enterTime.setText(hour + " - " + day);
        prepareTimeAfternoon()

    }

    private fun rcMorning(view: View) {

        timeListMorning = ArrayList<Time>()
        //        chooseDay(view);
        adapterMorning = TimeAdapter(
            this!!.context!!, timeListMorning as ArrayList<Time>, text_linearTimeLocation_enterTime,
            text_linearTimeLocation_enterTime.text as String
        )

        val mLayoutManager = GridLayoutManager(context, 3)
        recyclerView_timeMorning.setLayoutManager(mLayoutManager)
        recyclerView_timeMorning.addItemDecoration(GridSpacingItemDecoration(3, dpToPx(10), true))
        recyclerView_timeMorning.setItemAnimator(DefaultItemAnimator())
        recyclerView_timeMorning.setAdapter(adapterMorning)

        //        hour = adapterMorning.getHour();
        //        text_linearTimeLocation_enterTime.setText(hour + " - " + day);
        prepareTimeMoring()
    }

    private fun prepareTimeMoring() {

        var a = Time("8:00")
        timeListMorning?.add(a)

        a = Time("8:30")
        timeListMorning?.add(a)

        a = Time("9:00")
        timeListMorning?.add(a)

        a = Time("9:30")
        timeListMorning?.add(a)

        a = Time("10:00")
        timeListMorning?.add(a)

        a = Time("10:30")
        timeListMorning?.add(a)

        a = Time("11:00")
        timeListMorning?.add(a)

        a = Time("11:30")
        timeListMorning?.add(a)

        adapterMorning?.notifyDataSetChanged()
    }

    private fun prepareTimeAfternoon() {

        var a = Time("12:00")
        timeListAfternoon?.add(a)

        a = Time("12:30")
        timeListAfternoon?.add(a)

        a = Time("13:00")
        timeListAfternoon?.add(a)

        a = Time("13:30")
        timeListAfternoon?.add(a)

        a = Time("14:00")
        timeListAfternoon?.add(a)

        a = Time("14:30")
        timeListAfternoon?.add(a)

        a = Time("15:00")
        timeListAfternoon?.add(a)

        a = Time("15:30")
        timeListAfternoon?.add(a)

        a = Time("16:00")
        timeListAfternoon?.add(a)

        a = Time("16:30")
        timeListAfternoon?.add(a)

        a = Time("17:00")
        timeListAfternoon?.add(a)

        a = Time("17:30")
        timeListAfternoon?.add(a)


        adapterAfternoon?.notifyDataSetChanged()
    }

    private fun prepareTimeNight() {

        var a = Time("18:00")
        timeListNight?.add(a)

        a = Time("18:30")
        timeListNight?.add(a)

        a = Time("19:00")
        timeListNight?.add(a)

        a = Time("19:30")
        timeListNight?.add(a)
        a = Time("20:00")
        timeListNight?.add(a)

        a = Time("20:30")
        timeListNight?.add(a)

        a = Time("21:00")
        timeListNight?.add(a)

        a = Time("21:30")
        timeListNight?.add(a)

        adapterNight?.notifyDataSetChanged()
    }

    inner class GridSpacingItemDecoration(
        private val spanCount: Int,
        private val spacing: Int,
        private val includeEdge: Boolean
    ):
        RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view) // item position
            val column = position % spanCount // item column

            if (includeEdge) {
                outRect.left =
                    spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
                outRect.right =
                    (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing
                }
                outRect.bottom = spacing // item bottom
            } else {
                outRect.left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
                outRect.right =
                    spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing // item top
                }
            }
        }
    }

    private fun dpToPx(dp: Int): Int {
        val r = resources
        return Math.round(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp.toFloat(),
                r.displayMetrics
            )
        )
    }

    //    RecyclerView mau mong chinh
    private fun setRecyclerView(root: View) {
        txtMoney = root.findViewById(R.id.txtMoney)
        recyclerView = root.findViewById(R.id.recyclerView_model)
        recyclerViewPhu = root.findViewById(R.id.recyclerView_model1)
        text_linearBookSuccessful_selectedModel = root.findViewById(R.id.text_linearBookSuccessful_selectedModel)
        text_linearBookSuccessful_selectedModelPrice = root.findViewById(R.id.text_linearBookSuccessful_selectedModelPrice)
        image_linearBookSuccessful_selectedModel = root.findViewById(R.id.image_linearBookSuccessful_selectedModel)

        modelList = ArrayList<Model>()
        adapter = ModelAdapter(
            this!!.context!!,
            modelList as ArrayList<Model>, txtMoney, text_linearBookSuccessful_selectedModel,
            text_linearBookSuccessful_selectedModelPrice, image_linearBookSuccessful_selectedModel
        )

        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setLayoutManager(mLayoutManager)
        recyclerView.setAdapter(adapter)

        prepareAlbums()
    }

    private fun prepareAlbums() {
        val covers = intArrayOf(
            R.drawable.md1,
            R.drawable.md2,
            R.drawable.md4,
            R.drawable.md5,
            R.drawable.md6,
            R.drawable.md3,
            R.drawable.md7,
            R.drawable.md8,
            R.drawable.md9,
            R.drawable.md10,
            R.drawable.md11,
            R.drawable.md12,
            R.drawable.md13
        )

        var a = Model("Mẫu 1", 120, covers[0])
        modelList?.add(a)

        a = Model("Mẫu 2", 220, covers[1])
        modelList?.add(a)

        a = Model("Mẫu 3", 125, covers[2])
        modelList?.add(a)

        a = Model("Mẫu 4", 240, covers[3])
        modelList?.add(a)

        a = Model("Mẫu 5", 200, covers[4])
        modelList?.add(a)

        a = Model("Mẫu 6", 150, covers[5])
        modelList?.add(a)

        a = Model("Mẫu 7", 400, covers[6])
        modelList?.add(a)

        a = Model("Mẫu 8", 110, covers[7])
        modelList?.add(a)

        a = Model("Mẫu 9", 90, covers[8])
        modelList?.add(a)

        a = Model("Mẫu 10", 100, covers[9])
        modelList?.add(a)

        adapter?.notifyDataSetChanged()
    }

    // recyclerview mau mong  Phu
    private fun setRecyclerViewPhu(root: View) {
        txtMoney = root.findViewById(R.id.txtMoney)

        modelListPhu = ArrayList()
        adapterPhu = ModelAdapter(
            this!!.context!!,
            modelListPhu as ArrayList<Model>, txtMoney, text_linearBookSuccessful_selectedModel,
            text_linearBookSuccessful_selectedModelPrice, image_linearBookSuccessful_selectedModel
        )

        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewPhu.layoutManager = mLayoutManager
        recyclerViewPhu.adapter = adapterPhu

        prepareAlbumsPhu()
    }

    private fun prepareAlbumsPhu() {
        val covers = intArrayOf(
            R.drawable.md10,
            R.drawable.md11,
            R.drawable.md12,
            R.drawable.md13,
            R.drawable.md6,
            R.drawable.md3,
            R.drawable.md7,
            R.drawable.md8,
            R.drawable.md9,
            R.drawable.md1,
            R.drawable.md2,
            R.drawable.md4,
            R.drawable.md5
        )

        var a = Model("Mẫu 1", 250, covers[0])
        modelListPhu?.add(a)

        a = Model("Mẫu 2", 100, covers[1])
        modelListPhu?.add(a)

        a = Model("Mẫu 3", 90, covers[2])
        modelListPhu?.add(a)

        a = Model("Mẫu 4", 400, covers[3])
        modelListPhu?.add(a)

        a = Model("Mẫu 5", 75, covers[4])
        modelListPhu?.add(a)

        a = Model("Mẫu 6", 200, covers[5])
        modelListPhu?.add(a)

        a = Model("Mẫu 7", 120, covers[6])
        modelListPhu?.add(a)

        a = Model("Mẫu 8", 110, covers[7])
        modelListPhu?.add(a)

        a = Model("Mẫu 9", 90, covers[8])
        modelListPhu?.add(a)

        a = Model("Mẫu 10", 100, covers[9])
        modelListPhu?.add(a)

        adapterPhu?.notifyDataSetChanged()
    }
}
