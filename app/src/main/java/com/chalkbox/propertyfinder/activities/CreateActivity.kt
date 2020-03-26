package com.chalkbox.propertyfinder.activities

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.base.presenters.Presenter
import com.chalkbox.propertyfinder.navigation.Navigator
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_create.*

abstract class CreateActivity : AppCompatActivity() {

    private lateinit var mInterstitialAd: InterstitialAd

    protected open val nextButtonTitle: String
        get() = if (nextActivity == null) {
            this.resources.getString(R.string.create_list_it)
        } else {
            this.resources.getString(R.string.create_next)
        }

    protected val presenters: MutableList<Presenter<View>> = mutableListOf()
    abstract val nextActivity: Class<*>?
    abstract val nextActivityParcel: Map<String, Parcelable>

    abstract fun setupIntentExtras()
    abstract fun setupPresenters()
    abstract fun updateInfo()
    abstract fun create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_create)

        loadAd()

        setupIntentExtras()

        setupPresenters()

        val inflater = LayoutInflater.from(this)
        for (presenter in presenters) {
            val view = inflater.inflate(presenter.resId, null, false)
            presenter.bindView(view)
            contentContainer.addView(view)
        }

        createActionButton.text = nextButtonTitle
        createActionButton.visibility = if (nextButtonTitle.isEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }

        createActionButton.setOnClickListener {
            next()
        }
    }

    protected fun checkError(): Boolean {
        val errors = presenters.filter { presenter ->
            (presenter as? CreatePresenter<*>)?.updateError()?.result == false
        }

        if (errors.isNotEmpty()) {
            val description = errors.joinToString { presenter ->
                (presenter as? CreatePresenter<*>)?.updateError()?.description ?: ""
            }.split(",").first()

            Toast.makeText(this, description, Toast.LENGTH_LONG)
                .show()

            return false
        }

        return true
    }

    protected open fun postCreate(content: Map<String, Any>) {}

    protected fun setInProgressStatus(status: Boolean) {
        if (status) {
            createActionButton.isEnabled = false
            createActionButton.text = ""
            progressBar.visibility = View.VISIBLE
            buttonProgressIndicator.visibility = View.VISIBLE
        } else {
            createActionButton.isEnabled = true
            createActionButton.text = nextButtonTitle
            progressBar.visibility = View.GONE
            buttonProgressIndicator.visibility = View.GONE
        }
    }

    protected fun loadAd() {
        MobileAds.initialize(this) {}
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-3611518346729955/9033678846"
        mInterstitialAd.loadAd(AdRequest.Builder().build())
    }

    protected fun showAd() {
        mInterstitialAd.show()
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

    protected open fun next() {
        nextActivity?.let {
            if (!checkError()) {
                return
            }

            updateInfo()

            Navigator(this).navigateTo(
                it,
                nextActivityParcel
            )
        } ?: run {
            create()
        }
    }
}