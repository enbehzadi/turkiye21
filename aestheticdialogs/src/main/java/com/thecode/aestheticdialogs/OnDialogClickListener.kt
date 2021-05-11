package com.thecode.aestheticdialogs


interface OnDialogClickListenerok {
    fun onClick(dialog: AestheticDialog.Builder)
}

interface OnDialogClickListenercancel {
    fun onClick(dialog: AestheticDialog.Builder)
}

interface OnDialogClickListenerChangePhoneNumber {
    fun onClick(dialog: AestheticDialog.Builder)
}

interface OnDialogClickListenerSendVerifyCode {
    fun onClick(dialog: AestheticDialog.Builder)
}

interface OnDialogClickListenerRegisterGmail {
    fun onClick(dialog: AestheticDialog.Builder)
}
interface OnDialogClickListenerSell {
    fun onClick(dialog: AestheticDialog.Builder)
}

