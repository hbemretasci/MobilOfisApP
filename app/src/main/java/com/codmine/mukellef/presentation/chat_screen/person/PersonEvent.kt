package com.codmine.mukellef.presentation.chat_screen.person

sealed class PersonEvent{
    object LoadData: PersonEvent()
    object Refresh: PersonEvent()
}
