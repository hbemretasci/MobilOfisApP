package com.codmine.mukellef.presentation.chat_screen.person

import com.codmine.mukellef.domain.model.tax_payer.RelatedUser

sealed class PersonEvent{
    object LoadData: PersonEvent()
    object Refresh: PersonEvent()
    data class NavigateUser(val user: RelatedUser): PersonEvent()
}
