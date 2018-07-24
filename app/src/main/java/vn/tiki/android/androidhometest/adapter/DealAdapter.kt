package vn.tiki.android.androidhometest.adapter

import android.content.Context
import android.os.CountDownTimer
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.tiki.android.androidhometest.R
import kotlinx.android.synthetic.main.deal_item.view.*
import vn.tiki.android.androidhometest.data.api.response.Deal
import com.squareup.picasso.Picasso

import vn.tiki.android.androidhometest.util.dateConvert


class DealAdapter(val items : MutableList<Deal>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.deal_item, parent, false))
    }

    // Binds each deal in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        Picasso.with(context).load(items.get(position).productThumbnail).into(holder?.tvPhoto)
        holder?.tvProductName?.text = items.get(position).productName
        holder?.tvPrice?.text = items.get(position).productPrice.toString() + " đ"

        if (holder?.timer != null) {
            holder?.timer?.cancel()
        }
        if (dateConvert(items.get(position).startedDate , items.get(position).endDate) > 0) {
            holder?.timer = object :  CountDownTimer(dateConvert(items.get(position).startedDate , items.get(position).endDate), 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    var millisUntilFinished = millisUntilFinished
                    val secondsInMilli: Long = 1000
                    val minutesInMilli = secondsInMilli * 60
                    val hoursInMilli = minutesInMilli * 60
                    val elapsedHours = millisUntilFinished / hoursInMilli
                    millisUntilFinished = millisUntilFinished % hoursInMilli

                    val elapsedMinutes = millisUntilFinished / minutesInMilli
                    millisUntilFinished = millisUntilFinished % minutesInMilli

                    val elapsedSeconds = millisUntilFinished / secondsInMilli

                    val yy = String.format("%02d:%02d:%02d", elapsedHours, elapsedMinutes , elapsedSeconds)
                    holder?.tvDate?.setText(yy)
                }

                override fun onFinish() {
                    items.removeAt(position)
                    notifyDataSetChanged()
                }
            }.start()

        }else{
            holder?.tvDate?.setText("00:00:00")
        }
    }

}


class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val tvPhoto = view.item_product_ivThumbnail
        val tvProductName = view.item_product_tvName
        val tvPrice = view.item_price
        val tvDate = view.item_date
        var timer: CountDownTimer? = null
}