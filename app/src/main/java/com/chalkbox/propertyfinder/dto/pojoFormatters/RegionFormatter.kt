package com.chalkbox.propertyfinder.dto.pojoFormatters

import com.chalkbox.propertyfinder.dto.pojos.Region

class RegionFormatter<T: Region>(item: T) : PojoFormatter() {
    val displayName: String = displayFormat(item.name).capitalize()
}