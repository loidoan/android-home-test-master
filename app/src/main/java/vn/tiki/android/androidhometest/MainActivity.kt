package vn.tiki.android.androidhometest

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import vn.tiki.android.androidhometest.data.api.ApiServices
import vn.tiki.android.androidhometest.data.api.response.Deal
import vn.tiki.android.androidhometest.di.initDependencies
import vn.tiki.android.androidhometest.di.inject
import vn.tiki.android.androidhometest.di.releaseDependencies
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import vn.tiki.android.androidhometest.adapter.DealAdapter

class MainActivity : AppCompatActivity() {

  val apiServices by inject<ApiServices>()
  // Initializing an empty ArrayList to be filled with animals
  var deals: MutableList<Deal> = ArrayList()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initDependencies(this)

    setContentView(R.layout.activity_main)

    loadData()

    // Creates a vertical Layout Manager
    rv_deal_list.layoutManager = LinearLayoutManager(this)

    // You can use GridLayoutManager if you want multiple columns. Enter the number of columns as a parameter.
    rv_deal_list.layoutManager = GridLayoutManager(this, 2)

  }

  private fun loadData() {
    object : AsyncTask<Unit, Unit, MutableList<Deal>>() {
      override fun doInBackground(vararg params: Unit?): MutableList<Deal> {
        return apiServices.getDeals()
      }

      override fun onPostExecute(result: MutableList<Deal>?) {
        super.onPostExecute(result)
        deals = result!!
        updateData()
      }
    }.execute()
  }

  private fun updateData() {
    // Access the RecyclerView Adapter and load the data into it
    rv_deal_list.adapter = DealAdapter(deals, this)
  }

  override fun onDestroy() {
    super.onDestroy()
    releaseDependencies()
  }
}
