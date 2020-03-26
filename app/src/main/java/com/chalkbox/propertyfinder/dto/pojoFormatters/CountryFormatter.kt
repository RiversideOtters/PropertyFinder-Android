package com.chalkbox.propertyfinder.dto.pojoFormatters

import com.chalkbox.propertyfinder.dto.pojos.Country

class CountryFormatter<T: Country>(item: T) : PojoFormatter() {
    val displayName: String = displayFormat(item.name)
}