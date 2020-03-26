package com.chalkbox.propertyfinder.property.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.application.componentfactory.AppComponentFactory
import com.chalkbox.propertyfinder.navigation.Navigator
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_property_list.*
import javax.inject.Inject

class PropertyListActivity : AppCompatActivity() {

    @Inject
    internal lateinit var picasso: Picasso

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_property_list)

        AppComponentFactory()
            .newPropertyListActivityComponent(application)
            .inject(this)

        PropertyListPresenter(
            application = application,
            lifecycleOwner = this,
            navigator = Navigator(this),
            picasso = picasso
        ).bindView(findViewById(R.id.layout))

        // Init RecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}