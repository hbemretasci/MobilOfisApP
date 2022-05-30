package com.codmine.mukellef.presentation.main

sealed class MainEvent{
    object ExitDialog: MainEvent()
    object ExitCancel: MainEvent()
    object ExitConfirm: MainEvent()
}
