package com.chalkbox.propertyfinder.property.details.content

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearSnapHelper
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.dto.pojos.Property
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.property_create_unit_other_details.view.*

class PropertyImagesPresenter(
    context: Context,
    identifier: String,
    sortOrder: Int,
    picasso: Picasso,
    private val imageUrls: List<String>
) : PropertyContentPresenter<Property>(identifier, sortOrder) {

    override val showDivider: Boolean = false

    override val contentResId: Int = R.layout.property_images_layout

    override val shouldShow: Boolean
        get() = imageUrls.isNotEmpty()

    private var adapter: PropertyImagesAdapter<String> =
        PropertyImagesAdapter(
            context,
            picasso,
            imageUrls
        )

    override fun bindView(view: View) {
        super.bindView(view)

        view.recycler_view.adapter = adapter
        LinearSnapHelper().attachToRecyclerView(view.recycler_view)
    }
}