package com.chalkbox.propertyfinder.dto.pojoFormatters

import com.chalkbox.propertyfinder.dto.pojos.PropertySettingValue
import java.util.*

class PropertySettingValueFormatter<T: PropertySettingValue>(item: T) : PojoFormatter() {
    val displayName: String = displayFormat(item.name).capitalize()
}