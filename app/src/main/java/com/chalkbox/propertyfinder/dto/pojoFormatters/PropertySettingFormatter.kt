package com.chalkbox.propertyfinder.dto.pojoFormatters

import com.chalkbox.propertyfinder.dto.pojos.PropertySetting

class PropertySettingFormatter<T: PropertySetting>(item: T) : PojoFormatter() {
    val displayName: String = displayFormat(item.name).capitalize()
}