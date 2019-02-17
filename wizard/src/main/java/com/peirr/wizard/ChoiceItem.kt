package com.peirr.wizard

import android.os.Parcel
import android.os.Parcelable

class ChoiceItem : Parcelable {

    var iconRes: Int = 0
    var title: Int = 0
    var footer: Int = 0
    var value: String? = null

    constructor()

    constructor(iconRes: Int, title: Int, footer: Int, value: String) {
        this.iconRes = iconRes
        this.title = title
        this.footer = footer
        this.value = value
    }

    constructor(parcel: Parcel) : this() {
        iconRes = parcel.readInt()
        title = parcel.readInt()
        footer = parcel.readInt()
        value = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(iconRes)
        parcel.writeInt(title)
        parcel.writeInt(footer)
        parcel.writeString(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ChoiceItem> {
        override fun createFromParcel(parcel: Parcel): ChoiceItem {
            return ChoiceItem(parcel)
        }

        override fun newArray(size: Int): Array<ChoiceItem?> {
            return arrayOfNulls(size)
        }
    }
}
