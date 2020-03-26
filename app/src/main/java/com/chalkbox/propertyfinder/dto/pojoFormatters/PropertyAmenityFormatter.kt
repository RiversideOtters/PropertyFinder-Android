package com.chalkbox.propertyfinder.dto.pojoFormatters

import com.chalkbox.propertyfinder.dto.pojos.PropertyAmenity

class PropertyAmenityFormatter<T: PropertyAmenity>(item: T) : PojoFormatter() {
    val displayName: String = displayFormat(item.name).capitalize()
}