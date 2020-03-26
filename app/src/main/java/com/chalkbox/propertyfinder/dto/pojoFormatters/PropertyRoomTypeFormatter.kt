package com.chalkbox.propertyfinder.dto.pojoFormatters

import com.chalkbox.propertyfinder.dto.pojos.PropertyRoomType

class PropertyRoomTypeFormatter<T: PropertyRoomType>(item: T) : PojoFormatter() {
    val displayName: String = displayFormat(item.name).capitalize()
}