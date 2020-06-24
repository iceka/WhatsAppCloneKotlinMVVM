package com.iceka.whatsappclonekotlin.utils

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iceka.whatsappclonekotlin.R
import com.iceka.whatsappclonekotlin.data.model.CountryCallingCodes
import com.iceka.whatsappclonekotlin.data.model.User
import com.iceka.whatsappclonekotlin.ui.home.contact.ContactAdapter

@BindingAdapter("format_flag")
fun TextView.formatFlag(item: CountryCallingCodes) {
    val flagOffset = 0x1F1E6
    val asciiOffset = 0x41
    val codeName = item.code
    val firstChar = Character.codePointAt(codeName.toString(), 0) - asciiOffset + flagOffset
    val secondChar = Character.codePointAt(codeName.toString(), 1) - asciiOffset + flagOffset
    val flag = String(Character.toChars(firstChar)) + String(Character.toChars(secondChar))
    item.let {
        text = flag
    }
}

//@BindingAdapter("listData")
//fun RecyclerView.bindList(contactList: List<User>?) {
//    val adapter = adapter as ContactAdapter
//    adapter.submitList(contactListng
//    )
//}

@BindingAdapter("bindAvatar")
fun ImageView.bindAvatar(imageUrl: String) {
    apply { Glide.with(context)
        .load(imageUrl)
        .into(this)
    }
}


@BindingAdapter("bindAvatarInit")
fun ImageView.bindAvatar(uri: Uri?) {
    apply {
        if (uri != null) {
            Glide.with(context)
                .load(uri)
                .into(this)
        } else {
            Glide.with(context)
                .load(R.drawable.ic_baseline_person_24)
                .into(this)
        }

    }

}