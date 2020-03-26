package com.chalkbox.propertyfinder.dto.pojoFormatters

import com.chalkbox.propertyfinder.dto.pojos.PropertyType

class PropertyTypeFormatter<T: PropertyType>(item: T) : PojoFormatter() {
    val displayName: String = displayFormat(item.name).capitalize()
}