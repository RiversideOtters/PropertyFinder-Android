package com.chalkbox.propertyfinder.property.create.details

import android.content.Context
import android.view.View
import com.chalkbox.propertyfinder.R
import com.chalkbox.propertyfinder.activities.CreatePresenter
import com.chalkbox.propertyfinder.activities.CreatePresenterResult
import com.chalkbox.propertyfinder.dto.pojos.PropertySettingValue
import com.chalkbox.propertyfinder.dto.pojos.User
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.create_user_details.view.*

class UserCreatePresenter(
    context: Context,
    identifier: String,
    sortOrder: Int,
    private val user: User
) : CreatePresenter<View>(identifier, sortOrder) {

    override val shouldShow: Boolean = true
    override val showDivider: Boolean = false
    override val contentResId: Int = R.layout.create_user_details

    override val heading: String = context.resources.getString(R.string.account_info_header)

    private lateinit var nameText: TextInputEditText
    private lateinit var phoneText: TextInputEditText
    private lateinit var emailText: TextInputEditText

    override fun bindView(view: View) {
        super.bindView(view)

        nameText = view.nameValue
        phoneText = view.phoneValue
        emailText = view.emailValue

        // Gender
        view.genderLayout.setTitle(view.context.getText(R.string.gender_title).toString())
        view.genderLayout.setValues(
            listOf(
                PropertySettingValue(mapOf("en_us" to "Male")),
                PropertySettingValue(mapOf("en_us" to "Female")),
                PropertySettingValue(mapOf("en_us" to "Couple"))
            )
        )
        view.genderLayout.onSettingValueChanged = { value: PropertySettingValue ->
            user.gender = value
        }
    }

    override fun updateInfo() {
        user.name = nameText.text.toString()
        user.phone = phoneText.text.toString()
        user.email = emailText.text.toString()
    }

    override fun updateError(): CreatePresenterResult =
        when {
            nameText.text.isNullOrEmpty() -> CreatePresenterResult(
                false,
                "Name is required."
            )
            phoneText.text.isNullOrEmpty() -> CreatePresenterResult(
                false,
                "Phone number are required."
            )
            emailText.text.isNullOrEmpty() -> CreatePresenterResult(
                false,
                "Email is required."
            )
            else -> CreatePresenterResult(true)
        }
}