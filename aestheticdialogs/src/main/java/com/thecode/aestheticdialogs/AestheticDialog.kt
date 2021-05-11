package com.thecode.aestheticdialogs

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.text.method.ScrollingMovementMethod
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.annotation.Keep
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.dialog_connectify_error.view.*
import kotlinx.android.synthetic.main.dialog_connectify_success.view.*
import kotlinx.android.synthetic.main.dialog_emoji.view.*
import kotlinx.android.synthetic.main.dialog_emotion.view.*
import kotlinx.android.synthetic.main.dialog_flash.view.*
import kotlinx.android.synthetic.main.dialog_flat.view.*
import kotlinx.android.synthetic.main.dialog_flat.view.dialog_frame_flat
import kotlinx.android.synthetic.main.dialog_flat.view.dialog_icon_flat
import kotlinx.android.synthetic.main.dialog_flat.view.dialog_layout_flat
import kotlinx.android.synthetic.main.dialog_flat.view.dialog_message_flat
import kotlinx.android.synthetic.main.dialog_flat.view.dialog_title_flat
import kotlinx.android.synthetic.main.dialog_flat.view.image_close_register
import kotlinx.android.synthetic.main.dialog_flat_verify_sms.view.*
import kotlinx.android.synthetic.main.dialog_flat_verify_sms.view.dialog_lnr_verify_code
import kotlinx.android.synthetic.main.dialog_message_alert.view.*
import kotlinx.android.synthetic.main.dialog_rainbow.view.*
import kotlinx.android.synthetic.main.dialog_register_gmail.view.*
import kotlinx.android.synthetic.main.dialog_sell_product.view.*
import kotlinx.android.synthetic.main.dialog_toaster.view.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * Aesthetic Dialog class
 * Use Builder to create a new instance.
 *
 * @author Gabriel The Code
 */

@Keep
open class AestheticDialog {

    class Builder(
        //Necessary parameters
        @NonNull private val activity: Activity,
        @NonNull private val dialogStyle: DialogStyle,
        @NonNull private val dialogType: DialogType
    ) {


        lateinit var alertDialog: AlertDialog
        private val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(activity)

        private var title: String = "Title"
        private var message: String = "Message"

        private var txt_btn_left: String = "ok"
        private var txt_btn_right: String = "cancel"
        // Optional features
        private var isDarkMode: Boolean = false
        private var isCancelable: Boolean = true
        private var duration: Int = 0
        private var gravity: Int = Gravity.NO_GRAVITY
        private var animation: DialogAnimation = DialogAnimation.DEFAULT
        private lateinit var layoutView: View
        lateinit var btnSendPhoneNumber: AppCompatButton
        lateinit var sign_in_button:com.google.android.gms.common.SignInButton
       /* lateinit var sign_in_button: com.google.android.gms.common.SignInButton*/
       lateinit var btn_sell:Button
        lateinit var btnCodeVerify: AppCompatButton
        lateinit var prg_verify_sms: ProgressBar
        lateinit var dialog_lnr_verify_code: LinearLayout

        lateinit var edtPhoneNumber: EditText
        lateinit var edtCodeVerify: EditText
        private var setOnClickListenerok: OnDialogClickListenerok = object : OnDialogClickListenerok {
            override fun onClick(dialog: Builder) {
                dialog.dismiss()
            }
        }

        private var setOnClickListenerSendVerifyCode: OnDialogClickListenerSendVerifyCode = object : OnDialogClickListenerSendVerifyCode {
            override fun onClick(dialog: Builder) {
                dialog.dismiss()
            }
        }
        private var setonClickListenercancel: OnDialogClickListenercancel = object : OnDialogClickListenercancel {
            override fun onClick(dialog: Builder) {
                dialog.dismiss()
            }
        }

        private var setonClickListenerChangePhoneNumber: OnDialogClickListenerChangePhoneNumber = object : OnDialogClickListenerChangePhoneNumber {
            override fun onClick(dialog: Builder) {
                dialog.dismiss()
            }
        }

        private var setonClickListenerRegisterGmail: OnDialogClickListenerRegisterGmail = object : OnDialogClickListenerRegisterGmail {
            override fun onClick(dialog: Builder) {
                dialog.dismiss()
            }
        }

        private var setonClickListenerSell: OnDialogClickListenerSell = object : OnDialogClickListenerSell {
            override fun onClick(dialog: Builder) {
                dialog.dismiss()
            }
        }
        /**
         * Set dialog title text
         *
         * @param title
         * @return this, for chaining.
         */
        @NonNull
        fun setTitle(@NonNull title: String): Builder {
            this.title = title
            return this
        }

        /**
         * Set dialog message text
         *
         * @param message
         * @return this, for chaining.
         */
        @NonNull
        fun setMessage(@NonNull message: String): Builder {
            this.message = message
            return this
        }
        fun hideBtnsend():  AestheticDialog {
            btnSendPhoneNumber.visibility=View.INVISIBLE


            return AestheticDialog()
        }
        fun disableedtPhonenUMber():  AestheticDialog {
            edtPhoneNumber.isEnabled  = false //No such method found

            return AestheticDialog()
        }

        fun enablePhonenUMber():  AestheticDialog {
            edtPhoneNumber.isEnabled  = true //No such method found
            edtPhoneNumber.requestFocus();

            return AestheticDialog()
        }
        fun showBtnsend():  AestheticDialog {
            btnSendPhoneNumber.visibility=View.VISIBLE


            return AestheticDialog()
        }


        fun enableLinearVerifyCode():  AestheticDialog {
            dialog_lnr_verify_code.visibility=View.VISIBLE //No such method found

            return AestheticDialog()
        }
        fun setHightAlertDialog():  AestheticDialog {
            val height = activity.resources.getDimensionPixelSize(R.dimen.popup_height_register)
            val width = activity.resources.getDimensionPixelSize(R.dimen.popup_height)
            alertDialog.window?.setLayout(width, height)
            return AestheticDialog()
        }

        fun playProgressbar():  AestheticDialog {
            prg_verify_sms.visibility=View.VISIBLE

            Handler().postDelayed({
                prg_verify_sms!!.setVisibility(View.INVISIBLE)
                setHightAlertDialog()
                enableLinearVerifyCode()
                disableedtPhonenUMber()
                hideBtnsend()

            }, 4000/* 5 second */)

            return AestheticDialog()
        }
        fun validCellPhone(): Boolean {

            if (edtPhoneNumber.text == null ||  edtPhoneNumber.text.length <  11) {
                return false;
            } else {
                return android.util.Patterns.PHONE.matcher(edtPhoneNumber.text).matches();
            }

        }
        @NonNull
        fun setTxt_btn_left(@NonNull txt_btn_left: String): Builder {
            this.txt_btn_left = txt_btn_left
            return this
        }
        @NonNull
        fun setTxt_btn_right(@NonNull txt_btn_right: String): Builder {
            this.txt_btn_right = txt_btn_right
            return this
        }
        /**
         * Set dialog mode. Defined by default to false
         *
         * @param isDarkMode
         * @return this, for chaining.
         */
        @NonNull
        fun setDarkMode(@NonNull isDarkMode: Boolean): Builder {
            this.isDarkMode = isDarkMode
            return this
        }

        /**
         * Set an OnClickListener to the dialog
         *
         * @param onDialogClickListener interface for callback event on click of button.
         * @return this, for chaining.
         */
        @NonNull
        fun setOnClickListenerok(onDialogClickListener: OnDialogClickListenerok): Builder {
            this.setOnClickListenerok = onDialogClickListener
            return this
        }
        @NonNull
        fun setOnClickListenercancel(onDialogClickListener: OnDialogClickListenercancel): Builder {
            this.setonClickListenercancel = onDialogClickListener
            return this
        }

        @NonNull
        fun setonClickListenerChangePhoneNumber(onDialogClickListener: OnDialogClickListenerChangePhoneNumber): Builder {
            this.setonClickListenerChangePhoneNumber = onDialogClickListener
            return this
        }

        @NonNull
        fun setonClickListenerRegisterGmail(onDialogClickListener: OnDialogClickListenerRegisterGmail): Builder {
            this.setonClickListenerRegisterGmail = onDialogClickListener
            return this
        }

        @NonNull
        fun setonClickListenerSell(onDialogClickListener: OnDialogClickListenerSell): Builder {
            this.setonClickListenerSell = onDialogClickListener
            return this
        }

        @NonNull
        fun setonClickListenerSendVerifyCode(onDialogClickListener: OnDialogClickListenerSendVerifyCode): Builder {
            this.setOnClickListenerSendVerifyCode = onDialogClickListener
            return this
        }
        /**
         * Define if the dialog is cancelable
         *
         * @param isCancelable
         * @return this, for chaining.
         */
        @NonNull
        fun setCancelable(isCancelable: Boolean): Builder {
            this.isCancelable = isCancelable
            return this
        }

        /**
         * Define the display duration of the dialog
         *
         * @param duration in milliseconds
         * @return this, for chaining.
         */
        @NonNull
        fun setDuration(duration: Int): Builder {
            if (duration != 0) {
                this.duration = duration
                Handler().postDelayed({
                    this.dismiss()
                }, duration.toLong())
            }
            return this
        }

        /**
         * Set the gravity of the dialog
         *
         * @param gravity in milliseconds
         * @return this, for chaining.
         */
        @NonNull
        fun setGravity(gravity: Int): Builder {
            this.gravity = gravity
            return this
        }

        /**
         * Set the animation of the dialog
         *
         * @param animation in milliseconds
         * @return this, for chaining.
         */
        @NonNull
        fun setAnimation(animation: DialogAnimation): Builder {
            this.animation = animation
            return this
        }

        /**
         * Dismiss the dialog
         *
         * @return Aesthetic Dialog instance.
         */
        @NonNull
        fun dismiss(): AestheticDialog {
            if (alertDialog.isShowing) {
                alertDialog.dismiss()
            }
            return AestheticDialog()
        }


        /**
         * Choose the dialog animation according to the parameter
         *
         */
        @NonNull
        private fun chooseAnimation() {
                when (animation) {
                    DialogAnimation.ZOOM -> {
                        alertDialog.window?.attributes?.windowAnimations =
                            R.style.DialogAnimationZoom
                    }
                    DialogAnimation.FADE -> {
                        alertDialog.window?.attributes?.windowAnimations =
                            R.style.DialogAnimationFade
                    }
                    DialogAnimation.CARD -> {
                        alertDialog.window?.attributes?.windowAnimations =
                            R.style.DialogAnimationCard
                    }
                    DialogAnimation.SHRINK -> {
                        alertDialog.window?.attributes?.windowAnimations =
                            R.style.DialogAnimationShrink
                    }
                    DialogAnimation.SWIPE_LEFT -> {
                        alertDialog.window?.attributes?.windowAnimations =
                            R.style.DialogAnimationSwipeLeft
                    }
                    DialogAnimation.SWIPE_RIGHT -> {
                        alertDialog.window?.attributes?.windowAnimations =
                            R.style.DialogAnimationSwipeRight
                    }
                    DialogAnimation.IN_OUT -> {
                        alertDialog.window?.attributes?.windowAnimations =
                            R.style.DialogAnimationInOut
                    }
                    DialogAnimation.SPIN -> {
                        alertDialog.window?.attributes?.windowAnimations =
                            R.style.DialogAnimationSpin
                    }
                    DialogAnimation.SPLIT -> {
                        alertDialog.window?.attributes?.windowAnimations =
                            R.style.DialogAnimationSplit
                    }
                    DialogAnimation.DIAGONAL -> {
                        alertDialog.window?.attributes?.windowAnimations =
                            R.style.DialogAnimationDiagonal
                    }
                    DialogAnimation.WINDMILL -> {
                        alertDialog.window?.attributes?.windowAnimations =
                            R.style.DialogAnimationWindMill
                    }
                    DialogAnimation.SLIDE_UP -> {
                        alertDialog.window?.attributes?.windowAnimations =
                            R.style.DialogAnimationSlideUp
                    }
                    DialogAnimation.SLIDE_DOWN -> {
                        alertDialog.window?.attributes?.windowAnimations =
                            R.style.DialogAnimationSlideDown
                    }
                    DialogAnimation.SLIDE_LEFT -> {
                        alertDialog.window?.attributes?.windowAnimations =
                            R.style.DialogAnimationSlideLeft
                    }
                    DialogAnimation.SLIDE_RIGHT -> {
                        alertDialog.window?.attributes?.windowAnimations =
                            R.style.DialogAnimationSlideRight
                    }
                    DialogAnimation.DEFAULT -> {
                        alertDialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
                    }
                }
        }


        /**
         * Displays the dialog according to the parameters of the Builder
         *
         * @return Aesthetic Dialog instance.
         */
        @NonNull
        fun show(): AestheticDialog {

            when (dialogStyle) {
                DialogStyle.EMOJI -> {
                    layoutView = activity.layoutInflater.inflate(R.layout.dialog_emoji, null)
                    val layoutDialog = layoutView.dialog_layout_emoji
                    val imgClose: AppCompatImageView = layoutView.image_close_emoji
                    val icon: AppCompatImageView = layoutView.dialog_icon_emoji
                    val textTitle: AppCompatTextView = layoutView.text_title_emoji
                    val textMessage: AppCompatTextView = layoutView.text_message_emoji
                    textMessage.text = message
                    textTitle.text = title

                    if (dialogType == DialogType.SUCCESS) {
                        textTitle.setTextColor(
                            ContextCompat.getColor(
                                activity,
                                R.color.dialog_success
                            )
                        )
                        icon.setImageResource(R.drawable.thumbs_up_sign)
                    } else {
                        textTitle.setTextColor(
                            ContextCompat.getColor(
                                activity,
                                R.color.dialog_error
                            )
                        )
                        icon.setImageResource(R.drawable.man_shrugging)
                    }

                    if (isDarkMode) {
                        textMessage.setTextColor(
                            ContextCompat.getColor(
                                activity,
                                R.color.md_white_1000
                            )
                        )
                        layoutDialog.setBackgroundColor(
                            ContextCompat.getColor(
                                activity,
                                R.color.dark_background
                            )
                        )
                    }
                    dialogBuilder.setView(layoutView)
                    alertDialog = dialogBuilder.create()
                    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    alertDialog.window?.setGravity(Gravity.TOP)
                    this.chooseAnimation()
                    alertDialog.show()
                    val height =
                        activity.resources.getDimensionPixelSize(R.dimen.popup_height_emoji_dialog)
                    alertDialog.window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, height)

                    imgClose.setOnClickListener { setOnClickListenerok.onClick(this) }

                    imgClose.setOnClickListener { setonClickListenercancel.onClick(this) }

                }


                DialogStyle.DRAKE -> {
                    layoutView = if (dialogType == DialogType.SUCCESS) {
                        activity.layoutInflater.inflate(R.layout.dialog_drake_success, null)
                    } else {
                        activity.layoutInflater.inflate(R.layout.dialog_drake_error, null)
                    }
                    dialogBuilder.setView(layoutView)
                    alertDialog = dialogBuilder.create()
                    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    alertDialog.window?.setGravity(Gravity.CENTER)
                    this.chooseAnimation()
                    alertDialog.show()
                    val height =
                        activity.resources.getDimensionPixelSize(R.dimen.popup_height_drake)
                    alertDialog.window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, height)
                }

                DialogStyle.TOASTER -> {
                    if (isDarkMode) {
                        layoutView = activity.layoutInflater.inflate(R.layout.dialog_toaster, null)
                        val layoutDialog = layoutView.dialog_layout_toaster
                        layoutDialog.setBackgroundColor(
                            ContextCompat.getColor(
                                activity,
                                R.color.dark_background
                            )
                        )
                        val imgClose: AppCompatImageView = layoutView.image_close_toaster
                        val icon: AppCompatImageView = layoutView.dialog_icon_toaster
                        val textTitle: AppCompatTextView = layoutView.text_title_toaster
                        val textMessage: AppCompatTextView = layoutView.text_message_toaster
                        textMessage.setTextColor(
                            ContextCompat.getColor(
                                activity,
                                R.color.md_white_1000
                            )
                        )
                        val verticalView = layoutView.vertical_view_toaster
                        textMessage.text = message
                        textTitle.text = title
                        when (dialogType) {
                            DialogType.ERROR -> {
                                textTitle.setTextColor(
                                    ContextCompat.getColor(
                                        activity,
                                        R.color.dialog_error
                                    )
                                )
                                verticalView.setBackgroundColor(
                                    ContextCompat.getColor(
                                        activity,
                                        R.color.dialog_error
                                    )
                                )
                                icon.setImageResource(R.drawable.ic_error_red_24dp)
                            }
                            DialogType.SUCCESS -> {
                                textTitle.setTextColor(
                                    ContextCompat.getColor(
                                        activity,
                                        R.color.dialog_success
                                    )
                                )
                                verticalView.setBackgroundColor(
                                    ContextCompat.getColor(
                                        activity,
                                        R.color.dialog_success
                                    )
                                )
                                icon.setImageResource(R.drawable.ic_check_circle_green_24dp)
                            }
                            DialogType.WARNING -> {
                                textTitle.setTextColor(
                                    ContextCompat.getColor(
                                        activity,
                                        R.color.dialog_warning
                                    )
                                )
                                verticalView.setBackgroundColor(
                                    ContextCompat.getColor(
                                        activity,
                                        R.color.dialog_warning
                                    )
                                )
                                icon.setImageResource(R.drawable.ic_warning_orange_24dp)
                            }
                            DialogType.INFO -> {
                                textTitle.setTextColor(
                                    ContextCompat.getColor(
                                        activity,
                                        R.color.dialog_info
                                    )
                                )
                                verticalView.setBackgroundColor(
                                    ContextCompat.getColor(
                                        activity,
                                        R.color.dialog_info
                                    )
                                )
                                icon.setImageResource(R.drawable.ic_info_blue_24dp)
                            }
                        }
                        dialogBuilder.setView(layoutView)
                        alertDialog = dialogBuilder.create()
                        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        alertDialog.window?.setGravity(Gravity.TOP)
                        this.chooseAnimation()
                        alertDialog.show()
                        val height =
                            activity.resources.getDimensionPixelSize(R.dimen.popup_height_toaster)
                        alertDialog.window?.setLayout(
                            WindowManager.LayoutParams.WRAP_CONTENT,
                            height
                        )
                        imgClose.setOnClickListener { setOnClickListenerok.onClick(this) }

                        imgClose.setOnClickListener { setonClickListenercancel.onClick(this) }
                    } else {

                        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(activity)
                        layoutView = activity.layoutInflater.inflate(R.layout.dialog_toaster, null)
                        val imgClose: AppCompatImageView = layoutView.image_close_toaster
                        val icon: AppCompatImageView = layoutView.dialog_icon_toaster
                        val textTitle: AppCompatTextView = layoutView.text_title_toaster
                        val textMessage: AppCompatTextView = layoutView.text_message_toaster
                        val verticalView = layoutView.vertical_view_toaster
                        textMessage.text = message
                        textTitle.text = title
                        when (dialogType) {
                            DialogType.ERROR -> {
                                verticalView.setBackgroundColor(
                                    ContextCompat.getColor(
                                        activity,
                                        R.color.dialog_error
                                    )
                                )
                                icon.setImageResource(R.drawable.ic_error_red_24dp)
                            }
                            DialogType.SUCCESS -> {
                                verticalView.setBackgroundColor(
                                    ContextCompat.getColor(
                                        activity,
                                        R.color.dialog_success
                                    )
                                )
                                icon.setImageResource(R.drawable.ic_check_circle_green_24dp)
                            }
                            DialogType.WARNING -> {
                                verticalView.setBackgroundColor(
                                    ContextCompat.getColor(
                                        activity,
                                        R.color.dialog_warning
                                    )
                                )
                                icon.setImageResource(R.drawable.ic_warning_orange_24dp)
                            }
                            DialogType.INFO -> {
                                verticalView.setBackgroundColor(
                                    ContextCompat.getColor(
                                        activity,
                                        R.color.dialog_info
                                    )
                                )
                                icon.setImageResource(R.drawable.ic_info_blue_24dp)
                            }
                        }
                        dialogBuilder.setView(layoutView)
                        alertDialog = dialogBuilder.create()
                        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        alertDialog.window?.setGravity(Gravity.TOP)
                        this.chooseAnimation()
                        alertDialog.show()
                        val height =
                            activity.resources.getDimensionPixelSize(R.dimen.popup_height_toaster)
                        alertDialog.window?.setLayout(
                            WindowManager.LayoutParams.WRAP_CONTENT,
                            height
                        )
                        imgClose.setOnClickListener { setOnClickListenerok.onClick(this) }
                        imgClose.setOnClickListener { setonClickListenercancel.onClick(this) }
                    }

                }

                DialogStyle.RAINBOW -> {
                    layoutView = activity.layoutInflater.inflate(R.layout.dialog_rainbow, null)
                    val icon: AppCompatImageView = layoutView.dialog_icon_rainbow
                    val layoutDialog = layoutView.dialog_layout_rainbow
                    when (dialogType) {
                        DialogType.ERROR -> {
                            layoutDialog.setBackgroundColor(
                                ContextCompat.getColor(
                                    activity,
                                    R.color.dialog_error
                                )
                            )
                            icon.setImageResource(R.drawable.ic_error_red_24dp)
                        }
                        DialogType.SUCCESS -> {
                            layoutDialog.setBackgroundColor(
                                ContextCompat.getColor(
                                    activity,
                                    R.color.dialog_success
                                )
                            )
                            icon.setImageResource(R.drawable.ic_check_circle_green_24dp)
                        }
                        DialogType.WARNING -> {
                            layoutDialog.setBackgroundColor(
                                ContextCompat.getColor(
                                    activity,
                                    R.color.dialog_warning
                                )
                            )
                            icon.setImageResource(R.drawable.ic_warning_orange_24dp)
                        }
                        DialogType.INFO -> {
                            layoutDialog.setBackgroundColor(
                                ContextCompat.getColor(
                                    activity,
                                    R.color.dialog_info
                                )
                            )
                            icon.setImageResource(R.drawable.ic_info_blue_24dp)
                        }
                    }
                    val imgClose: AppCompatImageView = layoutView.image_close_rainbow
                    val textTitle: AppCompatTextView = layoutView.text_title_rainbow
                    val textMessage: AppCompatTextView = layoutView.text_message_rainbow
                    textMessage.text = message
                    textTitle.text = title
                    dialogBuilder.setView(layoutView)
                    alertDialog = dialogBuilder.create()
                    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    alertDialog.window?.setGravity(Gravity.TOP)
                    this.chooseAnimation()
                    alertDialog.show()
                    val height =
                        activity.resources.getDimensionPixelSize(R.dimen.popup_height_emoji_dialog)
                    alertDialog.window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, height)
                    imgClose.setOnClickListener { setOnClickListenerok.onClick(this) }
                    imgClose.setOnClickListener { setonClickListenercancel.onClick(this) }
                }

                DialogStyle.CONNECTIFY -> {
                    val imgClose: AppCompatImageView
                    val textTitle: AppCompatTextView
                    val textMessage: AppCompatTextView
                    val layoutDialog: LinearLayoutCompat
                    if (dialogType == DialogType.SUCCESS) {
                        layoutView = activity.layoutInflater.inflate(
                            R.layout.dialog_connectify_success,
                            null
                        )
                        layoutDialog = layoutView.dialog_layout_connectify_success
                        imgClose = layoutView.image_close_connectify_success
                        textTitle = layoutView.text_title_connectify_success
                        textMessage = layoutView.text_message_connectify_success
                    } else {
                        layoutView = activity.layoutInflater.inflate(
                            R.layout.dialog_connectify_error,
                            null
                        )
                        layoutDialog = layoutView.dialog_layout_connectify_error
                        imgClose = layoutView.image_close_connectify_error
                        textTitle = layoutView.text_title_connectify_error
                        textMessage = layoutView.text_message_connectify_error
                    }

                    textTitle.text = title
                    textMessage.text = message

                    if (isDarkMode) {
                        layoutDialog.setBackgroundColor(
                            ContextCompat.getColor(
                                activity,
                                R.color.dark_background
                            )
                        )
                        textMessage.setTextColor(
                            ContextCompat.getColor(
                                activity,
                                R.color.md_white_1000
                            )
                        )
                    }
                    dialogBuilder.setView(layoutView)
                    alertDialog = dialogBuilder.create()
                    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    alertDialog.window?.setGravity(Gravity.TOP)
                    this.chooseAnimation()
                    alertDialog.show()
                    alertDialog.window?.setLayout(
                        WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT
                    )
                    imgClose.setOnClickListener { setOnClickListenerok.onClick(this) }
                    imgClose.setOnClickListener { setonClickListenercancel.onClick(this) }
                }


                DialogStyle.FLASH -> {
                    layoutView = activity.layoutInflater.inflate(R.layout.dialog_flash, null)
                    val btnOk: AppCompatButton = layoutView.btn_action_flash
                    val textTitle: AppCompatTextView = layoutView.dialog_title_flash
                    val textMessage: AppCompatTextView = layoutView.dialog_message_flash
                    val dialogFrame = layoutView.dialog_frame_flash
                    val icon: AppCompatImageView = layoutView.img_icon_flash
                    if (dialogType == DialogType.SUCCESS) {
                        dialogFrame.setBackgroundResource(R.drawable.rounded_green_gradient_bg)
                        icon.setImageResource(R.drawable.circle_validation_success)
                    } else {
                        dialogFrame.setBackgroundResource(R.drawable.rounded_red_gradient_bg)
                        icon.setImageResource(R.drawable.circle_validation_error)
                    }
                    textMessage.text = message
                    textTitle.text = title
                    dialogBuilder.setView(layoutView)
                    alertDialog = dialogBuilder.create()
                    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    alertDialog.window?.setGravity(Gravity.CENTER)
                    this.chooseAnimation()
                    alertDialog.show()
                    val height = activity.resources.getDimensionPixelSize(R.dimen.popup_height)
                    val width = activity.resources.getDimensionPixelSize(R.dimen.popup_height)
                    alertDialog.window?.setLayout(width, height)
                    btnOk.setOnClickListener { setOnClickListenerok.onClick(this) }
                }
                DialogStyle.REGISTER_USER -> {
                    layoutView = activity.layoutInflater.inflate(
                        R.layout.dialog_flat_verify_sms,
                        null
                    )
                    btnSendPhoneNumber = layoutView.btn_action_flat_send_phoneNumber


                    val  btnEditePhoneNumber : AppCompatButton = layoutView.btn_verify_sms_edite_phone_number
                    btnCodeVerify = layoutView.btn_Send_verify_code
                    prg_verify_sms = layoutView.prg_verify_sms
                    dialog_lnr_verify_code = layoutView.dialog_lnr_verify_code
                    edtPhoneNumber = layoutView.edt_phone_number
                    edtCodeVerify = layoutView.edt_code_verify

                    val imgClose: AppCompatImageView = layoutView.image_close_verify_sms
                    val textTitle: AppCompatTextView = layoutView.dialog_title_flat
                    val textMessage: AppCompatTextView = layoutView.dialog_message_flat
                    val dialogFrame = layoutView.dialog_layout_flat
                    if (dialogType == DialogType.SUCCESS) {
                        //  dialogFrame.setBackgroundResource(R.drawable.rounded_green_gradient_bg)
                    } else {
                        //  dialogFrame.setBackgroundResource(R.drawable.rounded_red_gradient_bg)
                    }
                    /*  textMessage.text = message
                    textTitle.text = title*/
                    dialogBuilder.setView(layoutView)
                    alertDialog = dialogBuilder.create()
                    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    alertDialog.window?.setGravity(Gravity.CENTER)
                    this.chooseAnimation()
                    alertDialog.show()
                    val height = activity.resources.getDimensionPixelSize(R.dimen.popup_height)
                    val width = activity.resources.getDimensionPixelSize(R.dimen.popup_height)
                    alertDialog.window?.setLayout(width, height)
                    btnSendPhoneNumber.setOnClickListener { setOnClickListenerok.onClick(this) }
                    btnCodeVerify.setOnClickListener { setOnClickListenerSendVerifyCode.onClick(this)

                    }
                    btnEditePhoneNumber.setOnClickListener { setonClickListenerChangePhoneNumber.onClick(this)

                    }


                    imgClose.setOnClickListener { setonClickListenercancel.onClick(this)

                    }
                }
  DialogStyle.REGISTER_GMAIL -> {
                    layoutView = activity.layoutInflater.inflate(
                        R.layout.dialog_register_gmail,
                        null
                    )
     sign_in_button = layoutView.sign_in_button




                    val imgClose: AppCompatImageView = layoutView.image_close_register_gmail
                    val textTitle: AppCompatTextView = layoutView.dialog_title_flat
                    val textMessage: AppCompatTextView = layoutView.dialog_message_flat
                    val dialogFrame = layoutView.dialog_layout_flat
                    if (dialogType == DialogType.SUCCESS) {
                        //  dialogFrame.setBackgroundResource(R.drawable.rounded_green_gradient_bg)
                    } else {
                        //  dialogFrame.setBackgroundResource(R.drawable.rounded_red_gradient_bg)
                    }
                      textMessage.text = message
                    textTitle.text = title
                    dialogBuilder.setView(layoutView)
                    alertDialog = dialogBuilder.create()
                    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    alertDialog.window?.setGravity(Gravity.CENTER)
                    this.chooseAnimation()
                    alertDialog.show()
                    val height = activity.resources.getDimensionPixelSize(R.dimen.popup_height)
                    val width = activity.resources.getDimensionPixelSize(R.dimen.popup_height)
                    alertDialog.window?.setLayout(width, height)

      sign_in_button.setOnClickListener { setonClickListenerRegisterGmail.onClick(this)

                    }


                    imgClose.setOnClickListener { setonClickListenercancel.onClick(this)

                    }
                }
                DialogStyle.ALERT -> {
                    layoutView = activity.layoutInflater.inflate(
                        R.layout.dialog_message_alert,
                        null
                    )





                    val imgClose: AppCompatImageView = layoutView.iv_dialog_alert_close
                    val textTitle: AppCompatTextView = layoutView.dialog_alert_title
                    val textMessage: AppCompatTextView = layoutView.dialog_alert_message
                    val dialogFrame = layoutView.dialog_alert_message
                    if (dialogType == DialogType.ALERT) {
                        //  dialogFrame.setBackgroundResource(R.drawable.rounded_green_gradient_bg)
                    } else {
                        //  dialogFrame.setBackgroundResource(R.drawable.rounded_red_gradient_bg)
                    }
                    textMessage.text = message
                    textTitle.text = title
                    dialogBuilder.setView(layoutView)
                    alertDialog = dialogBuilder.create()
                    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    alertDialog.window?.setGravity(Gravity.CENTER)
                    this.chooseAnimation()
                    textMessage.movementMethod= ScrollingMovementMethod()

                    alertDialog.show()





                    imgClose.setOnClickListener { setonClickListenercancel.onClick(this)

                    }
                }
 DialogStyle.SELL -> {
                    layoutView = activity.layoutInflater.inflate(
                        R.layout.dialog_sell_product,
                        null
                    )
     btn_sell = layoutView.btn_sell




                    val imgClose: AppCompatImageView = layoutView.image_close_sell
                    val textTitle: AppCompatTextView = layoutView.dialog_sell_titele_product
                    val textMessage: AppCompatTextView = layoutView.dialog_sell_price_product
                    val dialogFrame = layoutView.dialog_layout_sell
                    if (dialogType == DialogType.SUCCESS) {
                        //  dialogFrame.setBackgroundResource(R.drawable.rounded_green_gradient_bg)
                    } else {
                        //  dialogFrame.setBackgroundResource(R.drawable.rounded_red_gradient_bg)
                    }
                      textMessage.text = message
                    textTitle.text = title
                    dialogBuilder.setView(layoutView)
                    alertDialog = dialogBuilder.create()
                    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    alertDialog.window?.setGravity(Gravity.CENTER)
                    this.chooseAnimation()
                    alertDialog.show()
     val height = activity.resources.getDimensionPixelSize(R.dimen.popup_height_sell)
     val width = activity.resources.getDimensionPixelSize(R.dimen.popup_width)
                    alertDialog.window?.setLayout(width, height)

      btn_sell.setOnClickListener { setonClickListenerSell.onClick(this)

                    }


                    imgClose.setOnClickListener { setonClickListenercancel.onClick(this)

                    }
}
                //**************sell****************************
                DialogStyle.EMOTION -> {
                    layoutView = activity.layoutInflater.inflate(R.layout.dialog_emotion, null)
                    val icon: AppCompatImageView = layoutView.img_icon_emotion
                    val layoutDialog = layoutView.dialog_layout_emotion
                    val textTitle: AppCompatTextView = layoutView.dialog_title_emotion
                    val textMessage: AppCompatTextView = layoutView.dialog_message_emotion
                    val textHour: AppCompatTextView = layoutView.dialog_hour_emotion
                    if (dialogType == DialogType.SUCCESS) {
                        icon.setImageResource(R.drawable.smiley_success)
                        layoutDialog.setBackgroundResource(R.drawable.background_emotion_success)
                    } else {
                        icon.setImageResource(R.drawable.smiley_error)
                        layoutDialog.setBackgroundResource(R.drawable.background_emotion_error)
                    }
                    val sdf = SimpleDateFormat("HH:mm")
                    val hour = sdf.format(Calendar.getInstance().time)
                    textMessage.text = message
                    textTitle.text = title
                    textHour.text = hour
                    dialogBuilder.setView(layoutView)
                    alertDialog = dialogBuilder.create()
                    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    alertDialog.window?.setGravity(Gravity.CENTER)
                    this.chooseAnimation()
                    alertDialog.show()
                    val height =
                        activity.resources.getDimensionPixelSize(R.dimen.popup_height_emotion)
                    alertDialog.window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, height)
                }
                DialogStyle.EMOTION -> {
                    layoutView = activity.layoutInflater.inflate(R.layout.dialog_emotion, null)
                    val icon: AppCompatImageView = layoutView.img_icon_emotion
                    val layoutDialog = layoutView.dialog_layout_emotion
                    val textTitle: AppCompatTextView = layoutView.dialog_title_emotion
                    val textMessage: AppCompatTextView = layoutView.dialog_message_emotion
                    val textHour: AppCompatTextView = layoutView.dialog_hour_emotion
                    if (dialogType == DialogType.SUCCESS) {
                        icon.setImageResource(R.drawable.smiley_success)
                        layoutDialog.setBackgroundResource(R.drawable.background_emotion_success)
                    } else {
                        icon.setImageResource(R.drawable.smiley_error)
                        layoutDialog.setBackgroundResource(R.drawable.background_emotion_error)
                    }
                    val sdf = SimpleDateFormat("HH:mm")
                    val hour = sdf.format(Calendar.getInstance().time)
                    textMessage.text = message
                    textTitle.text = title
                    textHour.text = hour
                    dialogBuilder.setView(layoutView)
                    alertDialog = dialogBuilder.create()
                    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    alertDialog.window?.setGravity(Gravity.CENTER)
                    this.chooseAnimation()
                    alertDialog.show()
                    val height =
                        activity.resources.getDimensionPixelSize(R.dimen.popup_height_emotion)
                    alertDialog.window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, height)
                }
                DialogStyle.FLAT -> {
                    if (isDarkMode) {
                        layoutView = activity.layoutInflater.inflate(R.layout.dialog_flat, null)
                        val btn_left: AppCompatButton = layoutView.btn_action_flat_left
                        val textTitle: AppCompatTextView = layoutView.dialog_title_flat
                        val textMessage: AppCompatTextView = layoutView.dialog_message_flat
                        val icon: AppCompatImageView = layoutView.dialog_icon_flat
                        val layoutDialog: LinearLayoutCompat = layoutView.dialog_layout_flat
                        val frameLayout = layoutView.dialog_frame_flat
                        when (dialogType) {
                            DialogType.ERROR -> {
                                icon.setImageResource(R.drawable.ic_error_red_24dp)
                                btn_left.setBackgroundResource(R.drawable.btn_red_selector)
                                frameLayout.setBackgroundResource(R.drawable.rounded_rect_red)
                            }
                            DialogType.SUCCESS -> {
                                icon.setImageResource(R.drawable.ic_check_circle_green_24dp)
                                btn_left.setBackgroundResource(R.drawable.btn_green_selector)
                                frameLayout.setBackgroundResource(R.drawable.rounded_rect_green)
                            }
                            DialogType.WARNING -> {
                                icon.setImageResource(R.drawable.ic_warning_orange_24dp)
                                btn_left.setBackgroundResource(R.drawable.btn_yellow_selector)
                                frameLayout.setBackgroundResource(R.drawable.rounded_rect_yellow)
                            }
                            DialogType.INFO -> {
                                icon.setImageResource(R.drawable.ic_info_blue_24dp)
                                btn_left.setBackgroundResource(R.drawable.btn_blue_selector)
                                frameLayout.setBackgroundResource(R.drawable.rounded_rect_blue)
                            }


                        }
                        layoutDialog.setBackgroundResource(R.drawable.rounded_dark_bg)
                        textTitle.setTextColor(
                            ContextCompat.getColor(
                                activity,
                                R.color.md_white_1000
                            )
                        )
                        textMessage.setTextColor(
                            ContextCompat.getColor(
                                activity,
                                R.color.md_white_1000
                            )
                        )
                        textMessage.text = message
                        textTitle.text = title
                        dialogBuilder.setView(layoutView)
                        alertDialog = dialogBuilder.create()
                        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        alertDialog.window?.setGravity(Gravity.CENTER)
                        this.chooseAnimation()
                        alertDialog.show()
                        val height = activity.resources.getDimensionPixelSize(R.dimen.popup_height)
                        val width = activity.resources.getDimensionPixelSize(R.dimen.popup_height)
                        alertDialog.window?.setLayout(width, height)
                        btn_left.setOnClickListener { setOnClickListenerok.onClick(this) }


                    } else {
                        layoutView = activity.layoutInflater.inflate(R.layout.dialog_flat, null)
                        val btn_left: AppCompatButton = layoutView.btn_action_flat_left
                        val imgClose: AppCompatImageView = layoutView.image_close_register
                        val textTitle: AppCompatTextView = layoutView.dialog_title_flat
                        val textMessage: AppCompatTextView = layoutView.dialog_message_flat
                        val icon: AppCompatImageView = layoutView.dialog_icon_flat
                        val layoutDialog: LinearLayoutCompat = layoutView.dialog_layout_flat
                        val frameLayout = layoutView.dialog_frame_flat
                        when (dialogType) {
                            DialogType.ERROR -> {
                                icon.setImageResource(R.drawable.ic_error_red_24dp)
                                btn_left.setBackgroundResource(R.drawable.btn_red_selector)
                                frameLayout.setBackgroundResource(R.drawable.rounded_rect_red)
                            }
                            DialogType.SUCCESS -> {
                                icon.setImageResource(R.drawable.ic_check_circle_green_24dp)
                                btn_left.setBackgroundResource(R.drawable.btn_green_selector)
                                frameLayout.setBackgroundResource(R.drawable.rounded_rect_green)
                            }
                            DialogType.WARNING -> {
                                icon.setImageResource(R.drawable.ic_warning_orange_24dp)
                                btn_left.setBackgroundResource(R.drawable.btn_yellow_selector)
                                frameLayout.setBackgroundResource(R.drawable.rounded_rect_yellow)
                            }
                            DialogType.INFO -> {
                                icon.setImageResource(R.drawable.ic_info_blue_24dp)
                                btn_left.setBackgroundResource(R.drawable.btn_blue_selector)
                                frameLayout.setBackgroundResource(R.drawable.rounded_rect_blue)
                            }


                        }
                        layoutDialog.setBackgroundResource(R.drawable.rounded_white_bg)
                        btn_left.text = txt_btn_left
                        textMessage.text = message
                        textTitle.text = title
                        dialogBuilder.setView(layoutView)
                        alertDialog = dialogBuilder.create()
                        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        alertDialog.window?.setGravity(Gravity.CENTER)
                        this.chooseAnimation()
                        alertDialog.show()
                        val height = activity.resources.getDimensionPixelSize(R.dimen.popup_height)
                        val width = activity.resources.getDimensionPixelSize(R.dimen.popup_height)

                        alertDialog.window?.setLayout(width, height)
                        btn_left.setOnClickListener {
                            setOnClickListenerok.onClick(this)
                            btn_left.setOnClickListener { setOnClickListenerok.onClick(this) }
                        }

                        imgClose.setOnClickListener { setonClickListenercancel.onClick(this) }
                    }
                }
            }

            alertDialog.setCancelable(isCancelable)
            if (gravity != Gravity.NO_GRAVITY) {
                alertDialog.window?.setGravity(gravity)
            }
            return AestheticDialog()
        }
    }
}